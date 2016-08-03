package aed.hivrisk;

import cam.aedes.CWebViewClient;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.webkit.WebView;

public class WebContent extends HivRisk {
	
	public WebView myWebView;
	public String navurl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Bundle extras = getIntent().getExtras(); 
        if(extras !=null)
        {
        	if(extras.containsKey("url")){
        		navurl = extras.getString("url");
        	}
        	
        }           

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.webcontent);
        myWebView = ((WebView)findViewById(R.id.wbContent));
        myWebView.setWebViewClient(new CWebViewClient());
        ((View)findViewById(R.id.btnNavBack)).setOnClickListener(BackNav);
        
        startNavigation();
    }
    
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    
    public void startNavigation() {
    	Log.v("NAV TO::::", navurl);
    	myWebView.loadUrl(navurl);
    	
    }
       
    public OnClickListener BackNav = new OnClickListener() {
        public void onClick(View v) {
        	if (myWebView.canGoBack()) {
                myWebView.goBack();
            } else {
            	finish();
            }
        }
    };
}