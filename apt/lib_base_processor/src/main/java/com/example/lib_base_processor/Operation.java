package com.example.lib_base_processor;

import com.example.lib_base_processor.Validators.Validate;

import javax.lang.model.element.Element;

public interface Operation {
    boolean validate(Validate validate1, Validate validate2, Element element);
}
