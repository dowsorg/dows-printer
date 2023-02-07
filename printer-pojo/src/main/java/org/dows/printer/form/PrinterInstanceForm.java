package org.dows.printer.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 打印机(PrinterInstance)实体类
 *
 * @author lait
 * @since 2023-02-06 18:25:04
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PrinterInstance对象", description = "打印机")
public class PrinterInstanceForm {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;


    @ApiModelProperty("通道ID")
    private String channelId;


    @ApiModelProperty("打印机ID")
    private String printerId;


    @ApiModelProperty("通道名")
    private String channelName;


    @ApiModelProperty("打印机名称")
    private String printerName;


    @ApiModelProperty("打印机型号")
    private String printerModel;


    @ApiModelProperty("打印机编号")
    private String printerNo;


    @ApiModelProperty("打印机描述")
    private String descr;


    @ApiModelProperty("打印机规格ID")
    private String printerSpecId;


    @ApiModelProperty("打印机规格描述[规格JSON串]")
    private String printerSpec;


}

