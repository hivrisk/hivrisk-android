package cam.async;

import org.xml.sax.InputSource;

public interface AsyncFeatures {
	
	public void completed_download_callback(String param, String result, String adparam);
	
	public void completed_download_callback(InputSource param, String result, String adparam);
	
	public void completed_download_callback(String...result);
	
	public void error_download_callback(String param, String result, String adparam);

}
