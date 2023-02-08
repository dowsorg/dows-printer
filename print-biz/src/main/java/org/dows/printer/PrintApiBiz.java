package org.dows.printer;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.dows.framework.api.Response;
import org.dows.printer.entity.PrintApi;
import org.dows.printer.entity.PrintParams;
import org.dows.printer.utils.HttpUtils;
import org.dows.printer.utils.PandStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrintApiBiz {

    private static final Logger logger = LoggerFactory.getLogger(PrintApiBiz.class);

    @Value("${print.daquzhineng.appid}")
    private  String appId ;

    @Value("${print.daquzhineng.secret}")
    private String appSecret ;

    public static void main(String[] args) {
//        reqJHApi("45656456");
    }

    /**
     * 聚合呗三方接口组装
     *
     * @param appId 接口id
     */
    public  Response reqJHApi(String appId) {
        String res = null;
        try {
            //根据接口id获取接口
            PrintApi printApi = new PrintApi();
            String url = printApi.getUrl();

            //测试数据
//            url = "https://printer.juhesaas.com/openapi/addPrinter";
            url = "https://printer.juhesaas.com/openapi/getDeviceStatus";


            //请求体
            List<PrintParams> bodyParams = Lists.newArrayList();

            //测试数据

            bodyParams.add(PrintParams.builder().paramName("打印机编号").paramCode("sn").type(2).defaultValue("570020010299").apiId("").build());
//            bodyParams.add(new PrintParams().setParamName("打印机编号").setParamCode("sn").setType(2).setDefaultValue("570020010299").setApiId(""));
//            bodyParams.add(new PrintParams().setParamName("设备密钥").setParamCode("key").setType(2).setDefaultValue("465456456").setApiId(""));
//            bodyParams.add(new PrintParams().setParamName("设备名称或备注").setParamCode("name").setType(2).setDefaultValue("465456456").setApiId(""));
//            bodyParams.add(new PrintParams().setParamName("").setParamCode("list").setType(2).setDefaultValue("465456456").setApiId(""));

            //获取请求体
            String bodyJson = getBody(bodyParams);

            //聚合呗请求头
//            List<PrintParams> headerParams = Lists.newArrayList();
            Map<String, Object> headerMap = getHeader(bodyJson);

            //post
            res = HttpUtils.doPost_bodyjson_headermap(url, bodyJson, headerMap);


        } catch (Exception e) {

        }
        System.out.println(res);
        return Response.ok(res);
    }

    private static String getBody(List<PrintParams> bodyParams) {
        String bodyJson = "";
        Map<String, Object> params = Maps.newHashMap();
        //判断是否直接list提交
        Boolean isl = false;
        for (PrintParams p : bodyParams) {
            if ("list".equals(p.getParamCode())) {
                isl = true;
            } else {
                //填充请求体
                if(ObjectUtils.isNotEmpty(p.getDefaultValue())){
                    params.put(p.getParamCode(), p.getDefaultValue());
                }else if(ObjectUtils.isNotEmpty(p.getDataApi())){//通过数据接口获取数据
                    
                }

            }
        }

        if(isl){
            JSONArray j = new JSONArray();
            j.add(params);
            bodyJson = PandStringUtils.getJsonObj(j);
        }else {
            bodyJson = PandStringUtils.getJsonObj(params);
        }
        return bodyJson;
    }


    /**
     * 三方接口组装
     *
     * @param appId 接口id
     */
    public Response diduidu(String appId) {
        try {

        } catch (Exception e) {

        }
        return Response.ok();
    }


    /**
     * 请求header组装参数
     *
     * @param bodyJson
     * @return
     */
    private  Map<String, Object> getHeader(String bodyJson) {

        Map<String, Object> maps = Maps.newHashMap();

        try {
            String uid = PandStringUtils.getUUID();
            String appId = this.appId;
            String appSecret = this.appSecret;
            Long stime = System.currentTimeMillis();

            String originContext = uid + appId + stime + appSecret;

            if (PandStringUtils.isNotBlank(bodyJson)) {
                originContext += bodyJson;
            }

            String sign = DigestUtils.md5Hex(originContext);

            maps.put("Content-Type", "application/json;charset=UTF-8");
            maps.put("appid", appId);
            maps.put("uid", uid);
            maps.put("stime", stime);
            maps.put("sign", sign);

            logger.info("聚合呗header信息：{}", PandStringUtils.getJsonObj(maps));

        } catch (Exception e) {
            logger.error("获取聚合呗HEADER异常" + e.getMessage(), e);
        }
        return maps;
    }

}
