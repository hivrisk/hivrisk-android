package cam.async;

import java.net.URL;

import org.json.JSONArray;
import org.xml.sax.InputSource;

import cam.aedes.CActivity;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

public class DownloadOptsSelect extends AsyncTask<String, Void, String> {
	
	CActivity a;
	Context context;
	int destiny;
	InputSource nis;
	String res;
	JSONArray jArray;
	String prebtn;
	String skind;
	
	public DownloadOptsSelect(Context main_context, CActivity cactivity, String kind) {

    }
	
	DownloadOptsSelect(Context main_context) {

	}
	
    protected String doInBackground(String... str) {
    	try {
    		URL url = new URL(str[0]);

		} catch (Exception e) {  
    			Log.v("PARSE ERROR:::::::::", e.toString());
    			res =" ";
		} 
        return res;
    }

    protected void onPostExecute(String result) {

    }
    
    
}
