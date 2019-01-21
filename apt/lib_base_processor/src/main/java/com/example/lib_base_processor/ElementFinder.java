package com.example.lib_base_processor;

import com.example.lib_base_processor.Validators.AnnotationValidator;
import com.example.lib_base_processor.Validators.KindValidator;
import com.example.lib_base_processor.Validators.Validator;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.util.ElementScanner7;

public class ElementFinder {
    private Validator validator;
    private final Class<? extends Annotation> annotation;

    public ElementFinder(Class<? extends Annotation> annotation) {
        this.validator = NO_VALIDATOR;
        this.annotation = annotation;
    }

    public static ElementFinder newInstance() {
        return newInstance(null);
    }

    public static ElementFinder newInstance(Class<? extends Annotation> annotation) {
        return new ElementFinder(annotation);
    }

    private static final Validator NO_VALIDATOR = new Validator() {
        public boolean validate(Element element) {
            return true;
        }
    };

    public ElementFinder andWithAnnotation(Class<? extends Annotation> annotation) {
        return this.withAnnotation(Validator.Operation.and, annotation);
    }

    protected List<Element> find(Element element) {
        return validator.validate(element) ? Collections.singletonList(element) : new ArrayList<Element>();
    }

    public ElementFinder andKindOf(ElementKind elementKind) {
        return kindOf(Validator.Operation.and, elementKind);
    }

    public ElementFinder scan(final ElementFinder searcher) {
        return (new ElementFinder.Decorator.Builder(this))
                .post(new ElementFinder(this.annotation) {

                    @Override
                    protected List<Element> find(Element element) {
                        final List<Element> result = new ArrayList<>();
                        element.accept(new ElementScanner7<List<Element>, Void>() {
                            @Override
                            public List<Element> scan(Element element, Void aVoid) {
                                List<Element> found = searcher.find(element);
                                if (found != null && !found.isEmpty()) {
                                    result.addAll(found);
                                }
                                return result;
                            }
                        }, null);
                        return result;
                    }

                }).build();
    }

    private ElementFinder kindOf(Validator.Operation op, final ElementKind kind) {
        return validator(op, new KindValidator() {

            @Override
            protected ElementKind is() {
                return kind;
            }
        });
    }

    private ElementFinder withAnnotation(Validator.Operation op, final Class<? extends Annotation> annotation) {
        return validator(op, new AnnotationValidator() {

            @Override
            protected Class<? extends Annotation> getAnnotation() {
                return annotation;
            }
        });
    }

    private ElementFinder validator(Validator.Operation op, Validator validator) {
        this.validator = validator.operation(op, validator);
        return this;
    }

    private static class Decorator extends ElementFinder {
        private final ElementFinder decrated;
        private final ElementFinder post;

        private Decorator(Builder builder) {
            super(builder.decreted.annotation);
            this.decrated = builder.decreted;
            this.post = builder.post;
        }

        private static final class Builder {
            private ElementFinder decreted;
            private ElementFinder post;

            public Builder(ElementFinder val) {
                this.decreted = val;
            }

            public Builder post(ElementFinder val) {
                this.post = val;
                return this;
            }

            public Decorator build() {
                return new Decorator(this);
            }
        }

    }
}
