package com.example.utils_library;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

public class AppInfoUtil {

    /**
     * 根据包名判断应用是否安装
     *
     * @param context     请求调用的Context实例
     * @param packageName 请求调用的包名
     * @return 返回此包名应用是否安装
     */
    public static boolean isAppInstallByPackageName(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return false;
        }
        List<PackageInfo> packageInfoList = context.getApplicationContext().getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfoList) {
            if (packageInfo.packageName.equals(packageName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 根据包名获取应用的名称
     *
     * @param context     请求调用的Context实例
     * @param packageName 请求调用的包名
     * @return 返回此包名应用的名称
     */
    public static String getAppNameByPackageName(Context context, String packageName) {
        if (context == null || TextUtils.isEmpty(packageName)) {
            return null;
        }
        PackageManager pm = context.getPackageManager();
        try {
            return pm.getApplicationLabel(pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA)).toString();
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private enum ApplicationType {
        //所有应用、非系统应用、系统应用
        AllApplication, NonSystemApplication, SystemApplication
    }

    /**
     * 获取设备的应用信息
     */
    private static List<AppInfo> getApplicationInfo(Context context, ApplicationType applicationType) {
        List<AppInfo> appInfoList = new ArrayList<>();
        List<PackageInfo> packageInfoList = context.getPackageManager().getInstalledPackages(0);
        for (PackageInfo packageInfo : packageInfoList) {
            AppInfo appInfo = new AppInfo();
            appInfo.setApplicationName(packageInfo.applicationInfo.loadLabel(context.getPackageManager()).toString());
            appInfo.setPackageName(packageInfo.packageName);
            appInfo.setApplicationIcon(packageInfo.applicationInfo.loadIcon(context.getPackageManager()));
            appInfo.setVersion(packageInfo.versionName);
            appInfo.setSourceDir(packageInfo.applicationInfo.sourceDir);
            appInfo.setLastUpdateTime(packageInfo.lastUpdateTime);
            switch (applicationType) {
                case AllApplication:
                    appInfoList.add(appInfo);
                    break;
                case SystemApplication:
                    if (isSystemApplication(packageInfo)) {
                        appInfoList.add(appInfo);
                    }
                    break;
                case NonSystemApplication:
                    if (!isSystemApplication(packageInfo)) {
                        appInfoList.add(appInfo);
                    }
                    break;
            }
        }
        return appInfoList;
    }

    /**
     * 判断是否是系统应用
     */
    private static Boolean isSystemApplication(PackageInfo packageInfo) {
        return (packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0;
    }

    /**
     * 获取设备所有的应用
     */
    public static List<AppInfo> getAllApplication(Context context) {
        return getApplicationInfo(context, ApplicationType.AllApplication);
    }

    /**
     * 获取设备所有的系统应用
     */
    public static List<AppInfo> getAllSystemApplication(Context context) {
        return getApplicationInfo(context, ApplicationType.SystemApplication);
    }

    /**
     * 获取设备所有的非系统应用
     */
    public static List<AppInfo> getAllNonSystemApplication(Context context) {
        return getApplicationInfo(context, ApplicationType.NonSystemApplication);
    }

}
