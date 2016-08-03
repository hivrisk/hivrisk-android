package cam.aedes;

import java.util.Date;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import aed.hivrisk.R;
import cam.async.DownloadAsyncImage;

public class CInterface {
	
	CActivity a;
	Context c;
	int Elementid;
	
	public CInterface(Context context, CActivity ca) {
		a = ca;
		c = context;
	}
	
	public void LoadWebImagetoView(int img, String iurl) {
     	ImageView ivPImg = (ImageView) a.findViewById(img);
     	//ivPImg.setImageResource(R.drawable.cargando);
     	//ivPImg.setVisibility(0);
    	new DownloadAsyncImage(a).execute(iurl, img+"");    	
	     	
	}
	
    public ImageView NewWebImage(int par, int w, int h, String imageurl, String tag, OnClickListener listener){
    	Context context = c;
    	ViewGroup ptv= (ViewGroup) a.findViewById(par);
    	ImageView nimage = new ImageView(context);
    	nimage.setLayoutParams(new LayoutParams(w,h));
    	//nimage.setImageResource(R.drawable.cargando);
    	if (!tag.equalsIgnoreCase("void")) {
    		//nimage.setTag(tag);
	    	//nimage.setOnClickListener(listener);
	    }

    	nimage.setId(GenerateID());
    	new DownloadAsyncImage(a).execute(imageurl, nimage.getId()+"");    	
     	ptv.addView(nimage);
   	
    	return nimage;
    }
	
	public TextView NewTV(int par, int styleres, String content, int LeftMargin, int TopMargin, int RightMargin, int BottomMargin){
		Context context = c;
		ViewGroup ptv= (ViewGroup) a.findViewById(par);
		TextView ntv = new TextView(context);
		ntv.setTextAppearance(context, styleres);
		LinearLayout.LayoutParams margin = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
		margin.setMargins(LeftMargin, TopMargin, RightMargin, BottomMargin); 
		if (content.length()>1) {
			ntv.setText(Html.fromHtml(content.trim()));
		} else { 
			ntv.setVisibility(8); 
		}
		int newid;
		Date ndat = new Date();
		newid = (int) ndat.getTime();
		newid = Math.abs(newid);
		ntv.setId(newid);
		ntv.setGravity(Gravity.CLIP_VERTICAL);
		ptv.addView(ntv, margin);
		return ntv;
	}
	
	public TextView NewTV(int par, int styleres, String content){
		Context context = c;
		ViewGroup ptv= (ViewGroup) a.findViewById(par);
		TextView ntv = new TextView(context);
		ntv.setTextAppearance(context, styleres);
		if (content.length()>1) {
			ntv.setText(Html.fromHtml(content.trim()));
			//ntv.setText(content.trim());
		} else { 
			ntv.setVisibility(8); 
		}
		ntv.setId(GenerateID());
		ptv.addView(ntv);
		return ntv;
	}
	
	public void WriteTV(int tv, String content, int titlemode, String tag, OnClickListener listener) {
    	
    	TextView myview = (TextView)a.findViewById(tv);
    	
    	
    	if (content.length()>1) {
    		content = content.trim();
    		myview.setText(Html.fromHtml(content).toString()); 
    		myview.setVisibility(0); 
		}
    	else { myview.setVisibility(8); }
    	
    	if (listener != null) {
	    	myview.setTag(tag);
	    	myview.setOnClickListener(listener);
	    }

    	switch(titlemode) {
    	case 1:
    		if (myview.getText().length()>80) {
    			myview.setTextSize(18);	
    		} else if (myview.getText().length()>60 && myview.getText().length()<80){
    			myview.setTextSize(21);	
    		} else if (myview.getText().length()>40 && myview.getText().length()<60){
    			myview.setTextSize(26);	
    		} else if (myview.getText().length()>20 && myview.getText().length()<40){
    			myview.setTextSize(31);	
    		} else if (myview.getText().length()<20){
    			myview.setTextSize(35);	
    		}
    		
    	default:
    		
    	break;
    	}
    }
		
