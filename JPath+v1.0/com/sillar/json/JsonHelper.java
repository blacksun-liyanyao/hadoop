package com.sillar.json;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

public class JsonHelper{
	private static final String TAG = "JsonHelper";
	
	private static final int LENGTH_BUFFER				= 2048;
	
	public static JSONObject getJSONObject(JSONObject jObj, String strJPath){
		
		return getJSONObject(jObj, JPath.createJPath(strJPath));
	}
	
	public static JSONObject getJSONObject(JSONObject jObj, JPath jPath){
		JSONObject rObj=null;
		if(jObj!=null && jPath!=null){
			JNodeArray jNodes=jPath.getJNodeArray();
			
			if(jNodes!=null){
				rObj=jObj;
				
				int len=jNodes.length();
				for(int i=0; i<len; i++){
					rObj=rObj.optJSONObject(jNodes.get(i));
				}
			}
		}
		
		return rObj;
	}
	
	public static JSONArray getJSONArray(JSONObject jObj, String strJPath){
		
		return getJSONArray(jObj, JPath.createJPath(strJPath));
	}
	
	public static JSONArray getJSONArray(JSONObject jObj, JPath jPath){
		JSONArray rArray=null;
		if(jObj!=null && jPath!=null){
			JNodeArray jNodes=jPath.getJNodeArray();
			
			if(jNodes!=null){
				JSONObject rObj=jObj;
				
				int len=jNodes.length();
				for(int i=0; i<len; i++){
					String node=jNodes.get(i);
					if(i==len-1){
						rArray=rObj.optJSONArray(node);
					}else{
						rObj=rObj.optJSONObject(node);
					}
				}
			}
		}
		
		return rArray;
	}
	
	public static boolean optBoolean(JSONObject jObj, String strJPath, boolean dValue){
		boolean result=dValue;
		
		JPath jPath=JPath.createJPath(strJPath);
		if(jPath!=null){
			JSONObject rObj=getJSONObject(jObj, jPath);
			if(rObj!=null){
				result=rObj.optBoolean(jPath.getKey(), dValue);
			}
		}
		
		return result;
	}
	
	public static boolean optBoolean(JSONObject jObj, String strJPath){
		
		return optBoolean(jObj, strJPath, false);
	}
	
	public static double optDouble(JSONObject jObj, String strJPath, double dValue){
		double result=dValue;
		
		JPath jPath=JPath.createJPath(strJPath);
		if(jPath!=null){
			JSONObject rObj=getJSONObject(jObj, jPath);
			if(rObj!=null){
				result=rObj.optDouble(jPath.getKey(), dValue);
			}
		}
		
		return result;
	}
	
	public static double optDouble(JSONObject jObj, String strJPath){
		
		return optDouble(jObj, strJPath, 0);
	}
	
	public static int optInt(JSONObject jObj, String strJPath, int dValue){
		int result=dValue;
		
		JPath jPath=JPath.createJPath(strJPath);
		if(jPath!=null){
			JSONObject rObj=getJSONObject(jObj, jPath);
			if(rObj!=null){
				result=rObj.optInt(jPath.getKey(), dValue);
			}
		}
		
		return result;
	}
	
	public static int optInt(JSONObject jObj, String strJPath){
		
		return optInt(jObj, strJPath, 0);
	}
	
	public static long optLong(JSONObject jObj, String strJPath, long dValue){
		long result=dValue;
		
		JPath jPath=JPath.createJPath(strJPath);
		if(jPath!=null){
			JSONObject rObj=getJSONObject(jObj, jPath);
			if(rObj!=null){
				result=rObj.optLong(jPath.getKey(), dValue);
			}
		}
		
		return result;
	}
	
	public static long optLong(JSONObject jObj, String strJPath, String jKey){
		
		return optLong(jObj, strJPath, 0);
	}
	
	public static float optFloat(JSONObject jObj, String strJPath, float dValue){
		float result=dValue;
		
		JPath jPath=JPath.createJPath(strJPath);
		if(jPath!=null){
			JSONObject rObj=getJSONObject(jObj, jPath);
			if(rObj!=null){
				result=(float)rObj.optDouble(jPath.getKey(), dValue);
			}
		}
		
		return result;
	}
	
	public static float optFloat(JSONObject jObj, String strJPath){
		
		return optFloat(jObj, strJPath, 0);
	}
	
