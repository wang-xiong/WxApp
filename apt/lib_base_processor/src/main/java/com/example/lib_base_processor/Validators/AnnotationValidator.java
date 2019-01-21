package com.example.lib_base_processor.Validators;

import com.google.auto.common.MoreElements;

import java.lang.annotation.Annotation;

import javax.lang.model.element.Element;

public abstract class AnnotationValidator extends Validator {
    @Override
    public boolean validate(Element element) {
        return MoreElements.isAnnotationPresent(element, getAnnotation());
    }

    protected abstract Class<? extends Annotation> getAnnotation();

}
