package com.example.lib_wx_processor;

import com.example.lib_base_processor.BaseAnnotationProcessor;
import com.example.lib_base_processor.ProcessingStep;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public class WxAnnotationProcessor extends BaseAnnotationProcessor {

    @Override
    protected List<ProcessingStep> getProcessingStepList() {
        return null;
    }

    @Override
    protected Set<Class<? extends Annotation>> getSupportedAnnotations() {
        return null;
    }
}
