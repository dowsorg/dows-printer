package org.dows.printer.biz;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.google.common.collect.Maps;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.dows.framework.api.Response;
import org.dows.printer.constant.ApiResultConstant;
import org.dows.printer.dto.PrintApiDTO;
import org.dows.printer.entity.PrintApiEntity;
import org.dows.printer.entity.PrintParamsEntity;
import org.dows.printer.enums.ApiMethodTypeEnum;
import org.dows.printer.service.PrintApiService;
import org.dows.printer.service.PrintParamsService;
import org.dows.printer.utils.PandStringUtils;
import org.dows.printer.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrintApiBiz {

    private final PrintApiService printApiService;

    private final PrintParamsService printParamsService;

    @Value("${print.juhe.appid}")
    private String appId;

    @Value("${print.juhe.secret}")
    private String appSecret;


    /**
     * 聚合呗三方接口组装
     *
     * @param apiId 接口id
     */
    public Response reqJHApi(String apiId) {
        String res = null;
        try {
            //根据接口id获取接口
            PrintApiEntity printApi = printApiService.getById(apiId);
            String url = printApi.getApi();

            //获取请求体
            List<PrintParamsEntity> bodyParams = getParamsBytype(apiId, 2);
            String bodyJson = getBody(bodyParams);

            //聚合呗请求头
//            List<PrintParamsEntity> headerParams = getParamsBytype(apiId, 1);
            Map<String, String> headerMap = getHeader(bodyJson);

            //http
            List<JSONObject> jsonObjects = RestTemplateUtils.executeHttp(printApi.getApi(), ApiMethodTypeEnum.getCodeDescByCodeBalue(printApi.getType()), headerMap, bodyJson,null);
            res = jsonObjects.toString();
        } catch (Exception e) {

        }
        System.out.println(res);
        return Response.ok(res);
    }

    private List<PrintParamsEntity> getParamsBytype(String apiId, Integer type) {
        return printParamsService.lambdaQuery().eq(PrintParamsEntity::getApiId, apiId).eq(PrintParamsEntity::getType, type).list();
    }

    private Map<String, String> getHeader(List<PrintParamsEntity> headerParams) {
        Map<String, String> params = Maps.newHashMap();
        params = headerParams.stream().collect(Collectors.toMap(PrintParamsEntity::getParamCode, p -> {
            //获取参数值
            String value = p.getDefaultValue();
            String apiId = p.getApiId();
            if (ObjectUtils.isEmpty(value) && ObjectUtils.isNotEmpty(apiId)) {
                //通过api获取
                value = (String) reqJHApi(apiId).getData();
            }
            return value;
        }));
        return params;
    }

    private String getBody(List<PrintParamsEntity> bodyParams) {
        String bodyJson = "";
        Map<String, Object> params = Maps.newHashMap();
        //判断是否直接list提交
        Boolean isl = false;
        for (PrintParamsEntity p : bodyParams) {
            if ("list".equals(p.getParamCode())) {
                isl = true;
            } else {
                //填充请求体
                if (ObjectUtils.isNotEmpty(p.getDefaultValue())) {
                    params.put(p.getParamCode(), p.getDefaultValue());
                } else if (ObjectUtils.isNotEmpty(p.getDataApi())) {//通过数据接口获取数据

                }

            }
        }

        if (isl) {
            JSONArray j = new JSONArray();
            j.add(params);
            bodyJson = PandStringUtils.getJsonObj(j);
        } else {
            bodyJson = PandStringUtils.getJsonObj(params);
        }
        return bodyJson;
    }


    /**
     * 三方接口组装
     *
     * @param appId 接口id
     */
    public Response demo(String appId) {
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
    private Map<String, String> getHeader(String bodyJson) {

        Map<String, String> maps = Maps.newHashMap();

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
            maps.put("stime", String.valueOf(stime));
            maps.put("sign", sign);

            log.info("聚合呗header信息：{}", PandStringUtils.getJsonObj(maps));

        } catch (Exception e) {
            log.error("获取聚合呗HEADER异常" + e.getMessage(), e);
        }
        return maps;
    }

}
