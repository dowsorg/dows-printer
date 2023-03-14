package org.dows.printer.bo;

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
@ApiModel(value = "PrintOrderBO对象", description = "打印订单内容")
public class PrintOrderBO {

    @ApiModelProperty("打印机订单号")
    private String orderNo;

    @ApiModelProperty("打印机订单项")
    private Long orderItemId;

}

