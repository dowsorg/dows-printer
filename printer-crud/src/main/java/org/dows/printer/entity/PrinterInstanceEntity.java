package org.dows.printer.entity;

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

import java.util.Date;

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
@ApiModel(value = "PrinterInstance对象", description = "打印机")
@TableName("printer_instance")
public class PrinterInstanceEntity implements CrudEntity {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private Long id;

    @ApiModelProperty("通道ID")
    private String channelId;

    @ApiModelProperty("打印机ID")
    private String printerId;

    @ApiModelProperty("通道名")
    private String channelName;

    @ApiModelProperty("打印机名称")
    private String printerName;

    @ApiModelProperty("打印机秘钥")
    private String printerKey;

    @ApiModelProperty("打印机型号")
    private String printerModel;

    @ApiModelProperty("打印机编号")
    private String printerNo;

    @ApiModelProperty("打印机描述")
    private String descr;

    @ApiModelProperty("打印机规格ID")
    private String printerSpecId;

    @ApiModelProperty("打印机规格描述[规格JSON串]")
    private String printerSpec;

    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("时间戳")
    private Date dt;

    @JsonIgnore
    @TableLogic
    @TableField(fill = FieldFill.INSERT)
    @ApiModelProperty("逻辑删除")
    private Boolean deleted;

    @ApiModelProperty("打印制作单 1是 2否")
    private Integer makingStatus;

    @ApiModelProperty("门店id")
    private String storeId;

}

