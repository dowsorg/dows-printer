package org.dows.printer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 根据内容打印接口(PrintContentDTO)
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
@ApiModel(value = "PrintContentDTO对象", description = "根据内容打印接口")
public class PrintContentDTO {

    @ApiModelProperty("打印机编号")
    private String printSn;

    @ApiModelProperty("打印机编号")
    private String content;

}

