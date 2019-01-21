package com.example.lib_base_processor;

public interface ProcessingStep {
    void process(AnnotatedElementHashMap annotatedElementHashMap, JavaFilesWriter javaFilesWriter);
}
