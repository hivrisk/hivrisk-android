package cam.aedes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import org.xml.sax.InputSource;

import android.os.AsyncTask;
import android.util.Log;


public class DownloadFilesTask extends AsyncTask<String, Void, String> {
	
	CActivity c;
	InputSource isp;
	boolean ErrorOcurred = false;
	String param;
	String murl;
	
	public DownloadFilesTask(CActivity ca, String url) {
		c = ca;
		this.murl = url;
		Log.v("URL::::::::::::::::::::::::",murl);
	}
	
	
    protected String doInBackground(String... str) {
    	if (str.length>2) { param = str[2]; }
    	Log.v("URL::::::::::::::::::::::::",murl);
    	try {
    		URL url = new URL(str[0]);
    		URLConnection conn = url.openConnection();
    		conn.setConnectTimeout(8000);
    		conn.setDefaultUseCaches(false);
        	conn.setReadTimeout(12000);
        	isp = null;
        	isp = new InputSource(conn.getInputStream());
			
		} catch (IOException e) { 
			 e.printStackTrace(); 
			 Log.v("ERROR DOWNLOAD::::::::::::::::::", str[0]);
			 c.ErrorOcurred=true;
		} catch (Exception eu) {
			Log.v("ERROR DOWNLOAD::::::::::::::::::", str[0]);
			c.ErrorOcurred=true;
		}
        return str[1];
    }

    protected void onPostExecute(String result) {  
    	if (isp!=null) {
	    	String res = StreamToString(isp.getByteStream()); 
			c.completed_download_callback(res, result, param);
    	} else {
    		c.ErrorOcurred=true;
    		c.completed_download_callback("", result, param);
    	}
		
    }
    
    public String StreamToString(InputStream is) {
		
    	if (is != null) {
    		BufferedReader r = new BufferedReader(new InputStreamReader(is), (8*2048));
    		StringBuilder total = new StringBuilder();
    		String line;
    		try {
    			while ((line = r.readLine()) != null) {
    			    total.append(line);
    			}
    		} catch (IOException e) {
    			Log.v("STREAM TO STRING ERROR:::", e.toString());
    			e.printStackTrace();
    		} catch (Exception e) {
    			Log.v("STREAM TO STRING ERROR:::", e.toString());
    		}
    	  
    		Log.v("STREAM TO STRING LENGHT:::::", total.length()+"");
    	  return total.toString();
    	  //return "";
    	}  else { 
    		return "";
    	}
	}
    
    
}