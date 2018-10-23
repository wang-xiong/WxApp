package com.example.apt_processor;

import com.example.apt_annotation.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
public class BindViewProcessor extends AbstractProcessor {

    private Messager mMessager;
    private Elements mElementUtils;
    private Map<String, ClassCreatorProxy> mProxyMap = new HashMap<>();

    /**
     * 1.init:初始化，可以得到processingEnvironment
     * 2.getSupportedAnnotationTypes:指定这个注解器是注册给那个注解的
     * 3.getSupportedSourceVersion:指定使用的Java版本
     * 4.process:可以在这里写扫描、评估和处理注解的代码，生成Java文件
     * 5.介绍下依赖库auto-service,在使用注解处理器需要先声明，步骤：
     *  a、需要在 processors 库的 main 目录下新建 resources 资源文件夹；
     *  b、在 resources文件夹下建立 META-INF/services 目录文件夹；
     *  c、在 META-INF/services 目录文件夹下创建 javax.annotation.processing.Processor 文件；
     *  d、在 javax.annotation.processing.Processor 文件写入注解处理器的全称，包括包路径；）
     *  这样声明下来也太麻烦了？这就是用引入auto-service的原因。
     *  通过auto-service中的@AutoService可以自动生成AutoService注解处理器是Google开发的，
     *  用来生成 META-INF/services/javax.annotation.processing.Processor 文件的
     */

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mMessager = processingEnvironment.getMessager();
        mElementUtils = processingEnvironment.getElementUtils();
    }


    @Override
    public Set<String> getSupportedAnnotationTypes() {
        HashSet<String> supportTypes = new LinkedHashSet<>();
        supportTypes.add(BindView.class.getCanonicalName());
        return supportTypes;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        //根据注解生成java文件
        mMessager.printMessage(Diagnostic.Kind.NOTE, "processing...");
        mProxyMap.clear();
        //得到所有的注解
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        for(Element element : elements) {
            VariableElement variableElement = (VariableElement) element;
            TypeElement classElement = (TypeElement) variableElement.getEnclosingElement();
            String fullClassName = classElement.getQualifiedName().toString();
            ClassCreatorProxy proxy = mProxyMap.get(fullClassName);
            if (proxy == null) {
                proxy = new ClassCreatorProxy(mElementUtils, classElement);
                mProxyMap.put(fullClassName, proxy);
            }
            BindView bindAnnotation =  variableElement.getAnnotation(BindView.class);
            int id = bindAnnotation.value();
            proxy.putElement(id, variableElement);
        }

        //通过遍历mProxyMap，创建Java文件
        for(String key : mProxyMap.keySet()) {
            ClassCreatorProxy proxyInfo = mProxyMap.get(key);
            //方法1通过StringBuilder来生成对应的Java代码
            /*try {
                mMessager.printMessage(Diagnostic.Kind.NOTE, "--> create " + proxyInfo.getProxyClassFullName());
                JavaFileObject jfo = processingEnv.getFiler().createSourceFile(proxyInfo.getProxyClassFullName(), proxyInfo.getTypeElement());
                Writer writer = jfo.openWriter();
                writer.write(proxyInfo.generateJavaCode());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
                mMessager.printMessage(Diagnostic.Kind.NOTE, "--> create " + proxyInfo.getProxyClassFullName() + "error");
            }*/

            //方法2通过javapoet来生成对应的Java代码
            JavaFile javaFile = JavaFile.builder(proxyInfo.getPackageName(), proxyInfo.generateJavaCode2()).build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        mMessager.printMessage(Diagnostic.Kind.NOTE, "process finish ...");
        return true;
    }
}
