package com.example.model_a;

import java.util.ArrayList;
import java.util.List;

public class OtherManager {

    private final List<IOther> LIST = new ArrayList<>();

    public OtherManager() {
        init();
    }

    private void init() {
    }

    private void registerOther(IOther other) {
        if (other !=  null) {
            LIST.add(other);
        }
    }

    public List<IOther> getALL() {
        return LIST;
    }
}
