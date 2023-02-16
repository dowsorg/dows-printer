package org.dows.printer.utils.third.print;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.dows.printer.utils.PandDateUtils;
import org.dows.printer.vo.DetailDishesVo;
import org.dows.printer.vo.JuhePrintContentVo;

import java.util.Date;

/**
 * 大趋智能打印机
 * 接口网址：https://www.yuque.com/docs/share/3138de29-7cd0-44d5-bede-c7ccf8c1c811#18c49d3eb4d10c3faeccf86751c73197
 *
 * @author user
 */
@Slf4j
public class JuhePrintContentUtils {

//    public static void main(String[] args) {
//        //菜品
//        List<DetailDishesVo> voList = new LinkedList<>();
//        voList.add(new DetailDishesVo("龙虾泡饭", 1, 38.00,null));
//        voList.add(new DetailDishesVo("芝士焗咖喱牛腩牛腩饭", 1, 38.00,null));
//        voList.add(new DetailDishesVo("炙烤猪扒饭", 1, 38.00,null));
//        //组装打印内容-总单
//        JuhePrintContentVo JuhePrintContentVo = new JuhePrintContentVo("五天日记【松江印象城店】", "A01", "6", "刘大牙", voList, "98.60", "苏庆北路246号印象城B1-901", "021-09001234", 1, null);
//        getMasterContent(JuhePrintContentVo);
//    }

    /**
     * 组装打印内容-总单     店名、桌号、人数、操作人、菜品、总计金额、地址、电话、日期
     *
     * @param storeName          店名    五天日记【松江印象城店】
     * @param tableNo            桌号    “A01”
     * @param pepNum             人数  "6"
     * @param operator           操作员
     * @param dishesVoList       菜品集合
     * @param amountTotal        总计金额
     * @param address            地址
     * @param phone              电话
     * @param affiliatePriceJson 优惠信息
     * @param type               小票类型    1-总单  2-结账单（优惠信息）  3、制作单  4、加菜  5退菜
     * @return
     */
    public static String getContent(JuhePrintContentVo printContentVo) {
        String result = "";
        try {
            int type = printContentVo.getType();
            if (ObjectUtils.isNotEmpty(type)) {
                if (1 == type || 2 == type) {
                    result = getMasterContent(printContentVo);
                } else {
                    result = getMakeContent(printContentVo);
                }
            }
        } catch (Exception e) {
            log.error("状态异常" + e.getMessage(), e);
        }
        return result;
    }


