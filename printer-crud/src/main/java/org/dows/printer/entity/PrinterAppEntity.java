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
 * 打印机应用(PrinterApp)实体类
 *
 * @author lait
 * @since 2023-02-06 18:25:03
 */
@SuppressWarnings("serial")
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PrinterApp对象", description = "打印机应用")
@TableName("printer_app")
public class PrinterAppEntity implements CrudEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("应用ID")
    private String appId;

    @ApiModelProperty("打印机ID")
    private String printerId;

    @ApiModelProperty("商户号")
    private String channelAccount;

    @ApiModelProperty("打印机型号")
    private String printerModel;

    @ApiModelProperty("授权商户打印机序列号")
    private String license;

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("逻辑删除")
    private Boolean deleted;

    @ApiModelProperty("授权开始时间")
    private Date startTime;

    @ApiModelProperty("授权结束时间")
    private Date endTime;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("时间戳")
    private Date dt;

}

