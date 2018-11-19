package com.example.plugin1;

import com.example.app_plugin_module.ICommonInterface;

public class PluginResources implements ICommonInterface {
    @Override
    public String getString() {
        return "plugin1";
    }

    @Override
    public int getDrawable() {
        return R.mipmap.ic_launcher;
    }
}