    /**
     * 组装打印内容-总单     店名、桌号、人数、操作人、菜品、总计金额、地址、电话、日期
     *
     * @param storeName   店名    五天日记【松江印象城店】
     * @param tableNo     桌号    “A01”
     * @param pepNum      人数  "6"
     * @param operator    操作员
     * @param voList      菜品集合
     * @param amountTotal 总计金额
     * @param address     地址
     * @param phone       电话
     * @return
     */
    public static String getMasterContent(JuhePrintContentVo printContentVo) {
        //开始组装
        StringBuilder con = new StringBuilder();
        //居中 不加粗  放大2   店名
        con.append("<C><font# bolder=0 height=2 width=2>").append(printContentVo.getStoreName()).append("</font#></C><BR>");
        con.append("<BR>");
        con.append("<C><font# bolder=1 height=3 width=3>桌号：").append(printContentVo.getTableNo()).append("</font#></C><BR>");
        con.append("<C>--------------------------------</C><BR>");
        con.append("<BR>");
        con.append("<C><font# bolder=1 height=2 width=2>人数：</font#><font# bolder=0 height=1 width=1>")
                .append(PrintUtil.alignText(printContentVo.getPepNum(), 'l', 6)).append("</font#>")
                .append("<font# bolder=1 height=2 width=2>操作员：</font#><font# bolder=0 height=1 width=1>")
                .append(printContentVo.getOperator())
                .append("</font#></C><BR>");
        con.append("<BR>");
        con.append("<C>----------- <font# bolder=1 height=2 width=2>菜品</font#><font# bolder=0 height=1 width=1> -----------</font#></C><BR>");
        con.append("<BR>");
        int length = 7;
        for (DetailDishesVo vo : printContentVo.getDishesVoList()) {
            String name = vo.getDishesName();
            String[] overName = getOverArr(name, length);
            //判断菜名是否超长
            if (ArrayUtils.isNotEmpty(overName)) {
                name = name.substring(0, 7);
            }
            // 补齐
            StringBuilder dishesName = new StringBuilder(name);
            int nameLength = dishesName.length();
            int padding = length - nameLength;
            for (int i = 0; i < padding; i++) {
                // 全角模式补齐
                dishesName.append("　");
            }
            con.append("<LEFT><font# bolder=0 height=2 width=1>" +
                    PrintUtil.alignText(dishesName.toString(), 'l', 8) +
                    PrintUtil.alignText("" + vo.getNum(), 'c', 6) +
                    PrintUtil.alignText("" + vo.getPrice(), 'r', 6) +
                    "</font#></LEFT><BR>");
            if (ArrayUtils.isNotEmpty(overName)) {
                for (String s : overName) {
                    con.append("<LEFT><font# bolder=0 height=2 width=1>").append(s).append("</font#></LEFT><BR>");
                }
            }
        }
        con.append("<BR>");
        con.append("<C>--------------------------------</C><BR>");
        if (2 == printContentVo.getType()) {//结账单-优惠信息
            con.append("<BR>");
            Double couponsPrice = 0.00, meituanPrice = 0.00, updateWxpay = 0.00; //优惠信息
            JSONObject parseObject = JSON.parseObject(printContentVo.getAffiliatePriceJson());
            if (ObjectUtils.isNotEmpty(parseObject.getDouble("merCouponsPrice"))) {
                couponsPrice = parseObject.getDouble("merCouponsPrice");//优惠折扣
            }
            if (ObjectUtils.isNotEmpty(parseObject.getDouble("meituanPrice"))) {
                meituanPrice = parseObject.getDouble("meituanPrice");//美团劵
            }
            if (ObjectUtils.isNotEmpty(parseObject.getDouble("updateWxpay"))) {
                updateWxpay = parseObject.getDouble("updateWxpay");//微信支付
            }
            con.append("<C><font# bolder=0 height=2 width=2>  优惠/折扣:    ").append(couponsPrice).append("</font#></C><BR>");
            con.append("<C><font# bolder=0 height=2 width=2>  美团劵：      ").append(meituanPrice).append("</font#></C><BR>");
            con.append("<C><font# bolder=0 height=2 width=2>  微信支付:     ").append(updateWxpay).append("</font#></C><BR>");
            con.append("<C>--------------------------------</C><BR>");
        }
        con.append("<C><font# bolder=0 height=3 width=3>总计金额：").append(printContentVo.getAmountTotal()).append("</font#></C><BR>");
        con.append("<C>————————————————————————————————</C><BR>");
        con.append("<BR>");
        con.append("<C><font# bolder=10 height=1 width=1>地址：").append(printContentVo.getAddress()).append("</font#></C><BR>");
        con.append("<C><font# bolder=10 height=1 width=1>电话：").append(printContentVo.getPhone()).append("</font#></C><BR>");
        con.append("<BR>");
        con.append("<C>").append(PandDateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")).append("</C><BR>");

        System.out.println(con.toString());
        return con.toString();
    }

    /**
     * 组装打印内容-制作单     桌号、菜名、份数、备注、日期
     *
     * @param tableNo    桌号    “A01”
     * @param dishesName 菜名
     * @param num        份数
     * @param comment    备注
     * @param type       类型 1、制作单  2、加菜  3退菜
     * @return
     */
    public static String getMakeContent(JuhePrintContentVo makeContentVo) {
        //开始组装
        StringBuilder con = new StringBuilder();
        con.append("<C><font# bolder=1 height=3 width=3>桌号：").append(makeContentVo.getTableNo());
        if (4 == makeContentVo.getType()) {
            con.append("  加菜");
        }
        if (5 == makeContentVo.getType()) {
            con.append("  退菜");
        }
        con.append("</font#></C><BR>");
        con.append("<C>--------------------------------</C><BR>");
        con.append("<BR>");
        con.append("<BR>");
        int length = 7;
        //获取菜品对象
        DetailDishesVo detailDishesVo = makeContentVo.getDishesVoList().get(0);
        String name = detailDishesVo.getDishesName();
        String[] overName = getOverArr(name, length);
        //判断菜名是否超长
        if (ArrayUtils.isNotEmpty(overName)) {
            name = name.substring(0, 7);
        }
        con.append("<LEFT><font# bolder=1 height=3 width=3>")
                .append(PrintUtil.alignText(name, 'r', 8))
                .append(detailDishesVo.getNum())
                .append("份</font#></LEFT><BR>");
        // 菜名补齐
        if (ArrayUtils.isNotEmpty(overName)) {
            for (String s : overName) {
                con.append("<LEFT><font# bolder=1 height=3 width=3>").append(s).append("</font#></LEFT><BR>");
            }
        }
        con.append("<BR>");
        con.append("<BR>");
        con.append("<C><font# bolder=1 height=1 width=1>备注：").append(detailDishesVo.getComment()).append("</font#></C><BR>");
        con.append("<BR>");
        con.append("<BR>");
        con.append("<C>").append(PandDateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")).append("</C><BR>");

        System.out.println(con.toString());
        return con.toString();
    }


//    /**
//     * 组装打印内容-制作单     桌号、菜名、份数、备注、日期
//     *
//     * @param tableNo    桌号    “A01”
//     * @param dishesName 菜名
//     * @param num        份数
//     * @param comment    备注
//     * @param type       类型 1、制作单  2、加菜  3退菜
//     * @return
//     */
//    public static String getMakeContent(DaquPrintMakeContentVo makeContentVo) {
//        //开始组装
//        StringBuilder con = new StringBuilder();
//        con.append("<C><font# bolder=1 height=3 width=3>桌号：").append(makeContentVo.getTableNo());
//        if (2 == makeContentVo.getType()) {
//            con.append("  加菜");
//        }
//        if (3 == makeContentVo.getType()) {
//            con.append("  退菜");
//        }
//        con.append("</font#></C><BR>");
//        con.append("<C>--------------------------------</C><BR>");
//        con.append("<BR>");
//        con.append("<BR>");
//        int length = 7;
//        String name = makeContentVo.getDishesName();
//        String[] overName = getOverArr(name, length);
//        //判断菜名是否超长
//        if (ArrayUtils.isNotEmpty(overName)) {
//            name = name.substring(0, 7);
//        }
//        con.append("<LEFT><font# bolder=1 height=3 width=3>")
//                .append(PrintUtil.alignText(name, 'r', 8))
//                .append(makeContentVo.getNum())
//                .append("份</font#></LEFT><BR>");
//        // 菜名补齐
//        if (ArrayUtils.isNotEmpty(overName)) {
//            for (String s : overName) {
//                con.append("<LEFT><font# bolder=1 height=3 width=3>").append(s).append("</font#></LEFT><BR>");
//            }
//        }
//        con.append("<BR>");
//        con.append("<BR>");
//        con.append("<C><font# bolder=1 height=1 width=1>备注：").append(makeContentVo.getComment()).append("</font#></C><BR>");
//        con.append("<BR>");
//        con.append("<BR>");
//        con.append("<C>").append(PandDateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")).append("</C><BR>");
//
//        System.out.println(con.toString());
//        return con.toString();
//    }


//    /**
//     * 组装打印内容-结账单     店名、桌号、人数、操作人、菜品、总计金额、地址、电话、日期
//     *
//     * @param storeName          店名    五天日记【松江印象城店】
//     * @param tableNo            桌号    “A01”
//     * @param pepNum             人数  "6"
//     * @param operator           操作员
//     * @param voList             菜品集合
//     * @param amountTotal        总计金额
//     * @param address            地址
//     * @param phone              电话
//     * @param affiliatePriceJson 其他附属信息
//     * @return
//     */
//    public static String getCheckoutContent(JuhePrintContentVo printContentVo) {
//        //开始组装
//        StringBuilder con = new StringBuilder();
//        //居中 不加粗  放大2   店名
//        con.append("<C><font# bolder=0 height=2 width=2>").append(printContentVo.getStoreName()).append("</font#></C><BR>");
//        con.append("<BR>");
//        con.append("<C><font# bolder=1 height=3 width=3>桌号：").append(printContentVo.getTableNo()).append("</font#></C><BR>");
//        con.append("<C>--------------------------------</C><BR>");
//        con.append("<BR>");
//        con.append("<C><font# bolder=1 height=2 width=2>人数：</font#><font# bolder=0 height=1 width=1>")
//                .append(PrintUtil.alignText(printContentVo.getPepNum(), 'l', 6)).append("</font#>")
//                .append("<font# bolder=1 height=2 width=2>操作员：</font#><font# bolder=0 height=1 width=1>")
//                .append(printContentVo.getOperator())
//                .append("</font#></C><BR>");
//        con.append("<BR>");
//        con.append("<C>----------- <font# bolder=1 height=2 width=2>菜品</font#><font# bolder=0 height=1 width=1> -----------</font#></C><BR>");
//        con.append("<BR>");
//        int length = 7;
//        for (DetailDishesVo vo : printContentVo.getVoList()) {
//            String name = vo.getDishesName();
//            String[] overName = getOverArr(name, length);
//            //判断菜名是否超长
//            if (ArrayUtils.isNotEmpty(overName)) {
//                name = name.substring(0, 7);
//            }
//            // 补齐
//            StringBuilder dishesName = new StringBuilder(name);
//            int nameLength = dishesName.length();
//            int padding = length - nameLength;
//            for (int i = 0; i < padding; i++) {
//                // 全角模式补齐
//                dishesName.append("　");
//            }
//            con.append("<LEFT><font# bolder=0 height=2 width=1>" +
//                    PrintUtil.alignText(dishesName.toString(), 'l', 8) +
//                    PrintUtil.alignText("" + vo.getNum(), 'c', 6) +
//                    PrintUtil.alignText("" + vo.getPrice(), 'r', 6) +
//                    "</font#></LEFT><BR>");
//            if (ArrayUtils.isNotEmpty(overName)) {
//                for (String s : overName) {
//                    con.append("<LEFT><font# bolder=0 height=2 width=1>").append(s).append("</font#></LEFT><BR>");
//                }
//            }
//        }
//        con.append("<C>--------------------------------</C><BR>");
//        con.append("<BR>");
//        Double couponsPrice = 0.00, meituanPrice = 0.00, updateWxpay = 0.00; //优惠信息
//        JSONObject parseObject = JSON.parseObject(printContentVo.getAffiliatePriceJson());
//        if (ObjectUtils.isNotEmpty(parseObject.getDouble("merCouponsPrice"))) {
//            couponsPrice = parseObject.getDouble("merCouponsPrice");//优惠折扣
//        }
//        if (ObjectUtils.isNotEmpty(parseObject.getDouble("meituanPrice"))) {
//            meituanPrice = parseObject.getDouble("meituanPrice");//美团劵
//        }
//        if (ObjectUtils.isNotEmpty(parseObject.getDouble("updateWxpay"))) {
//            updateWxpay = parseObject.getDouble("updateWxpay");//微信支付
//        }
//        con.append("<C><font# bolder=0 height=2 width=2>  优惠/折扣:    ").append(couponsPrice).append("</font#></C><BR>");
//        con.append("<C><font# bolder=0 height=2 width=2>  美团劵：      ").append(meituanPrice).append("</font#></C><BR>");
//        con.append("<C><font# bolder=0 height=2 width=2>  微信支付:     ").append(updateWxpay).append("</font#></C><BR>");
//        con.append("<C>--------------------------------</C><BR>");
//        con.append("<C><font# bolder=0 height=3 width=3>总计金额：").append(printContentVo.getAmountTotal()).append("</font#></C><BR>");
//        con.append("<C>————————————————————————————————</C><BR>");
//        con.append("<BR>");
//        con.append("<C><font# bolder=10 height=1 width=1>地址：").append(printContentVo.getAddress()).append("</font#></C><BR>");
//        con.append("<C><font# bolder=10 height=1 width=1>电话：").append(printContentVo.getPhone()).append("</font#></C><BR>");
//        con.append("<BR>");
//        con.append("<C>").append(PandDateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")).append("</C><BR>");
//
//        System.out.println(con.toString());
//        return con.toString();
//    }


    public static String[] getOverArr(String dishesName, int length) {
        int nameLength = dishesName.length();
        int padding = length - nameLength;
        String[] overName = null;
        if (padding < 0) {
            int i1 = nameLength / 7;
            overName = new String[i1];
            int i = nameLength % 7;
            for (int j = 0; j < i1 - 1; j++) {
                int i2 = (1 + j) * 7;
                overName[j] = dishesName.substring(i2, i2 + 7);
            }
            overName[i1 - 1] = dishesName.substring(dishesName.length() - i);
        }
        return overName;
    }


}
