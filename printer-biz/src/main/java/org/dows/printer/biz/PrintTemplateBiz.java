package org.dows.printer.biz;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.printer.utils.third.print.JuhePrintContentUtils;
import org.dows.printer.vo.JuhePrintContentVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class PrintTemplateBiz {

    private static final Logger logger = LoggerFactory.getLogger(PrintTemplateBiz.class);

    /**
     * 获取模板组装内容
     *
     * @param temId 模板id
     */
    public Response getConByTem(String temId) {
        try {


        } catch (Exception e) {

        }
        return Response.ok();
    }

    /**
     * 组装小票内容
     *
     * @param printContentVo  小票组装内容
     * @param -storeName   店名    五天日记【松江印象城店】
     * @param -tableNo     桌号    “A01”
     * @param -pepNum      人数  "6"
     * @param -operator    操作员
     * @param -dishesVoList      菜品集合
     * @param -amountTotal 总计金额
     * @param -address     地址
     * @param -phone       电话
     * @param -affiliatePriceJson       优惠信息
     * @param -type       小票类型    1-总单  2-结账单（优惠信息）  3、制作单  4、加菜  5退菜
     * @return
     */
    public Response getModelContent(JuhePrintContentVo printContentVo) {
        String result = "";
        try {
            int type = printContentVo.getType();
            if (ObjectUtils.isEmpty(type)) {
                return Response.fail("小票类型不能为空");
            }
            result = JuhePrintContentUtils.getContent(printContentVo);
        } catch (Exception e) {
            logger.error("状态异常" + e.getMessage(), e);
        }
        return Response.ok(result);
    }

}
