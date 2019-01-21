package com.example.lib_base_processor;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Set;

import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;

public class AnnotatedElementHashMap extends HashMap<Class<? extends Annotation>, Set<? extends Element>> {

    public AnnotatedElementHashMap(Set<Class<? extends Annotation>> annotationClassSet, RoundEnvironment roundEnvironment) {
        for (Class<? extends Annotation> annotationClass : annotationClassSet) {
            this.put(annotationClass, roundEnvironment.getElementsAnnotatedWith(annotationClass));
        }
    }
}
