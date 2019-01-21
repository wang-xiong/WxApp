package com.example.lib_base_processor.Validators;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;

public abstract class KindValidator extends Validator{

    @Override
    public boolean validate(Element element) {
        return this.is() == element.getKind();
    }

    protected abstract ElementKind is();
}
