package org.dows.printer.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * slw_print_info 实体类
 * 打印机信息
 * 由GenEntityMysql类自动生成
 * Fri Mar 04 16:36:33 CST 2022
 *
 * @xuanxy
 */
@Data
@ToString
@Builder
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "SlwPrintInfo对象", description = "打印机信息")
public class SlwPrintInfo {

    /**
     * 主键id
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @ApiModelProperty("主键ID")
    private String id;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;

    /**
     * 编辑时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date updateTime;

    /**
     * 商户用户id
     */
    private String userId;

    /**
     * 门店id
     */
    private String storeId;

    /**
     * 打印机种类 1大趋
     */
    private Integer printType;

    /**
     * 打印机序列号
     */
    private String printSn;

    /**
     * 打印机名称
     */
    private String printName;

    /**
     * 打印机秘钥
     */
    private String printKey;

    /**
     * 打印机状态 -1设备离线 1待绑定 2待机使用中 3网络已断开
     */
    private Integer printStatus;

    /**
     * 打印机位置 1前台 2后厨
     */
    private Integer printPlace;

    /**
     * 打印机打印条数
     */
    private Integer printCount;

    /**
     * 数据状态 1正常 2已删除
     */
    private Integer dataStatus;

    /**
     * 打印制作单 1是 2否
     */
    private Integer makingStatus;
    /**
     * 制作单内容
     */
    private String makingContent;


}

