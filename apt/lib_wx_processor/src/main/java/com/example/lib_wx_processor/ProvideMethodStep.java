package com.example.lib_wx_processor;

import com.example.lib_base_processor.AnnotatedElementHashMap;
import com.example.lib_base_processor.ElementFinder;
import com.example.lib_base_processor.JavaFilesWriter;
import com.example.lib_base_processor.ProcessingEnv;
import com.example.lib_base_processor.ProcessingStep;
import com.example.lib_wx_annotation.Provide;
import com.example.lib_wx_annotation.ProviderModule;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.util.SimpleElementVisitor6;

public class ProvideMethodStep implements ProcessingStep {

    @Override
    public void process(AnnotatedElementHashMap annotatedElementHashMap, JavaFilesWriter javaFilesWriter) {
        ElementFinder provideMethodFinder = ElementFinder.newInstance()
                .andWithAnnotation(Provide.class)
                .andKindOf(ElementKind.METHOD);

        List<Element> providedMethod = new ArrayList<>();

        ElementFinder topClassFinder = ElementFinder.newInstance(ProviderModule.class)
                .andKindOf(ElementKind.CLASS)
                .scan(provideMethodFinder);

        ElementFinder dependenciesClassFinder = ElementFinder.newInstance(ProviderModule.class)
                .andKindOf(ElementKind.CLASS)
                .getTypeAnnotationValue(ProviderModule.class, "dependencies")
                .scan(provideMethodFinder);

        providedMethod.addAll(topClassFinder.find(annotatedElements));
        providedMethod.addAll(dependenciesClassFinder.find(annotatedElements));

        for (Element element : providedMethod) {
            Scope scope = Scope.get(element.accept(EXECUTABLE_ELEMENT_VISITOR, null));
            ProcessingEnv.debug("provide %s scope %s", element, scope);
            ProvideMethod provideMethod = new ProvideMethod(element.accept(EXECUTABLE_ELEMENT_VISITOR, null));
            Finder.addProvideMethod(scope, provideMethod);
        }
    }

    private static final ElementVisitor<ExecutableElement, Void> EXECUTABLE_ELEMENT_VISITOR = new SimpleElementVisitor6<ExecutableElement, Void>() {
        protected ExecutableElement defaultAction(Element e, Void p) {
            throw new IllegalArgumentException();
        }

        public ExecutableElement visitExecutable(ExecutableElement e, Void p) {
            return e;
        }
    };
}
