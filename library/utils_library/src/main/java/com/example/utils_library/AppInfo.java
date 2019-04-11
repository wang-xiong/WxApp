package com.example.utils_library;

import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;

public class AppInfo {

    //应用名
    private String applicationName;

    //应用包名
    private String packageName;

    //应用图标
    private Drawable applicationIcon;

    //版本号
    private String version;

    //最后更新时间
    private long lastUpdateTime;

    //apk路径
    private String sourceDir;

    //apk大小 MB
    public long getApkSize() {
        if (TextUtils.isEmpty(sourceDir)) {
            return 0;
        }
        File apkFile = new File(sourceDir);
        return apkFile.length() / 1024 / 1024;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public Drawable getApplicationIcon() {
        return applicationIcon;
    }

    public void setApplicationIcon(Drawable applicationIcon) {
        this.applicationIcon = applicationIcon;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getSourceDir() {
        return sourceDir;
    }

    public void setSourceDir(String sourceDir) {
        this.sourceDir = sourceDir;
    }

    @NonNull
    @Override
    public String toString() {
        return "AppInfo{" +
                "applicationName='" + applicationName + '\'' +
                ", packageName='" + packageName + '\'' +
                ", applicationIcon=" + applicationIcon +
                ", version='" + version + '\'' +
                ", lastUpdateTime=" + lastUpdateTime +
                ", sourceDir='" + sourceDir + '\'' +
                '}';
    }

}