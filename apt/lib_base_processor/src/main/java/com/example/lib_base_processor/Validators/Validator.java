package com.example.lib_base_processor.Validators;



import javax.lang.model.element.Element;

public abstract class Validator implements Validate {


    public final Validator operation(final Validator.Operation op, final Validator validator) {
        return new Validator() {
            @Override
            public boolean validate(Element element) {
                return op.validate(Validator.this, validator, element);
            }
        };
    }

    public enum Operation implements com.example.lib_base_processor.Operation {

        and {
            @Override
            public boolean validate(Validate validate1, Validate validate2, Element element) {
                return validate1.validate(element) && validate1.validate(element);
            }
        }
    }
}
