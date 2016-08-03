package cam.aedes;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}