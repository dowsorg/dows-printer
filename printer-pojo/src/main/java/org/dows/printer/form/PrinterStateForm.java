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
@ApiModel(value = "PrinterState对象", description = "打印机-状态")
public class PrinterStateForm {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("打印机ID")
    private String printerId;

    @ApiModelProperty("设备联网状态: 0 当前离线 1 在线")
    private Integer onlineStatus;

    @ApiModelProperty("设备工作状态: 0 就绪, 1 打印中, 2 缺纸, 3 过温, 4 打印故障")
    private Integer workStatus;

}

