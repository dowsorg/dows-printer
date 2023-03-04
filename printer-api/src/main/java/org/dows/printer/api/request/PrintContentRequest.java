package org.dows.printer.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 根据内容打印接口(PrintContentRequest)
 *
 * @author lait
 * @since 2023-02-06 18:25:00
 */
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "PrintContentRequest 打印内容对象", description = "根据内容打印")
public class PrintContentRequest {

    @ApiModelProperty("打印机编号")
    private String printerNo;

    @ApiModelProperty("打印机编号")
    private String content;

}

