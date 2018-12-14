package com.example.base_interface;

import java.util.HashMap;
import java.util.Set;

public class ComponentManager {

    private static HashMap<String, IComponent> CATEGORIES = new HashMap<>();


    static void register(IComponent component) {
        if (component != null) {
            CATEGORIES.put(component.getName(), component);
        }
    }


    public static Set<String> getComponentNames() {
        return CATEGORIES.keySet();
    }
}