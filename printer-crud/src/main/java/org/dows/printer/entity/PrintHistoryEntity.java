package org.dows.printer.entity;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;
import org.dows.framework.crud.mybatis.CrudEntity;

/**
 * 打印记录(PrintHistory)实体类
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PrintHistory对象", description = "打印记录")
@TableName("print_history")
public class PrintHistoryEntity implements CrudEntity {

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

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("逻辑删除")
    private Boolean deleted;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("时间戳")
    private Date dt;

}

