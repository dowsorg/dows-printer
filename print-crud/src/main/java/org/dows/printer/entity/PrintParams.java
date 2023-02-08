package org.dows.printer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 *
 * 打印机信息
 * 由GenEntityMysql类自动生成
 * Fri Mar 04 16:36:33 CST 2022
 *
 * @xuanxy
 */
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PrintApi对象", description = "打印三方接口")
public class PrintParams {

    /**
     * 主键id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private String id;

    /**
     * 主键id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String apiId;

    /**
     * 参数名
     */
    private String paramName;

    /**
     * 参数code
     */
    private String paramCode;

    /**
     * 参数默认值
     */
    private String defaultValue;

    /**
     * 获取参数的接口
     */
    private String dataApi;


    /**
     * 参数类型   1、请求头    2、请求体   3、请求行    4、响应体
     */
    private Integer type;



}

