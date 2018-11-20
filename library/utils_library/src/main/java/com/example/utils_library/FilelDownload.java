package com.example.utils_library;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/**
 * Created by wangxiong on 2018/7/22.
 */

public class FilelDownload {

    public static File saveFile(ResponseBody responseBody, String destFileDir, String fileName) {
        InputStream in = null;
        FileOutputStream fos = null;

        File fileDir = new File(destFileDir);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }

        File file = new File(fileDir, fileName);

        try {
            long sum =0;
            long toatal = responseBody.contentLength();

            in = responseBody.byteStream();
            fos = new FileOutputStream(file);
            byte[] buff = new byte[2048];
            int len;
            while ((len = in.read(buff)) != -1) {
                sum += len;
                fos.write(buff, 0, len);
                final long finalSum = sum;

                //写入的进度
                // onProgress((int) (finalSum * 100 / total),total);
            }
            fos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return file;
    }
}
