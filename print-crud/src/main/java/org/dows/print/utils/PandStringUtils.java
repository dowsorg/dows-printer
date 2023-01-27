package org.dows.print.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

public class PandStringUtils {
	
	private static Logger logger = LoggerFactory.getLogger(PandStringUtils.class);
	
	public static final char UNDERLINE = '_';
	
	public static final String TABLE_PREFIX = "AOITEMP_";
	
	public static boolean isBlank(String value){
		boolean flag = false;
		if(value == null || value.trim().equals("null") || value.trim().equals("undefined") || value.trim().equals("")){
			flag = true;
		}
		return flag;
	}
	
	public static boolean isNotBlank(String value){
		boolean flag = false;
		if(!isBlank(value)){
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 
	 * @param
	 * @return
	 */
	public static String failJson(String message,Object object){
		ObjectMapper mapper = new ObjectMapper();
		String returnValue = "";
		Map<String, Object> map = Maps.newHashMap();
		map.put("status", 0);
		map.put("message", message);
		map.put("data", object);
		try {
			returnValue = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			logger.error("json转换失败"+e.getMessage(),e);
		}
		return returnValue;
	}
	/**
	 * 
	 * @param
	 * @return
	 */
	public static String printJson(String message,Object object){
		ObjectMapper mapper = new ObjectMapper();
		String returnValue = "";
		Map<String, Object> map = Maps.newHashMap();
		map.put("status", 1);
		map.put("message", message);
		map.put("data", object);
		try {
			returnValue = mapper.writeValueAsString(map);
		} catch (JsonProcessingException e) {
			logger.error("json转换失败"+e.getMessage(),e);
		}
		return returnValue;
	}
	/**
	 * 
	 * @param data
	 * @return
	 */
	public static String getJsonObj(Object data){
		ObjectMapper mapper = new ObjectMapper();
		String returnValue = "";
		try {
			returnValue = mapper.writeValueAsString(data);
		} catch (JsonProcessingException e) {
			logger.error("json转换失败"+e.getMessage(),e);
		}
		return returnValue;
	}
	
    /**
     * _转驼峰(非正则)
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        param = param.toLowerCase();
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
    
    /**
     * _转驼峰（正则）
     *
     * @param param
     * @return
     */
    public static String underlineToCamelZz(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        StringBuilder sb = new StringBuilder(param.toLowerCase());
        Matcher mc = Pattern.compile("_").matcher(param);
        int i = 0;
        while (mc.find()) {
            int position = mc.end() - (i++);
            //String.valueOf(Character.toUpperCase(sb.charAt(position)));
            sb.replace(position - 1, position + 1, sb.substring(position, position + 1).toUpperCase());
        }
        return sb.toString();
    }
    
    public static String getRandomNum(int num){
    	Random rd = new Random();  
    	String[] radmon = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
    	StringBuffer sb = new StringBuffer();  
    	
    	for (int i = 0; i < num; i++) {  
    		String s = radmon[rd.nextInt(10)];  
    		sb.append(s);  
    	}  
    	return sb.toString();
    }
    
    public static String getRandomStr(int num){
    	Random rd = new Random();  
        String[] radmon = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
        		"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z",
        		"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        StringBuffer sb = new StringBuffer();  
        
        for (int i = 0; i < num; i++) {  
            String s = radmon[rd.nextInt(58)];  
            sb.append(s);  
        }  
        return sb.toString();
    }
    
    /**
     * 得到全球唯一标示
     * @return
     */
    public static String getUUID(){
    	String returnStr = "";
    	returnStr = UUID.randomUUID().toString().replaceAll("-", "");
    	return returnStr;
    }
    /** 
     * 将字符串复制到剪切板。 
     */  
    public static void setSysClipboardText(String writeMe) {  
        Clipboard clip = Toolkit.getDefaultToolkit().getSystemClipboard();  
        Transferable tText = new StringSelection(writeMe);  
        clip.setContents(tText, null);  
    }
    
    /**
     * 获取分页结束索引
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public static int endPageIndex(Integer pageIndex,Integer pageSize){
    	int endPageIndex = 10;
    	try {
			if(pageIndex==null){
				pageIndex=1;
			}
			if(pageSize==null){
				pageSize=10;
			}
			endPageIndex = pageIndex*pageSize;
		} catch (Exception e) {
			// TODO: handle exception
		}
    	return endPageIndex;
    }
    
    public static long getCRC32(String str){
    	CRC32 crc = new CRC32();
    	try {
    		int cnt = str.length();
    		crc.update(str.getBytes());
    	} catch (Exception e) {
    		logger.error("获取字符串crc32值"+e.getMessage(),e);
    	}
    	return crc.getValue();
    }
    
    
    /**
     * 检测是否是客户端
     * @param
     * @return
     */
    public static boolean isClient(HttpServletRequest request){
    	boolean flag = false;
    	try {
    		String appid = request.getHeader("appid");
    		if(isBlank(appid)){
    			return flag;
    		}
    		byte[] decodeFast = Base64Utils.decode(appid.getBytes());
			String mmKey = new String(decodeFast);
			if(isNotBlank(mmKey)){
				flag = true;
			}
		} catch (Exception e) {
			logger.error("appid解密一样"+e.getMessage(),e);
		}
        return flag;
    }
	
	public static void main(String[] args) throws Exception{
		System.out.println(getUUID());
		setSysClipboardText("123213");
	}
}
