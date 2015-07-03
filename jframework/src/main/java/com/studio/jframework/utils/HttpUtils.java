package com.studio.jframework.utils;

import android.os.Environment;
import android.os.Handler;
import android.os.Message;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2015-07-01.
 */
public class HttpUtils {

    private static final int DOWNLOAD_STATE = 1;//正在下载

    public static String download(String url, String savePath, String fileName,
                                  Handler mHandler) {
        HttpGet get = new HttpGet(url);
        HttpClient httpClient = new DefaultHttpClient();
        httpClient.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT,
                10 * 1000); // 超时设置
        httpClient.getParams().setIntParameter(
                HttpConnectionParams.CONNECTION_TIMEOUT, 5000);// 连接超时
        try {
            HttpResponse respon = httpClient.execute(get);
            if (respon.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                InputStream in = respon.getEntity().getContent();
                //判断SD的状态
                if (Environment.getExternalStorageState().equals(
                        Environment.MEDIA_MOUNTED)) {
                    // 获取文件总长度
                    long total_length = respon.getEntity().getContentLength();
                    // 保存文件
                    File apkFile = new File(savePath, fileName);
                    FileOutputStream out = new FileOutputStream(apkFile);
                    // 下载数据
                    byte[] buffer = new byte[1024];
                    int b = 0;
                    int pLenght = 0;
                    while ((b = in.read(buffer)) != -1) {
                        // 更新进度条进度
                        pLenght += b;
                        int progress = (int) (((float) pLenght / total_length) * 100);
                        // 发送到异步任务更新进度条
                        Message message = new Message();
                        message.what = DOWNLOAD_STATE;//返回状态
                        message.arg1 = progress;//目前的进度
                        mHandler.sendMessage(message);
                        out.write(buffer, 0, b);
                    }
                    out.flush();
                    out.close();
                    in.close();
                    httpClient.getConnectionManager().shutdown();
                } else {
                    return "SD卡不存在或者处于挂挡时期";
                }
                return "ok";
            }
        } catch (IOException e) {
            return "网络异常";
        }
        return "连接失败";
    }
}
