package org.dows.printer.utils;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.dows.printer.constant.ApiResultConstant;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class RestTemplateUtils {

    @Resource(name = "clientHttpRestTemplate")
    private RestTemplate clientHttpRestTemplate;
    private static RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        restTemplate = clientHttpRestTemplate;
    }


    /**
     * http 执行获取数据
     *
     * @param apiUrl
     * @param method
     * @param header
     * @param bodyStr
     * @param query
     */
    public static List<JSONObject> executeHttp(String apiUrl, String method, Map<String, String> header, String bodyStr, Map<String, Object> query) {
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(header);
        HttpEntity<String> entity = new HttpEntity<>(bodyStr, headers);
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl);
        if (MapUtil.isNotEmpty(query)) {
            for (Map.Entry<String, Object> e : query.entrySet()) {
                builder.queryParam(e.getKey(), e.getValue());
            }
        }
        String realUrl = builder.build().toString();
        log.info("realUrl:{}", realUrl);
        ResponseEntity<Object> exchange = null;
        try {
            exchange = restTemplate.exchange(realUrl, HttpMethod.valueOf(method), entity,
                    Object.class);
        } catch (Exception e) {
            log.error("error", e);
        }
        if (exchange.getStatusCode().isError()) {
            log.error("errorBody", exchange.getBody());
        }
        Object body = exchange.getBody();
        String jsonStr = JSONObject.toJSONString(body);
        List<JSONObject> result = new ArrayList<>();
        if (jsonStr.trim().startsWith(ApiResultConstant.LEFT_BIG_BOAST)
                && jsonStr.trim().endsWith(ApiResultConstant.RIGTH_BIG_BOAST)) {
            // JSONObject
            result.add(JSONObject.parseObject(jsonStr));
        } else if (jsonStr.trim().startsWith(ApiResultConstant.LEFT_MIDDLE_BOAST)
                && jsonStr.trim().endsWith(ApiResultConstant.RIGHT_MIDDLE_BOAST)) {
            // List
            result = JSONArray.parseArray(jsonStr, JSONObject.class);
        } else {
            result.add(new JSONObject());
        }
        return result;
    }


}
