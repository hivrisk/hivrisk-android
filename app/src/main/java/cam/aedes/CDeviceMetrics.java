package cam.aedes;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.WindowManager;

public class CDeviceMetrics {
	
	public int dDensity;
	public int dWidth;
	public int dHeight;
	public int dMayorAxis;
	public int screensize;
	public boolean dLandscape;
	public boolean dTablet;
	Context context;
	Activity a;
	
	public CDeviceMetrics(Context yourcontext, Activity activity) {
		context = yourcontext;
		a = activity;
		SetUpDeviceMetrics();
	}
	
	public void SetUpDeviceMetrics() {
    	WindowManager mWinMgr = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
    	DisplayMetrics dpm = new DisplayMetrics();
    	a.getWindowManager().getDefaultDisplay().getMetrics(dpm);
    	dDensity = dpm.densityDpi;
    	dWidth = mWinMgr.getDefaultDisplay().getWidth();
    	dHeight = mWinMgr.getDefaultDisplay().getHeight();
    	
    	dLandscape = dWidth < dHeight;
    	
    	if (dLandscape) {
    		dMayorAxis = dHeight;
    	} else {
    		dMayorAxis = dWidth;
    	}
    	
    	if (dMayorAxis>1024) { screensize = 4; }
    	if (dMayorAxis>860) { screensize = 3; }
    	if (dMayorAxis>640) { screensize = 2; }
    	if (dMayorAxis>470) { screensize = 1; }
    	if (dMayorAxis>426) { screensize = 0; }
    	    	
	}
	
	public int PixelsToDP(int px) {
		////Log.v("DENSITY::::::::", dDensity+"d");
		//int r = (int)(pixels*(dDensity/160));
		//Log.v("DPI::::::::", r+" dp");
		DisplayMetrics metrics = new DisplayMetrics();
		a.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		float logicalDensity = metrics.density;
		return (int) Math.ceil(px * logicalDensity);
	}
	
	public int DPToPixels(int dp) {
		//Log.v("DENSITY::::::::", dDensity+"d");
		//int r = (int)(dp/(dDensity/160));
		//Log.v("DPI::::::::", r+" dp");
		//Resources r = a.getResources();
		//float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics());
		//return (int) px;
		DisplayMetrics metrics = new DisplayMetrics();
		a.getWindowManager().getDefaultDisplay().getMetrics(metrics);
		float logicalDensity = metrics.density;
		return (int) Math.ceil(dp * logicalDensity);
	}
	
	public boolean AmITablet() {
		if (dMayorAxis>900) { return true; } 
		return false;
	}

}
