package com.example.module_welfare.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wangxiong on 2018/7/22.
 */

public class PreviewBean implements Parcelable{

    private String urlString;

    public PreviewBean(String url) {
        this.urlString = url;
    }

    protected PreviewBean(Parcel in) {
        urlString = in.readString();
    }



    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    public static final Creator<PreviewBean> CREATOR = new Creator<PreviewBean>() {
        @Override
        public PreviewBean createFromParcel(Parcel in) {
            return new PreviewBean(in);
        }

        @Override
        public PreviewBean[] newArray(int size) {
            return new PreviewBean[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.urlString);
    }
}
