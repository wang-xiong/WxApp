package com.example.lib_base_processor;

import com.squareup.javapoet.JavaFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.processing.Filer;

public class JavaFilesWriter {
    private boolean isStop;
    private final Filer filer;
    private final List<JavaFile> javaFileList;

    public JavaFilesWriter(Filer filer) {
        this.filer = filer;
        javaFileList = new ArrayList<>();
    }

    public void write(JavaFile javaFile) {
        javaFileList.add(javaFile);
    }

    public void write(Collection<JavaFile> javaFiles) {
        javaFileList.addAll(javaFiles);
    }

    public boolean isStop() {
        return this.isStop;
    }

    public void flush() {
        isStop = ProcessingEnv.hasErrors();
        if (isStop) {
            ProcessingEnv.deliver();
        } else {
            for (JavaFile javaFile : javaFileList) {
                isStop = ProcessingEnv.hasErrors();
                if (isStop) {
                    ProcessingEnv.deliver();
                    break;
                }
                try {
                    javaFile.writeTo(filer);
                } catch (IOException e) {
                    ProcessingEnv.debug("IOException write java file %s", javaFile.typeSpec.name);
                    e.printStackTrace();
                }
            }
        }
    }
}
