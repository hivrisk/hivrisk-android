package cam.async;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import cam.aedes.CActivity;

public class DownloadAsyncImage extends AsyncTask<String, Void, String> {

	CActivity t;
	Context c;
	InputStream is;
	
	public DownloadAsyncImage(CActivity cactivity){
		t = cactivity;
	}
    
	protected String doInBackground(String... str) {
		try {
    		URL url = new URL(str[0]);
    		is = (InputStream) url.getContent();

		} catch (IOException e) { e.printStackTrace(); 
		 	Log.v("ERROR DOWNLOAD", str[0]); 	
		} catch (Exception e) { e.printStackTrace(); 
			Log.v("ERROR DOWNLOAD", str[0]); 	
		}		
        return str[1];
    }

    protected void onPostExecute(String key) {
		if (is != null) {
			SetAsyncDrawable(key);
		}
    	
    }
    
    public void SetAsyncDrawable(String id) {
    	ImageView img = (ImageView) t.findViewById(Integer.parseInt(id));
		try {
	    	Drawable d = Drawable.createFromStream(is, "Async");
    		img.setImageDrawable(d);
    	} catch (Exception e) {
    		//img.setImageResource(R.drawable.errorload);
    	}
    	              
    }
    
}
