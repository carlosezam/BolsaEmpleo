package com.ittap.bolsadeempleo;

import android.app.Activity;
import android.app.DownloadManager;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ittap.bolsadeempleo.api.SharedPrefManager;
import com.ittap.bolsadeempleo.api.URLs;

public class WebActivity extends AppCompatActivity {

    WebView webView;
    Usuario usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        usuario = SharedPrefManager.getInstance(this).getUsuario();

        webView = (WebView) findViewById(R.id.webView);

        final Activity activity = this;

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                activity.setProgress( newProgress * 1000 );
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(activity, "error", Toast.LENGTH_LONG).show();
            }
        });

        webView.loadUrl(URLs.URI_PDF + "?id_usuario="+String.valueOf(usuario.id));

        webView.setDownloadListener(new DownloadListener()
        {

            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                //for downloading directly through download manager
                final DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
                request.allowScanningByMediaScanner();

                request.setMimeType(mimetype);
                //------------------------COOKIE------------------------
                String cookies = CookieManager.getInstance().getCookie(url);
                request.addRequestHeader("cookie", cookies);
                //------------------------COOKIE------------------------

                request.addRequestHeader("User-Agent", userAgent);
                request.setDescription("Downloading file...");
                request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimetype));
                request.allowScanningByMediaScanner();
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, URLUtil.guessFileName(url, contentDisposition, mimetype));
                final DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);

                new Thread("Browser download") {
                    public void run() {
                        dm.enqueue(request);
                    }
                }.start();
            }
        });
    }
}
