package com.example.lib_base_processor;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;

public class ProcessingEnv {
    public static ProcessingEnvironment processingEnvironment;
    public static boolean debug = false;
    private static Errors errors = new Errors();

    public static boolean hasErrors() {
        return errors.hasErrors();
    }

    public static void deliver() {
        errors.deliver(processingEnvironment.getMessager());
    }

    public static void debug(String format, Object... args) {
        if (debug) {
            System.out.println(String.format(format, args));
        }
    }

    public static class Errors {
        private final List<Error> errorList = new LinkedList<>();

        private Errors() {
        }

        public void addMissing(Element element, String reason) {
            this.errorList.add(new Error(element, String.format("Missing value: %s", reason)));
        }

        private void deliver(Messager messager) {
            for (Error error : errorList) {
                if (error.element != null) {
                    messager.printMessage(Diagnostic.Kind.ERROR, error.text, error.element);
                } else {
                    messager.printMessage(Diagnostic.Kind.ERROR, error.text);
                }
            }
        }

        private boolean hasErrors() {
            return !this.errorList.isEmpty();
        }

        static class Error {
            private final Element element;
            private final String text;

            public Error(Element element, String text) {
                this.element = element;
                this.text = text;
            }
        }
    }
}