	 public TextView NewTV(int par, int styleres, String content, String tag, OnClickListener listener){
	    	Context context = c;
	    	ViewGroup ptv= (ViewGroup) a.findViewById(par);
	    	TextView ntv = new TextView(context);
	    	ntv.setTextAppearance(context, styleres);
	    	
	    	if (!tag.equalsIgnoreCase("void")) {
		    	ntv.setTag(tag);
		    	ntv.setOnClickListener(listener);
		    }

	    	if (content.length()>1) {
	    		ntv.setText(Html.fromHtml(content.trim()).toString());
	    		//ntv.setText(content.trim());
	    	} else { 
	    		ntv.setVisibility(8); 
	    	}
	    	ntv.setId(GenerateID());
	    	ptv.addView(ntv);
	    	return ntv;
    }
	 
	 public VerticalTextView NewVerticalTV(int par, int styleres, String content, String tag, OnClickListener listener){
	    	Context context = c;
	    	ViewGroup ptv= (ViewGroup) a.findViewById(par);
	    	VerticalTextView vtv = new VerticalTextView(context);
	    	vtv.setTextAppearance(context, styleres);
	    	
	    	if (!tag.equalsIgnoreCase("void")) {
		    	vtv.setTag(tag);
		    	vtv.setOnClickListener(listener);
		    }

	    	if (content.length()>1) {
	    		vtv.setText(Html.fromHtml(content.trim()).toString());
	    		//ntv.setText(content.trim());
	    	} else { 
	    		vtv.setVisibility(8); 
	    	}
	    	vtv.setId(GenerateID());
	    	ptv.addView(vtv);
	    	return vtv;
 }
	
	public LinearLayout NewView(int par, int w, int h, int orientation){
		Context context = c;
		ViewGroup pview= (ViewGroup) a.findViewById(par);
		LinearLayout nview = new LinearLayout(context);
		LayoutParams params = new LinearLayout.LayoutParams(w,h);
		nview.setLayoutParams(params);
		nview.setVisibility(0);
		if (!(par==0)) { pview.addView(nview);}
		nview.setOrientation(orientation);
		nview.setId(GenerateID());
		return nview;
	}
	
	public LinearLayout NewVSV(int par, int w, int h){
		Context context = c;
		ViewGroup pview= (ViewGroup) a.findViewById(par);
		ScrollView nview = new ScrollView(context);
		LayoutParams params = new LinearLayout.LayoutParams(w,h);
		nview.setLayoutParams(params);
		nview.setVisibility(0);
		if (!(par==0)) { pview.addView(nview);}
		nview.setId(GenerateID());
		return NewView(nview.getId(), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, LinearLayout.VERTICAL);
	}
	
	public LinearLayout NewHSV(int par, int w, int h){
		Context context = c;
		ViewGroup pview= (ViewGroup) a.findViewById(par);
		HorizontalScrollView nview = new HorizontalScrollView(context);
		LayoutParams params = new LinearLayout.LayoutParams(w,h);
		nview.setLayoutParams(params);
		nview.setVisibility(0);
		if (!(par==0)) { pview.addView(nview);}
		nview.setId(GenerateID());
		return NewView(nview.getId(), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT, LinearLayout.HORIZONTAL);
	}
	
	public int NewImage(int par, int w, int h, int image){
		Context context = c;
		ViewGroup ptv= (ViewGroup) a.findViewById(par);
		ImageView nimage = new ImageView(context);
		nimage.setLayoutParams(new LayoutParams(w,h));
		nimage.setImageResource(image);
		ptv.addView(nimage);
		return nimage.getId();
	}
	
	public int NewImage(int par, int w, int h, int image, String tag, OnClickListener listener){
		Context context = c;
		ViewGroup ptv= (ViewGroup) a.findViewById(par);
		ImageView nimage = new ImageView(context);
		nimage.setLayoutParams(new LayoutParams(w,h));
		nimage.setImageResource(image);
		if (!tag.equalsIgnoreCase("void")) {
	    	nimage.setTag(tag);
	    	nimage.setOnClickListener(listener);
	    }
		ptv.addView(nimage);
		return nimage.getId();
	}
	
	
	public int GenerateID() {
    	
    	a.Elementid= a.Elementid+1;
		return a.Elementid;   	
    }
	
	
	
	

}
