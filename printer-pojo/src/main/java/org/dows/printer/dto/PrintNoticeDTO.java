package org.dows.printer.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 异步通知小票
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
@ApiModel(value = "PrintNoticeDTO对象", description = "异步通知小票")
public class PrintNoticeDTO {

    @ApiModelProperty("回调消息类型")
    private int type;

    @ApiModelProperty("回调时间")
    private int rtime;

    @ApiModelProperty("回调业务内容")
    private String data;

}

