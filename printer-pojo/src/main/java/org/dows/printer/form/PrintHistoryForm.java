package org.dows.printer.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 打印记录(PrintHistory)实体类
 *
 * @author lait
 * @since 2023-02-06 18:25:01
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PrintHistory对象", description = "打印记录")
public class PrintHistoryForm {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;


    @ApiModelProperty("应用ID")
    private String appId;


    @ApiModelProperty("通道ID")
    private String channelId;


    @ApiModelProperty("通道商户号")
    private String channelAccount;


    @ApiModelProperty("打印机ID")
    private String printerId;


    @ApiModelProperty("打印机编号")
    private String printerNo;


    @ApiModelProperty("打印参数JSON")
    private String params;


    @ApiModelProperty("打印时间")
    private String printTime;


    @ApiModelProperty("打印状态码")
    private String stateCode;


    @ApiModelProperty("状态描述")
    private String stateDescr;


    @ApiModelProperty("是否打印成功")
    private Boolean flag;


}

