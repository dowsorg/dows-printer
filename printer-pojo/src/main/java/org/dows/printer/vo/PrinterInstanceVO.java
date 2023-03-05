package org.dows.printer.vo;

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
@ApiModel(value = "PrinterInstance列表返回类", description = "打印机")
public class PrinterInstanceVO {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("打印机ID")
    private String printerId;

    @ApiModelProperty("通道名")
    private String channelName;

    @ApiModelProperty("打印机名称")
    private String printerName;

    @ApiModelProperty("打印机位置")
    private String printerSeat;

    @ApiModelProperty("打印类型")
    private String printeType;

    @ApiModelProperty("打印区域")
    private String printeArea;

    @ApiModelProperty("打印机状态 0 就绪, 1 打印中, 2 缺纸, 3 过温, 4 打印故障 -1 当前离线")
    private Integer printerStatus;


}

