package com.studio.jframework.utils;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * @author Jason
 */
public class WebViewUtils {

    /**
     * A WebView setting which enable JavaScript ,DomStorage and file access.
     *
     * @param webView     The webView you wanna enable JavaScript
     * @param appCacheDir A String path to the directory containing Application Caches files.
     * @return The WebSettings instance
     */
    public static WebSettings enableJavaScript(WebView webView, String appCacheDir) {
        WebSettings webViewSettings = webView.getSettings();
        // wSet.setAppCacheMaxSize();
        webViewSettings.setJavaScriptEnabled(true);
        webViewSettings.setDomStorageEnabled(true);
        // String appCacheDir = this.getActivity().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webViewSettings.setAppCachePath(appCacheDir);
        webViewSettings.setAllowFileAccess(true);
        webViewSettings.setAppCacheEnabled(true);
        webView.setWebViewClient(new webViewClient());
        webViewSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        return webViewSettings;
    }

    /**
     * Sets whether the WebView loads pages in overview mode, that is,
     * zooms out the content to fit on screen by width.
     *
     * @param webView The webView you wanna enable adapting screen size automatically
     * @return The WebSettings instance
     */
    public static WebSettings adaptScreenSize(WebView webView) {
        WebSettings webViewSettings = webView.getSettings();
        webViewSettings.setLoadWithOverviewMode(true);
        webViewSettings.setUseWideViewPort(true);
        return webViewSettings;
    }

    static class webViewClient extends WebViewClient {
        /**
         * To enable click event happen inside the webView
         *
         * @param view The webView instance
         * @param url  The url to be loaded
         * @return True if the host application wants to leave the current WebView
         * and handle the url itself, otherwise return false.
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}