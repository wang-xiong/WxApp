package com.example.lib_base_processor;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedOptions;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

@SupportedOptions({"debug"})
public abstract class BaseAnnotationProcessor extends AbstractProcessor {
    private List<ProcessingStep> processingStepList;
    private JavaFilesWriter javaFilesWriter;

    //每个注解处理器必须要有个空的构造函数
    public BaseAnnotationProcessor() {
    }

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        ProcessingEnv.processingEnvironment = processingEnvironment;
        ProcessingEnv.debug = processingEnvironment.getOptions().containsKey("debug");

        this.processingStepList = getProcessingStepList();
        this.javaFilesWriter = new JavaFilesWriter(processingEnvironment.getFiler());
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (!javaFilesWriter.isStop()) {
            AnnotatedElementHashMap annotatedElementHashMap = new AnnotatedElementHashMap(getSupportedAnnotations(), roundEnvironment);
            for (ProcessingStep processingStep : processingStepList) {
                processingStep.process(annotatedElementHashMap, javaFilesWriter);
            }
        }
        return false;
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> annotationTypes = new HashSet<>();
        Set<Class<? extends Annotation>> supportedAnnotations = getSupportedAnnotations();
        for (Class<? extends Annotation> annotation : supportedAnnotations) {
            //绝对路径名称
            annotationTypes.add(annotation.getCanonicalName());
        }
        return annotationTypes;
    }

    protected abstract List<ProcessingStep> getProcessingStepList();

    protected abstract Set<Class<? extends Annotation>> getSupportedAnnotations();

}
