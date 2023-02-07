package org.dows.printer.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 接口映射(PrintMapping)实体类
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
@ApiModel(value = "PrintMapping对象", description = "接口映射")
public class PrintMappingForm {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;


    @ApiModelProperty("映射ID")
    private String mappingId;


    @ApiModelProperty("通道ID")
    private String channelId;


    @ApiModelProperty("映射通道ID")
    private String toChannelId;


    @ApiModelProperty("接口ID")
    private String apiId;


    @ApiModelProperty("映射接口ID")
    private String toApIdi;


    @ApiModelProperty("接口URI")
    private String api;


    @ApiModelProperty("映射接口URI")
    private String toApi;


}

