package com.example.app_okhttp.okhttp;

import android.net.Uri;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;

public class SimpleHttpClient {

    private Builder mBuilder;

    private SimpleHttpClient(Builder builder) {
        this.mBuilder = builder;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public void enqueue(BaseCallBack baseCallBack) {
        OkHttpManager.getInstance().request(this, baseCallBack);
    }

    public Request getRequest() {
        Request.Builder builder = new Request.Builder();
        switch (mBuilder.method) {
            case "Get":
                builder.get();
                builder.url(buildGetRequestParams());
                break;
            case "Post":
                builder.post(buildRequestBody());
                builder.url(mBuilder.url);
                break;
        }
        return builder.build();
    }

    private String buildGetRequestParams() {
        if (mBuilder.paramsList.size() > 0) {
            return this.mBuilder.url;
        }
        Uri.Builder builder = Uri.parse(mBuilder.url).buildUpon();
        for (RequestParams params : mBuilder.paramsList) {
            builder.appendQueryParameter(params.getKey(), params.getValue() == null ? "" : params.getValue());
        }
        String url = builder.build().toString();
        Log.e("SimpleHttpClient", "the get url ：" + url);
        return url;
    }

    private RequestBody buildRequestBody() {
        RequestBody requestBody;
        if (mBuilder.isPostJson) {
            JSONObject jsonObject = new JSONObject();
            try {
                for (RequestParams params : mBuilder.paramsList) {
                    jsonObject.put(params.getKey(), params.getValue());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            String jsonParams = jsonObject.toString();
            requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonParams);
        } else {
            FormBody.Builder formBodyBuilder = new FormBody.Builder();
            for (RequestParams params : mBuilder.paramsList) {
                formBodyBuilder.add(params.getKey(), params.getValue() == null ? "" : params.getValue());
            }
            requestBody = formBodyBuilder.build();
        }
        return requestBody;
    }

    public static class Builder {
        private String method;
        private String url;
        private boolean isPostJson;
        private List<RequestParams> paramsList;

        public Builder() {
            this.method = "Get";
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder get() {
            this.method = "Get";
            return this;
        }

        public Builder post() {
            this.method = "Post";
            return this;
        }

        public Builder json() {
            this.isPostJson = true;
            return post();
        }

        public Builder addRequestParams(String key, String value) {
            if (paramsList == null) {
                paramsList = new ArrayList<>();
            }
            paramsList.add(new RequestParams(key, value));
            return this;
        }

        public SimpleHttpClient build() {
            return new SimpleHttpClient(this);
        }
    }
}