	public static String optString(JSONObject jObj, String strJPath, String dValue){
		String result=dValue;
		
		JPath jPath=JPath.createJPath(strJPath);
		if(jPath!=null){
			JSONObject rObj=getJSONObject(jObj, jPath);
			if(rObj!=null){
				result=rObj.optString(jPath.getKey(), dValue);
			}
		}
		
		return result;
	}
	
	public static String optString(JSONObject jObj, String strJPath){
		
		return optString(jObj, strJPath, null);
	}
	
	public static JSONObject getJSONObject(File file){
		JSONObject jsonObject=null;
		
		if(file!=null && file.exists()){
			BufferedReader reader=null;
			
			try{
				reader=new BufferedReader(new FileReader(file));
				
				StringBuilder sbJson=new StringBuilder();
				int length=LENGTH_BUFFER;
				char[] buffer=new char[LENGTH_BUFFER];
				while((length=reader.read(buffer, 0, LENGTH_BUFFER))!=-1){
					sbJson.append(buffer, 0, length);
				}
				
				String strJson=sbJson.toString();
				if(!TextUtils.isEmpty(strJson)){
					jsonObject=new JSONObject(strJson);
				}
			}catch(Exception e){
				Log.e(TAG, e.getMessage(), e);
			}finally{
				if(reader!=null){
					try{
						reader.close();
					}catch(Exception e){
						Log.v(TAG, e.getMessage(), e);
					}
				}
			}
		}
		
		return jsonObject;
	}
	
	public static JSONObject getJSONObject(String filePath){
		JSONObject jsonObject=null;
		
		if(!TextUtils.isEmpty(filePath)){
			File file=new File(filePath);
			jsonObject=getJSONObject(file);
		}
		
		return jsonObject;
	}
	
	public static JSONObject getAssetsJson(Context context, String assetsPath){
		JSONObject jsonObject=null;
		
		if(!TextUtils.isEmpty(assetsPath) && context!=null){
			BufferedInputStream inStream=null;
			
			try {
				StringBuilder sbJson=new StringBuilder();
				inStream=new BufferedInputStream(context.getAssets().open(assetsPath));
				int len=LENGTH_BUFFER;
				byte[] buff=new byte[LENGTH_BUFFER];
				String strTemp=null;
				while((len=inStream.read(buff, 0, LENGTH_BUFFER))!=-1){
					strTemp=new String(buff, 0, len);
					sbJson.append(strTemp);
				}
				
				String strJson=sbJson.toString();
				if(!TextUtils.isEmpty(strJson)){
					jsonObject=new JSONObject(strJson);
				}
			} catch (Exception e) {
				Log.e(TAG, e.getMessage(), e);
			}finally{
				if(inStream!=null){
					try {
						inStream.close();
					} catch (Exception e) {
						Log.v(TAG, e.getMessage(), e);
					}
				}
			}
		}
		
		return jsonObject;
	}
	
	public static boolean writeJSONObject(JSONObject json, String filePath){
		boolean result=false;
		
		if(!TextUtils.isEmpty(filePath)){
			File file=new File(filePath);
			result=writeJSONObject(json, file);
		}
		
		return result;
	}
	
	public static boolean writeJSONObject(JSONObject json, File file){
		boolean result=false;
		if(json!=null && file!=null){
			if(!file.exists()){
				try {
					result=file.createNewFile();
				} catch (Exception e) {
					Log.e(TAG, e.getMessage(), e);
				}
			}else{
				result=true;
			}
			
			if(result){
				String strJson=json.toString();
				if(!TextUtils.isEmpty(strJson)){
					BufferedWriter writer=null;
					
					try {
						FileWriter fileWriter=new FileWriter(file);
						writer=new BufferedWriter(fileWriter);
						writer.append(strJson);
						writer.flush();
					} catch (Exception e) {
						result=false;
						Log.e(TAG, e.getMessage(), e);
					}finally{
						if(writer!=null){
							try {
								writer.close();
							} catch (Exception e) {
								Log.v(TAG, e.getMessage(), e);
							}
						}
					}
				}
			}
		}
		
		return result;
	}
	
	/**
	 * 异步保存
	 * 
	 * @param strCity
	 * @param json
	 * @return
	 */
	public static void asyncWriteJSONObject(final JSONObject json, final File jsonFile){
//		Runnable runnable=new Runnable() {
//			
//			@Override
//			public void run() {
//				JsonHelper.writeJSONObject(json, jsonFile);
//			}
//		};
		
//		ThreadPoolManager.getInstance().execute(runnable);
		new Thread(new Runnable(){

			@Override
			public void run(){
				JsonHelper.writeJSONObject(json, jsonFile);
			}
		}).start();
	}
}
