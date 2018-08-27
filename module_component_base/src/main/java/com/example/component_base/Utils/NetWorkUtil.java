package com.example.component_base.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

/**
 * Created by wangxiong on 2018/8/8.
 */

public class NetWorkUtil {

    public Context mContext;
    private NetWorKState mState;
    private ConnectionReceiver mConnectionReceiver;

    public NetWorkUtil(Context mContext) {
        this.mContext = mContext.getApplicationContext();
        this.mConnectionReceiver = new ConnectionReceiver();
        this.mState = getNetWorKState();
    }

    public enum NetWorKState {
        Unusable,
        TwoG,
        ThreeG,
        FourG,
        Wifi
    }

    public NetWorKState getNetWorKState() {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isAvailable()) {
            return NetWorKState.Unusable;
        }
        final  int type = networkInfo.getType();
        if (type == ConnectivityManager.TYPE_WIFI) {
            return NetWorKState.Wifi;
        }
        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
        final int subType = telephonyManager.getNetworkType();
        switch (subType) {
            case TelephonyManager.NETWORK_TYPE_GPRS:
            case TelephonyManager.NETWORK_TYPE_EDGE:
            case TelephonyManager.NETWORK_TYPE_CDMA:
            case TelephonyManager.NETWORK_TYPE_1xRTT:
            case TelephonyManager.NETWORK_TYPE_IDEN:
            case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                return NetWorKState.TwoG;
            case TelephonyManager.NETWORK_TYPE_UMTS:
            case TelephonyManager.NETWORK_TYPE_EVDO_0:
            case TelephonyManager.NETWORK_TYPE_EVDO_A:
            case TelephonyManager.NETWORK_TYPE_HSDPA:
            case TelephonyManager.NETWORK_TYPE_HSUPA:
            case TelephonyManager.NETWORK_TYPE_HSPA:
            case TelephonyManager.NETWORK_TYPE_EVDO_B:
            case TelephonyManager.NETWORK_TYPE_EHRPD:
            case TelephonyManager.NETWORK_TYPE_HSPAP:
                return NetWorKState.ThreeG;
            case TelephonyManager.NETWORK_TYPE_LTE:
                return NetWorKState.FourG;
            default:
                return NetWorKState.TwoG;
        }

    }

    /**
     * 监听网络变化
     */
    private class ConnectionReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            NetWorKState state = getNetWorKState();
            if (mState != state) {
                //状态发送变化
            }
        }
    };

    public void registerReceiver() {
        IntentFilter filter = new IntentFilter(
                ConnectivityManager.CONNECTIVITY_ACTION);
        mContext.registerReceiver(mConnectionReceiver, filter);
    }

    public void unregisterReceiver() {
        mContext.unregisterReceiver(mConnectionReceiver);
    }

}
