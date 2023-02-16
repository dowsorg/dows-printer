package org.dows.printer.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.framework.crud.mybatis.MybatisCrudRest;
import org.dows.printer.biz.PrintTemplateBiz;
import org.dows.printer.entity.PrintTemplateEntity;
import org.dows.printer.form.PrintTemplateForm;
import org.dows.printer.service.PrintTemplateService;
import org.dows.printer.vo.JuhePrintContentVo;
import org.springframework.web.bind.annotation.*;

/**
 * 应用打印模板(PrintTemplate)表控制层
 *
 * @author
 * @date
 */
@Api(tags = "应用打印模板")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("printTemplate")
public class PrintTemplateRest implements MybatisCrudRest<PrintTemplateForm, PrintTemplateEntity, PrintTemplateService> {

    private final PrintTemplateBiz printTemplateBiz;

    /**
     * 获取模板组装内容根据模板id
     *
     * @param temId 模板id
     */
    @GetMapping("/getConByTem")
    @ApiOperation(value = "获取模板组装内容根据模板id")
    public Response getConByTem(String temId) {
        return printTemplateBiz.getConByTem(temId);
    }

    /**
     * 组装小票内容
     *
     * @param printContentVo      小票组装内容
     * @param -storeName          店名    五天日记【松江印象城店】
     * @param -tableNo            桌号    “A01”
     * @param -pepNum             人数  "6"
     * @param -operator           操作员
     * @param -dishesVoList       菜品集合
     * @param -amountTotal        总计金额
     * @param -address            地址
     * @param -phone              电话
     * @param -affiliatePriceJson 优惠信息
     * @param -type               小票类型    1-总单  2-结账单（优惠信息）  3、制作单  4、加菜  5退菜
     * @return
     */
    @PostMapping("/getModelContent")
    @ApiOperation(value = "组装小票内容")
    public Response getModelContent(@RequestBody JuhePrintContentVo printContentVo) {
        return printTemplateBiz.getModelContent(printContentVo);
    }

}

