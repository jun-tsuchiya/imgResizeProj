package imgResizeProj;

import java.io.File;
import java.io.FilenameFilter;
/*
 * 拡張子のフィルタリング設定
 *
 */
public class addImageFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
	        if (name.endsWith("jpg")) {
	            return true;
	        } else if(name.endsWith("jpeg")) {
	        	return true;
	        }else if(name.endsWith("png")){
	        	return true;
	        }else{
	            return false;
	        }
	    }
	}

