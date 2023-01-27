package org.dows.print.utils;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dows.print.utils.PandStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtils {
	
	private static Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
	public static void main(String[] args) {
		
		Map<String, Object> params = Maps.newHashMap();
		params.put("userId", "2c9305ba7cbb345f017cbb558b85001e");
		params.put("searchType", "1");
		params.put("pageIndex", "1");
		params.put("pageSize", "100");
		params.put("paging",false);
		Map<String, Object> header = Maps.newHashMap();
		header.put("access_token",111111);
		header.put("plat_source",10001);
		String doPost = doPost("https://www.qianyoushi.com/hkpserver/hkp/housework/reserve_work_list", params,header );
//		String doPost = doPost("http://localhost:8989/hkp/housework/reserve_work_list", params,header );
		System.out.println(doPost);
		
	}
	 
	/**
	 * get请求
	 * @return
	 */
	public static String doGet(String url,String header) {
        try {
        	HttpClient client = HttpClients.createDefault();
            //发送get请求
            HttpGet request = new HttpGet(url);
            request.setHeader("Accept", "application/json"); 
            request.setHeader("Content-Type", "application/json");
    		if(PandStringUtils.isNotBlank(header)){
    			request.setHeader("Authorization", "Bearer "+header);
    		}
            HttpResponse response = client.execute(request);
            String strResult = EntityUtils.toString(response.getEntity());
            return strResult;
        } catch (IOException e) {
        	e.printStackTrace();
        }
        
        return null;
	}
	
	/**
	 * post请求(用于key-value格式的参数)
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> params){
		
		BufferedReader in = null;  
        try {  
            // 定义HttpClient  
            HttpClient client = HttpClients.createDefault();
            // 实例化HTTP方法  
            HttpPost request = new HttpPost();
            request.setURI(new URI(url));
            
            //设置参数
            List<NameValuePair> nvps = new ArrayList<NameValuePair>();
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
    			String name = (String) iter.next();
    			String value = String.valueOf(params.get(name));
    			nvps.add(new BasicNameValuePair(name, value));
    			
    			//System.out.println(name +"-"+value);
    		}
            request.setEntity(new UrlEncodedFormEntity(nvps,StandardCharsets.UTF_8));
            
            HttpResponse response = client.execute(request);  
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){	//请求成功
            	in = new BufferedReader(new InputStreamReader(response.getEntity()  
                        .getContent(),"utf-8"));
                StringBuffer sb = new StringBuffer("");  
                String line = "";  
                String NL = System.getProperty("line.separator");  
                while ((line = in.readLine()) != null) {  
                    sb.append(line + NL);  
                }
                
                in.close();  
                
                return sb.toString();
            }
            else{	//
            	System.out.println("状态码：" + code);
            	return null;
            }
        }
        catch(Exception e){
        	e.printStackTrace();
        	
        	return null;
        }
	}
	
	/**
	 * post请求(用于key-value格式的参数)
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPost(String url, Map<String, Object> params,Map<String, Object> headerMap){
		
		BufferedReader in = null;  
        try {  
            // 定义HttpClient  
            HttpClient client = HttpClients.createDefault();
            // 实例化HTTP方法  
            logger.info("{}请求,参数：{}",url,PandStringUtils.getJsonObj(params));
            HttpPost httpPost = new HttpPost(url);  
//            httpPost.setHeader("Accept", "application/json"); 
//            httpPost.setHeader("Content-Type", "application/json");
            if(headerMap!=null && !headerMap.isEmpty()){
            	for(Entry entry:headerMap.entrySet()){
            		httpPost.setHeader(entry.getKey()+"", entry.getValue()+"");
            	}
            }
            //设置参数
            List<BasicNameValuePair> nvps = new ArrayList<BasicNameValuePair>(); 
            for (Iterator iter = params.keySet().iterator(); iter.hasNext();) {
    			String name = (String) iter.next();
    			String value = String.valueOf(params.get(name));
    			nvps.add(new BasicNameValuePair(name, value));
    			
    			//System.out.println(name +"-"+value);
    		}
            //将参数做字符串的转换
            httpPost.setEntity(new UrlEncodedFormEntity(nvps,StandardCharsets.UTF_8));
            
            HttpResponse response = client.execute(httpPost);  
            int code = response.getStatusLine().getStatusCode();
            if(code == 200){	//请求成功
            	in = new BufferedReader(new InputStreamReader(response.getEntity()  
                        .getContent(),"utf-8"));
                StringBuffer sb = new StringBuffer("");  
                String line = "";  
                String NL = System.getProperty("line.separator");  
                while ((line = in.readLine()) != null) {  
                    sb.append(line + NL);  
                }
                
                in.close();  
                
                logger.info("返回结果：{}",sb.toString());
                return sb.toString();
            }
            else{	//
            	System.out.println("状态码：" + code);
            	return null;
            }
        }
        catch(Exception e){
        	e.printStackTrace();
        	
        	return null;
        }
	}
	
	/**
	 * post请求（用于请求json格式的参数）
	 * @param url
	 * @param params
	 * @param  headerMap
	 * @return
	 */
	public static String doPost_bodyjson_headermap(String url, String params,Map<String, Object> headerMap) throws Exception {
		
		logger.info("post bodyjson请求，url={}，body=",url,params);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost   
		if(headerMap!=null && !headerMap.isEmpty()){
			logger.info("post bodyjson请求，header=",PandStringUtils.getJsonObj(headerMap));
        	for(Entry entry:headerMap.entrySet()){
        		httpPost.setHeader(entry.getKey()+"", entry.getValue()+"");
        	}
        }
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);        
		CloseableHttpResponse response = null;
		
		try {
			
			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			String jsonString = EntityUtils.toString(responseEntity);
			logger.info("请求返回："+jsonString+",状态："+state);
			return jsonString;
		}
		finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * post请求（用于请求json格式的参数）
	 * @param url
	 * @param params
	 * @param header
	 * @return
	 */
	public static String doPost(String url, String params,String header) throws Exception {
		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost   
		httpPost.setHeader("Accept", "application/json"); 
		httpPost.setHeader("Content-Type", "application/json");
		if(PandStringUtils.isNotBlank(header)){
			httpPost.setHeader("Authorization", "Bearer "+header);
		}
		String charSet = "UTF-8";
		StringEntity entity = new StringEntity(params, charSet);
		httpPost.setEntity(entity);        
		CloseableHttpResponse response = null;
		
		try {
			
			response = httpclient.execute(httpPost);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			String jsonString = EntityUtils.toString(responseEntity);
			logger.info("请求返回："+jsonString+",状态："+state);
			return jsonString;
		}
		finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * delete请求
	 * @param url
	 * @param header
	 * @return
	 */
	public static String doDelete(String url,String header) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpDelete httpDelete = new HttpDelete(url);
		httpDelete.setHeader("Accept", "application/json"); 
		httpDelete.setHeader("Content-Type", "application/json");
		if(PandStringUtils.isNotBlank(header)){
			httpDelete.setHeader("Authorization", "Bearer "+header);
		}
		CloseableHttpResponse response = null;
		
		try {
			response = httpclient.execute(httpDelete);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			String jsonString = EntityUtils.toString(responseEntity);
			logger.info("请求返回："+jsonString+",状态："+state);
			return jsonString;
		}
		finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * put请求
	 * @param url
	 * @param params
	 * @return
	 */
	public static String doPut(String url, String params,String header) throws Exception {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPut httpPut = new HttpPut(url);
		httpPut.setHeader("Accept", "application/json"); 
		httpPut.setHeader("Content-Type", "application/json");
		if(PandStringUtils.isNotBlank(header)){
			httpPut.setHeader("Authorization", "Bearer "+header);
		}
		String charSet = "UTF-8";
		//设置参数
		if(params!=null){
			StringEntity se = new StringEntity(params, charSet);
			httpPut.setEntity(se);
		}
		CloseableHttpResponse response = null;
		
		try {
			response = httpclient.execute(httpPut);
			StatusLine status = response.getStatusLine();
			int state = status.getStatusCode();
			HttpEntity responseEntity = response.getEntity();
			String jsonString = EntityUtils.toString(responseEntity);
			logger.info("请求返回："+jsonString+",状态："+state);
			return jsonString;
		}
		finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * post请求（用于请求json格式的参数）
	 * @param url
	 * @param params
	 * @return
	 */
	public static boolean doPostForFormat(String url, String params,String savePath,String fileName) throws Exception {
		
		
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);// 创建httpPost   
    	httpPost.setHeader("Accept", "application/json"); 
    	httpPost.setHeader("Content-Type", "application/json");
    	String charSet = "UTF-8";
    	StringEntity entity = new StringEntity(params, charSet);
    	httpPost.setEntity(entity);        
    	CloseableHttpResponse response = null;
        boolean flag = false;
        try {
        	response = httpClient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
            	HttpEntity responseEntity = response.getEntity();
            	boolean ifJson = false;
            	if (responseEntity != null) {
            		byte[] responseBytes = getData(responseEntity);
            		try {
        			 	JSONObject.parseObject(new String(responseBytes));
        	            ifJson=true;
        	        } catch (Exception e) {
        	        }
            		 if(ifJson){
            			 logger.info(new String(responseBytes));
            			 return false;
            		 }
                    if(responseBytes != null){
                    	//流保存
                    	writeLocal(responseBytes, savePath, fileName);
                    	flag = true;
                    }
                }
            	return flag;
            }else{
				 logger.error("请求返回:"+state+"("+url+")");
			}
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
        return flag;
	}
	
	/**
     * 获取Entity中数据
     * @param httpEntity
     * @return
     * @throws Exception
     */
    public static byte[] getData(HttpEntity httpEntity) throws Exception{

        BufferedHttpEntity bufferedHttpEntity = new BufferedHttpEntity(httpEntity);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bufferedHttpEntity.writeTo(byteArrayOutputStream);
        byte[] responseBytes = byteArrayOutputStream.toByteArray();
        return responseBytes;
    }
    
    private static void writeLocal(byte[] data,String savePath,String fileName){
    	try {
    		OutputStream out = new FileOutputStream(new File(savePath+fileName));
    		out.write(data);
    		out.flush();
    		out.close();
    		logger.info("分享二维码写入本地成功fileName="+fileName);
		} catch (Exception e) {
			logger.error("获取字节流图片保存异常"+e.getMessage(),e);
		}
    }
    
    
    /**
	 * post提交带流参数的url
	 * @param url
	 * @param reqParam
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static String postFileMultiPart(String url,Map<String,ContentBody> reqParam){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        
        try {
            // 创建httpget.    
            HttpPost httppost = new HttpPost(url);
        	
            //setConnectTimeout：设置连接超时时间，单位毫秒。setConnectionRequestTimeout：设置从connect Manager获取Connection 超时时间，单位毫秒。这个属性是新加的属性，因为目前版本是可以共享连接池的。setSocketTimeout：请求获取数据的超时时间，单位毫秒。 如果访问一个接口，多少时间内无法返回数据，就直接放弃此次调用。
            RequestConfig defaultRequestConfig = RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(5000).setSocketTimeout(15000).build();
            httppost.setConfig(defaultRequestConfig);
            
            logger.info("executing request " + httppost.getURI());
            
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            for(Entry<String,ContentBody> param : reqParam.entrySet()){
            	multipartEntityBuilder.addPart(param.getKey(), param.getValue());
            }
            HttpEntity reqEntity = multipartEntityBuilder.build();
            httppost.setEntity(reqEntity);
            
            // 执行post请求.    
            CloseableHttpResponse response = null;
            
            try {  
            	response = httpclient.execute(httppost);
                // 获取响应实体    
                HttpEntity entity = response.getEntity();  
                if (entity != null) { 
                	return EntityUtils.toString(entity,Charset.forName("UTF-8"));
                }
            }catch (Exception e) {
				logger.error("流文件post上传异常"+e.getMessage(),e);
			} finally {  
                try {
                	if(response != null){
                		response.close();
                	}
				} catch (IOException e) {
					logger.error(""+e.getMessage(),e);
				}
                
            }
        } finally {  
            // 关闭连接,释放资源    
            try {  
                httpclient.close();  
            } catch (IOException e) {  
            	logger.error("流文件post上传异常"+e.getMessage(),e);
            }  
        }
        return null;  
    }
	
	/**
	 * post请求获取byte输入流
	 * @param URL
	 * @param json
	 * @return
	 */
	public static ByteArrayInputStream sendPost(String URL, String json) {
        InputStream inputStream = null;
        ByteArrayInputStream byteArrayInputStream = null;
        // 创建默认的httpClient实例.
        CloseableHttpClient httpclient = HttpClients.createDefault();
        // 创建httppost
        HttpPost httppost = new HttpPost(URL);
        httppost.addHeader("Content-type", "application/json; charset=utf-8");
        httppost.setHeader("Accept", "application/json");
        try {
            StringEntity s = new StringEntity(json, Charset.forName("UTF-8"));
            s.setContentEncoding("UTF-8");
            httppost.setEntity(s);
            HttpResponse response = httpclient.execute(httppost);
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK){
                // 获取相应实体
                HttpEntity entity = response.getEntity();
                inputStream = entity.getContent();
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                // 创建一个Buffer字符串
                byte[] buffer = new byte[1024];
                // 每次读取的字符串长度，如果为-1，代表全部读取完毕
                int len = 0;
                // 使用一个输入流从buffer里把数据读取出来
                while ((len = inputStream.read(buffer)) != -1) {
                    // 用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                    outStream.write(buffer, 0, len);
                }
                // 关闭输入流
                inputStream.close();
                // 把outStream里的数据写入内存
                byteArrayInputStream = new ByteArrayInputStream(outStream.toByteArray());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return byteArrayInputStream;
    }


}
