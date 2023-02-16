package org.dows.printer.dto;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 打印接口(PrintApi)实体类
 *
 * @author lait
 * @since 2023-02-06 18:25:00
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class PrintApiDTO {

    /**
     * http requestUrl
     */
    private String apiUrl;

    /**
     * http method
     */
    private String method;

    /**
     * http header
     */
    private Map<String, String> header;

    /**
     * http 请求体
     */
    private String body;

    /**
     * http 请求行参数query
     */
    private Map<String, Object> query;



}

