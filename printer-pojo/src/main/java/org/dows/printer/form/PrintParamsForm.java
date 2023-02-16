package org.dows.printer.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 打印参数(PrintParams)实体类
 *
 * @author lait
 * @since 2023-02-06 18:25:02
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PrintParams对象", description = "打印参数")
public class PrintParamsForm {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;


    @ApiModelProperty("接口ID")
    private String apiId;


    @ApiModelProperty("通道ID")
    private String channelId;


    @ApiModelProperty("参数名")
    private String paramName;


    @ApiModelProperty("参数code")
    private String paramCode;


    @ApiModelProperty("参数默认值")
    private String defaultValue;


    @ApiModelProperty("获取 参数的接口")
    private String dataApi;

    /**
     * 参数类型   1、请求头    2、请求体   3、请求行    4、响应体
     */
    @ApiModelProperty("参数类型 1、请求头 2、请求体 3、请求行 4、响应体")
    private Integer type;


}

