package net.vipmro.search.core.utils;

import net.vipmro.search.core.exception.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * http工具
 *
 * @author fengxiangyang
 * @date 2018/11/30
 */
public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    public static final String CHARSET = "utf-8";
    /**
     * post
     *
     * @param url
     * @return
     */
    public static String post(String url) {
        return request(url, "POST", CHARSET);
    }

    /**
     * get
     *
     * @param url
     * @param logger
     * @return
     */
    public static String get(String url, Logger logger) {
        return request(url, "GET", CHARSET);
    }

    /**
     * 请求
     *
     * @param url
     * @param method
     * @return
     */
    public static String request(String url, String method) {
        return request(url, method, CHARSET);
    }

    /**
     * 请求
     *
     * @param url
     * @param method
     * @param charset
     * @return
     */
    public static String request(String url, String method, String charset) {
        HttpURLConnection httpConnection = null;
        InputStream dataIn = null;
        try {
            logger.info("request: " + url);
            httpConnection = (HttpURLConnection) new URL(url).openConnection();
            httpConnection.setConnectTimeout(30000);
            httpConnection.setReadTimeout(30000);
            httpConnection.setRequestMethod(method);
            dataIn = httpConnection.getInputStream();
            String strRead;
            BufferedReader reader = new BufferedReader(new InputStreamReader(dataIn, charset));
            StringBuilder result = new StringBuilder();
            while ((strRead = reader.readLine()) != null) {
                result.append(strRead).append('\n');
            }
            reader.close();
            logger.info("response: " + result.toString());
            return result.toString();
        } catch (IOException e) {
            Integer code = null;
            if (httpConnection != null) {
                try {
                    code = httpConnection.getResponseCode();
                } catch (IOException e1) {
                }
            }
            if (code != null) {
                logger.error("请求网络错误, " + code + "：" + url, e);
                throw new HttpException("请求网络错误, " + code + "：" + url, e);
            } else {
                logger.error("请求网络错误：" + url, e);
                throw new HttpException("请求网络错误：" + url, e);
            }
        } finally {
            if (dataIn != null) {
                try {
                    dataIn.close();
                } catch (IOException e) {
                }
            }
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
    }
}
