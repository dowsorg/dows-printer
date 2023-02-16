package org.dows.printer.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

/**
 * 参数元数据(PrintMeta)实体类
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
@ApiModel(value = "PrintMeta对象", description = "参数元数据")
public class PrintMetaForm {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;


    @ApiModelProperty("应用ID")
    private String appId;


    @ApiModelProperty("参数名")
    private String paramName;


    @ApiModelProperty("参数接口")
    private String paramApi;


}

