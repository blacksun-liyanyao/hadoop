package com.sillar.json;

import org.json.JSONException;

import android.text.TextUtils;
import android.util.Log;

public class JPath implements Cloneable{
	private static final String TAG = "JPath";

	public static final String SEPARATOR_NODE		= "/";
	public static final String SEPARATOR_VALUE		= "$";
	
	private String jKey;
	private JNodeArray jNodeArray;
	
	private JPath(String path) throws JSONException{
		if(!TextUtils.isEmpty(path)){
			int i=path.lastIndexOf(SEPARATOR_VALUE);
			
			if(i>=0){
				jKey=path.substring(i+1);
				if(TextUtils.isEmpty(jKey)){
					throw new JSONException("JPath is Illegal!");
				}
				
				path = i==0 ? "" : path.substring(0, i);
			}
			
			jNodeArray=new JNodeArray(path);
		}
	}
	
	public static JPath createJPath(String strJPath){
		JPath jPath=null;
		
		try{
			jPath=new JPath(strJPath);
		}catch(Exception e){
			Log.e(TAG, e.getMessage(), e);
		}
		
		return jPath;
	}
	
	public String getKey(){
		return jKey;
	}
	
	public JNodeArray getJNodeArray(){
		return jNodeArray;
	}
	
	@Override
	public JPath clone(){
		JPath newJPath=null;
		
        try {
        	newJPath = (JPath)super.clone();
        	newJPath.jKey=new String(jKey);
        	newJPath.jNodeArray = (JNodeArray)jNodeArray.clone();
        }catch(Exception e) {
           //do nothing
        	Log.e("", e.getMessage(), e);
        }
		
		return newJPath;
	}
	
	@Override
	public int hashCode(){
        int result = 1;
        
        int len=jNodeArray.length();
        for(int i=0; i<len; i++){
        	String node=jNodeArray.get(i);
        	result = (31 * result) + (node == null ? 0 : node.hashCode());
        }
        
        result = (31 * result) + (jKey == null ? 0 : jKey.hashCode());

        return result;
	}

	@Override
	public boolean equals(Object o){
		boolean resule=false;
		
		if(o!=null && o instanceof JPath){
			JPath jPath=(JPath)o;
			resule = (TextUtils.isEmpty(jKey) && TextUtils.isEmpty(jPath.jKey) || 
					!TextUtils.isEmpty(jKey) && jKey.equals(jPath.jKey)) && 
					jNodeArray.equals(jPath.jNodeArray);
		}

		return resule;
	}

	@Override
	public String toString(){
		StringBuilder sbJPath=new StringBuilder(jNodeArray.toString());
		
		if(!TextUtils.isEmpty(jKey)){
			sbJPath.append(SEPARATOR_VALUE).append(jKey);
		}
		
		return sbJPath.toString();
	}
}
