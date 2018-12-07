package com.example.app_okhttp;

import com.example.app_okhttp.ProgressListener;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

public class ProgressResponseBody extends ResponseBody {
    private ResponseBody responseBody;
    private ProgressListener progressListener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
        this.responseBody = responseBody;
        this.progressListener = listener;
    }

    @Override
    public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override
    public long contentLength() {
        return responseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(getSource(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source getSource(Source source) {
        return new ForwardingSource(source) {
            long total = 0;
            long sum = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long len = super.read(sink, byteCount);

                if (total == 0) {
                    total = responseBody.contentLength();
                }
                sum += (len == -1 ? 0 : len);

                int progress = (int) (sum * 1.0f / sum * 100);
                if (len == -1) {
                    progressListener.onDone(total);
                } else {
                    progressListener.onProgress(progress);
                }
                return len;
            }
        };
    }
}
