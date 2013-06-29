package com.wookoozoo.networkproxy;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.mime.FormBodyPart;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpClientNoPoolUtil {
    private static final Logger            LOG        = LoggerFactory.getLogger(HttpClientNoPoolUtil.class);

    private static final DefaultHttpClient httpclient = new DefaultHttpClient();
    static {
        httpclient.getParams().setParameter(ClientPNames.COOKIE_POLICY, CookiePolicy.BROWSER_COMPATIBILITY);
    }

    public static String post(String url, List<? extends NameValuePair> nvps, HttpContext localContext,
                              Header[] headers, String encoding) throws IOException {
        long start = System.currentTimeMillis();

        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeaders(headers);
        httpPost.setEntity(new UrlEncodedFormEntity(nvps, encoding));

        HttpEntity entity = null;
        try {
            HttpResponse response = httpclient.execute(httpPost, localContext);
            entity = response.getEntity();
            InputStream ins = entity.getContent();
            String result = IOUtils.toString(ins, encoding);
            if (LOG.isDebugEnabled()) {
                LOG.debug("****** http client invoke (Post method), url: " + url + ", nameValuePair: " + nvps
                        + ", result: " + result);
            }
            return result;
        } finally {
            EntityUtils.consume(entity);
            httpPost.releaseConnection();
            if (LOG.isDebugEnabled()) {
                LOG.debug("****** http client invoke (Post method), url: " + url + ", nameValuePair: " + nvps
                        + ", time: " + String.valueOf(System.currentTimeMillis() - start) + "ms.");
            }
        }
    }

    public static String postParts(String url, List<FormBodyPart> parts, HttpContext localContext, Charset encoding)
            throws IOException {
        long start = System.currentTimeMillis();

        HttpPost httpPost = new HttpPost(url);
        MultipartEntity reqEntity = new MultipartEntity();
        for (FormBodyPart part : parts) {
            reqEntity.addPart(part);
        }
        httpPost.setEntity(reqEntity);

        HttpEntity entity = null;
        try {
            HttpResponse response = httpclient.execute(httpPost);
            entity = response.getEntity();
            InputStream ins = entity.getContent();
            String result = IOUtils.toString(ins, encoding);
            if (LOG.isDebugEnabled()) {
                LOG.debug("****** http client invoke (Post method), url: " + url + ", result: " + result);
            }
            return result;
        } finally {
            EntityUtils.consume(entity);
            httpPost.releaseConnection();
            if (LOG.isDebugEnabled()) {
                LOG.debug("****** http client invoke (Post method), url: " + url + ", time: "
                        + String.valueOf(System.currentTimeMillis() - start) + "ms.");
            }
        }
    }

    public static String get(String url, List<? extends NameValuePair> nvps, HttpContext localContext, String encoding)
            throws IOException {
        long start = System.currentTimeMillis();

        if (encoding == null) {
            encoding = "UTF-8";
        }
        //构造nvps为queryString
        if (nvps != null && nvps.size() > 0) {
            String query = URLEncodedUtils.format(nvps, encoding);
            url += "?" + query;
        }
        HttpGet httpGet = new HttpGet(url);

        HttpEntity entity = null;
        try {
            HttpResponse response = httpclient.execute(httpGet, localContext);
            entity = response.getEntity();
            InputStream ins = entity.getContent();
            String result = IOUtils.toString(ins, encoding);
            if (LOG.isDebugEnabled()) {
                LOG.debug("****** http client invoke (Get method), url: " + url + ", nameValuePair: " + nvps
                        + ", result: " + result);
            }
            return result;
        } finally {
            EntityUtils.consume(entity);
            httpGet.releaseConnection();
            if (LOG.isDebugEnabled()) {
                LOG.debug("****** http client invoke (Get method), url: " + url + ", nameValuePair: " + nvps
                        + ", time: " + String.valueOf(System.currentTimeMillis() - start) + "ms.");
            }
        }
    }
}
