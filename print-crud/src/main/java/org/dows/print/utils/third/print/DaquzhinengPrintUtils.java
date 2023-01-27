package org.dows.print.utils.third.print;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Maps;
import org.apache.commons.codec.digest.DigestUtils;
import org.dows.print.utils.HttpUtils;
import org.dows.print.utils.PandDateUtils;
import org.dows.print.utils.PandStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 大趋智能打印机
 * 接口网址：https://www.yuque.com/docs/share/3138de29-7cd0-44d5-bede-c7ccf8c1c811#18c49d3eb4d10c3faeccf86751c73197
 *
 * @author user
 */
public class DaquzhinengPrintUtils {

    private static Logger logger = LoggerFactory.getLogger(DaquzhinengPrintUtils.class);

    private static final String ADD_PRINT_URL = "https://printer.juhesaas.com/openapi/addPrinter";//添加打印机
    private static final String EDIT_PRINT_URL = "https://printer.juhesaas.com/openapi/editPrinter";//修改打印机
    private static final String DEL_PRINT_URL = "https://printer.juhesaas.com/openapi/delPrinter";//删除打印机
    private static final String GET_DEVICE_STATUS_URL = "https://printer.juhesaas.com/openapi/getDeviceStatus";//查看打印机状态
    private static final String PRINT_URL = "https://printer.juhesaas.com/openapi/print";//打印小票
    private static final String GET_PRINT_STATUS_URL = "https://printer.juhesaas.com/openapi/getPrintStatus";//查看小票打印状态
    private static final String CLEAR_WAITING_QUEUE_URL = "https://printer.juhesaas.com/openapi/cleanWaitingQueue";//清空打印机队列
    private static final String DENSITY_URL = "https://printer.juhesaas.com/openapi/setDensity";//设置打印机浓度
    private static final String PAY_IN_VOICE_URL = "https://printer.juhesaas.com/openapi/payInVoice";//播放收银语音

	private static final String appid = "926508687122599936";
	private static final String secret = "0d3bcef8f009462ba3d14a11cb7ba178";

    public static void main(String[] args) {

//		getHeader();
        String sn = "570020010299";
        String printStatus = getPrintStatus(sn);
        System.out.println(printStatus);

        //设置打印墨浓度
//		String setDensity = setDensity(sn, 5);
//		System.out.println(setDensity);

        //添加打印机
//		String key="s6wr5w",name="101号测试打印机";
//		List<DaquPrintVo> voList = Lists.newArrayList();
//		DaquPrintVo vo = new DaquPrintVo();
//		vo.setKey(key);
//		vo.setName(name);
//		vo.setSn(sn);
//		voList.add(vo);
//		String addPrinter = addPrinter(voList);
//		System.out.println(addPrinter);

        //修改打印机
//		String name="101测试打印机";
//		List<DaquPrintVo> voList = Lists.newArrayList();
//		DaquPrintVo vo = new DaquPrintVo();
//		vo.setName(name);
//		vo.setSn(sn);
//		voList.add(vo);
//		String editPrinter = editPrinter(voList);
//		System.out.println(editPrinter);

        //删除打印机
//		List<String> snList = Lists.newArrayList();
//		snList.add(sn);
//		String delPrinter = delPrinter(snList);
//		System.out.println(delPrinter);

        //打印小票
//		DetailDishesVo vv = new DetailDishesVo();
//		vv.setDishesName("黄焖鸡");
//		vv.setNum(3);
//		vv.setPrice(40.11);
//		List<DetailDishesVo> vlist = Lists.newArrayList();
//		vlist.add(vv);
//		vlist.add(vv);
//		vlist.add(vv);
//		String content = getPrintIssueContent_20220122("五天日记", "堂食：101号", "加饭", vlist, 25.00, null, null, null, "上海市先番城1号2座", "021-652401240");
//		System.out.println("打印信息："+content);
//		String print = print(sn, content, true);
//		System.out.println(print);

        //查看小票打印状态
//		String getPrintStatus = getPrintStatus(sn, "927349035331792897");
//		System.out.println(getPrintStatus);

        //播放收银语音
//		String getPrintStatus = payInVoice(sn, 2,2115);
//		System.out.println(getPrintStatus);

    }


    /**
     * 播放收银语音
     *
     * @param sn         打印机编号	string	是
     * @param payChannel 支付渠道	2 微信 3 支付宝 9 云闪付
     * @param payAmount  支付金额	金额精确到‘分’，如 11.19元传值为1119，最大：2147483647
     * @return
     */
    public static String payInVoice(String sn, int payChannel, int payAmount) {

        String res = null;

        try {
            Map<String, Object> parameters = Maps.newHashMap();
            parameters.put("sn", sn);
            parameters.put("payChannel", payChannel);
            parameters.put("payAmount", payAmount);

            String bodyJson = PandStringUtils.getJsonObj(parameters);
            Map<String, Object> headerMap = getHeader(bodyJson);

            res = HttpUtils.doPost_bodyjson_headermap(PAY_IN_VOICE_URL, bodyJson, headerMap);

            logger.info("聚合呗-播放收银语音  res:{}", res);

        } catch (Exception e) {
            logger.error("播放收银语音异常" + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 查看打印小票
     *
     * @param sn      打印机编号	string	是
     * @param printId 小票id	string	是
     * @return
     */
    public static String getPrintStatus(String sn, String printId) {

        String res = null;

        try {
            Map<String, Object> parameters = Maps.newHashMap();
            parameters.put("sn", sn);
            parameters.put("printId", printId);
            String bodyJson = PandStringUtils.getJsonObj(parameters);
            Map<String, Object> headerMap = getHeader(bodyJson);

            res = HttpUtils.doPost_bodyjson_headermap(GET_PRINT_STATUS_URL, bodyJson, headerMap);

            logger.info("聚合呗-查看打印小票  res:{}", res);

        } catch (Exception e) {
            logger.error("查看打印小票异常" + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 打印小票
     *
     * @param sn      打印机编号	string	是
     * @param content 打印内容	string	否	最大长度 50
     * @param voice   是否播放语音  boolean false不播放，true播放  默认播放
     * @return
     */
    public static String print(String sn, String content, boolean voice) {

        String res = null;

        try {
            Map<String, Object> parameters = Maps.newHashMap();
            parameters.put("sn", sn);
            parameters.put("content", content);
            if (voice) {
                parameters.put("voice", "10");
            }
            String bodyJson = PandStringUtils.getJsonObj(parameters);
            Map<String, Object> headerMap = getHeader(bodyJson);

            //返回示例：{"data":{"printId":"927349035331792897","queueSize":2},"message":"OK","code":0}
            res = HttpUtils.doPost_bodyjson_headermap(PRINT_URL, bodyJson, headerMap);

            logger.info("聚合呗-打印小票  res:{}", res);

        } catch (Exception e) {
            logger.error("打印小票异常" + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 删除打印机
     *
     * @param sn   打印机编号	string	是
     * @param key  设备秘钥	string	是
     * @param name 设备名称或备注	string	否	最大长度 50
     * @param lang 优先使用语言	int	否	13-拉丁文；16-中文; 只有设备支持对应语言时才生效，不支持时使用默认设置
     * @return
     */
    public static String delPrinter(List<String> snList) {

        String res = null;

        try {

            String bodyJson = PandStringUtils.getJsonObj(snList);
            Map<String, Object> headerMap = getHeader(bodyJson);


            res = HttpUtils.doPost_bodyjson_headermap(DEL_PRINT_URL, bodyJson, headerMap);

            logger.info("聚合呗-删除打印机  res:{}", res);

        } catch (Exception e) {
            logger.error("删除打印机异常" + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 设置打印浓度
     *
     * @param sn      打印机编号	string	是
     * @param density 打印浓度  4 较淡 5 普通 6 较浓(默认) 7 浓
     * @return
     */
    public static String setDensity(String sn, int density) {

        String res = null;

        try {

            Map<String, Object> paramters = Maps.newHashMap();
            paramters.put("sn", sn);
            paramters.put("density", density);

            String bodyJson = PandStringUtils.getJsonObj(paramters);
            Map<String, Object> headerMap = getHeader(bodyJson);


            res = HttpUtils.doPost_bodyjson_headermap(DENSITY_URL, bodyJson, headerMap);


            logger.info("聚合呗-设置打印机浓度  res:{}", res);

        } catch (Exception e) {
            logger.error("设置打印机浓度异常" + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 修改打印机
     *
     * @param sn   打印机编号	string	是
     * @param key  设备秘钥	string	是
     * @param name 设备名称或备注	string	否	最大长度 50
     * @param lang 优先使用语言	int	否	13-拉丁文；16-中文; 只有设备支持对应语言时才生效，不支持时使用默认设置
     * @return
     */
    public static String editPrinter(List<DaquPrintVo> voList) {

        String res = null;

        try {

            String bodyJson = PandStringUtils.getJsonObj(voList);
            Map<String, Object> headerMap = getHeader(bodyJson);


            res = HttpUtils.doPost_bodyjson_headermap(EDIT_PRINT_URL, bodyJson, headerMap);

            logger.info("聚合呗-修改打印机  res:{}", res);

        } catch (Exception e) {
            logger.error("修改打印机异常" + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 添加打印机
     *
     * @param sn   打印机编号	string	是
     * @param key  设备秘钥	string	是
     * @param name 设备名称或备注	string	否	最大长度 50
     * @param lang 优先使用语言	int	否	13-拉丁文；16-中文; 只有设备支持对应语言时才生效，不支持时使用默认设置
     * @return
     */
    public static String addPrinter(List<DaquPrintVo> voList) {

        String res = null;

        try {

            String bodyJson = PandStringUtils.getJsonObj(voList);
            Map<String, Object> headerMap = getHeader(bodyJson);


            res = HttpUtils.doPost_bodyjson_headermap(ADD_PRINT_URL, bodyJson, headerMap);

            logger.info("聚合呗-添加打印机  res:{}", res);

        } catch (Exception e) {
            logger.error("添加打印机异常" + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 查看打印机状态
     *
     * @param sn 设备编号
     * @return
     */
    public static String getPrintStatus(String sn) {

        String res = null;

        try {
            Map<String, Object> paramters = Maps.newHashMap();
            paramters.put("sn", sn);

            String bodyJson = PandStringUtils.getJsonObj(paramters);
            Map<String, Object> headerMap = getHeader(bodyJson);


            res = HttpUtils.doPost_bodyjson_headermap(GET_DEVICE_STATUS_URL, bodyJson, headerMap);

            logger.info("聚合呗-查看打印机状态  res:{}", res);

        } catch (Exception e) {
            logger.error("查看打印机状态异常" + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 清空打印机队列
     *
     * @param sn 设备编号
     * @return
     */
    public static String cleanWaitingQueue(String sn) {

        String res = null;

        try {
            Map<String, Object> paramters = Maps.newHashMap();
            paramters.put("sn", sn);

            String bodyJson = PandStringUtils.getJsonObj(paramters);
            Map<String, Object> headerMap = getHeader(bodyJson);


            res = HttpUtils.doPost_bodyjson_headermap(CLEAR_WAITING_QUEUE_URL, bodyJson, headerMap);

            logger.info("聚合呗-清空打印机队列  res:{}", res);

        } catch (Exception e) {
            logger.error("清空打印机队列异常" + e.getMessage(), e);
        }
        return res;
    }


    /**
     * 请求header组装参数
     *
     * @param paramters
     * @return
     */
    private static Map<String, Object> getHeader(String bodyJson) {

        Map<String, Object> maps = Maps.newHashMap();

        try {
            String uid = PandStringUtils.getUUID();
//            String appId = GlobalProperties.getApplicationByKeyForBundel("print.daquzhineng.appid");
//            String appSecret = GlobalProperties.getApplicationByKeyForBundel("print.daquzhineng.secret");
			String appId = appid;
			String appSecret = secret;
            Long stime = System.currentTimeMillis();

            String originContext = uid + appId + stime + appSecret;

            if (PandStringUtils.isNotBlank(bodyJson)) {
                originContext += bodyJson;
            }

            String sign = DigestUtils.md5Hex(originContext);

            maps.put("Content-Type", "application/json;charset=UTF-8");
            maps.put("appid", appId);
            maps.put("uid", uid);
            maps.put("stime", stime);
            maps.put("sign", sign);

            logger.info("聚合呗header信息：{}", PandStringUtils.getJsonObj(maps));

        } catch (Exception e) {
            logger.error("获取聚合呗HEADER异常" + e.getMessage(), e);
        }
        return maps;
    }

    /**
     * 组装出单打印内容
     *
     * @param title       出单标题    “堂食72号”
     * @param remark      备注    “环保单，顾客不需要附带餐具”
     * @param voList      菜品集合
     * @param dishesName  菜品名称
     * @param num         份数
     * @param price       价格
     * @param realPrice   实付金额
     * @param contactName 联系人姓名
     * @param address     联系人地址
     * @param phone       联系人电话
     * @return
     */
    public static String getPrintIssueContent(String title, String remark, List<DetailDishesVo> voList, double realPrice,
                                              String contactName, String address, String phone) {

        String content = null, mphone = phone;
        if (phone != null && phone.length() > 10 && phone.indexOf("*") == -1) {//隐藏手机号
            mphone = phone.substring(0, 3) + "****" + phone.substring(7);
        }
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("<LEFT><font# bolder=1 height=2 width=2>商家小票</font#></LEFT><BR>");
            sb.append("<C><font# bolder=1 height=2 width=2>***" + title + "***</font#></C><BR>");
            sb.append("*t_4oYtL0rR*<BR>");
            sb.append("<BR>");
            sb.append("<LEFT>下单时间：" + PandDateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss") + "</LEFT><BR>");
            sb.append("<LEFT>期望送达时间：立即送餐</LEFT><BR><C>--------------------------------</C><BR>");
            sb.append("<LEFT><font# bolder=1 height=2 width=2>备注：" + remark + "</font#></LEFT><BR>");
            sb.append("<C>********************************</C><BR>");
            sb.append("<C>------------0------------</C><BR>");
            for (DetailDishesVo vo : voList) {
                sb.append("<font# bolder=1 height=2 width=1>" + vo.getDishesName() + "     ×" + vo.getNum() + "   ￥"
                        + vo.getPrice() + "   </font#><BR>");
            }
//			sb.append("<C>--------------优惠--------------</C><BR>");
//			sb.append("<LEFT><font# bolder=0 height=2 width=1>[满1.0元减1.0元]</font#></LEFT><BR>");
            sb.append("<C>--------------------------------</C><BR>");
//			sb.append("<LEFT>配送费：￥0.01</LEFT><BR><LEFT>原价：￥45.01</LEFT><BR>");
            sb.append("<LEFT>实付：￥" + realPrice + "</LEFT><BR>");
            sb.append("<C>--------------------------------</C><BR>");
            sb.append("<LEFT><font# bolder=1 height=2 width=1>" + address + "</font#></LEFT><BR>");
            sb.append("<LEFT><font# bolder=1 height=2 width=1>" + contactName + "</font#></LEFT><BR>");
            sb.append("<LEFT><font# bolder=1 height=2 width=1>" + mphone + "</font#></LEFT><BR>");
            sb.append("<C>****#3完****</C><BR>");

            logger.info("聚合呗组装打印信息：{}", sb.toString());
            return sb.toString();
        } catch (Exception e) {
            logger.error("组装打印内容异常" + e.getMessage(), e);
        }
        return content;
    }

    public static String getPrintDeskOrder(String shopName, String title,  List<DetailDishesVo> voList, double realPrice,
                                                       String sysName,Integer numberCount, String shopAddress, String shopMobile, String affiliatePriceJson) {

        try {
            StringBuilder sb = new StringBuilder();
            sb.append("<C><font# height=2 width=2>" + shopName + "</font#></C><BR>");
            sb.append("<BR>");
            sb.append("<C><font# bolder=1 height=2 width=2>" + title + "</font#></C><BR>");
            sb.append("<C>---------------------------------</C><BR>");
            sb.append("<C>")
              .append(PrintUtil.alignText("人数：" + numberCount, 'l', 10))
              .append(PrintUtil.alignText("操作员：" + sysName, 'r', 10))
              .append("</C>");
            sb.append("<BR>");
            sb.append("<C>------------菜品------------</C><BR>");
            int length =  voList.stream().map(DetailDishesVo::getDishesName).max(Comparator.comparing(String::length)).get().length();
            for (DetailDishesVo vo : voList) {
                // 补齐
                StringBuilder dishesName = new StringBuilder(vo.getDishesName());
                int padding = length - dishesName.length();
                for (int i = 0; i < padding; i++) {
                    // 全角模式补齐
                    dishesName.append("　");
                }

                sb.append("<LEFT><font# bolder=1 height=2 width=1>" +
                                  PrintUtil.alignText(dishesName.toString(), 'l', 8) +
                                  PrintUtil.alignText("×" + vo.getNum(), 'c', 6) +
                                  PrintUtil.alignText("￥" + vo.getPrice(), 'r', 6) +
                                  "</font#></LEFT><BR>");
            }
            sb.append("<C>--------------------------------</C><BR>");
            Double couponsPrice = null, updateWxpay = null, cashPrice = null, otherPrice = null,
                    meituanPrice = null, bankPrice = null, koubeiPrice = null, tiktokPrice = null;
            //{"koubeiPrice":0.1,"tiktokPrice":0.1,"otherPrice":2.4,
            // "bankPrice":0.1,"cashPrice":0.1,"meituanPrice":0.1,"merCouponsPrice":0.1}
            if (PandStringUtils.isNotBlank(affiliatePriceJson)) {
                JSONObject parseObject = JSON.parseObject(affiliatePriceJson);
                //打包费
                if (parseObject != null) {
                    //优惠折扣
                    couponsPrice = parseObject.getDouble("merCouponsPrice");
                    //支付明细--微信支付补充
                    updateWxpay = parseObject.getDouble("updateWxpay");
                    //支付明细--现金支付
                    cashPrice = parseObject.getDouble("cashPrice");
                    meituanPrice = parseObject.getDouble("meituanPrice");
                    bankPrice = parseObject.getDouble("bankPrice");
                    koubeiPrice = parseObject.getDouble("koubeiPrice");
                    tiktokPrice = parseObject.getDouble("tiktokPrice");
                    otherPrice = parseObject.getDouble("otherPrice");
                }

            }
            if (couponsPrice != null) {
                sb.append("<C>优惠/折扣：").append(couponsPrice).append("</C><BR>");
            }
            if (updateWxpay != null) {
                sb.append("<C>微信支付：").append(updateWxpay).append("</C><BR>");
            }
            if (cashPrice != null) {
                sb.append("<C>现金支付：").append(cashPrice).append("</C><BR>");
            }
            if (meituanPrice != null) {
                sb.append("<C>美团点评：").append(meituanPrice).append("</C><BR>");
            }
            if (bankPrice != null) {
                sb.append("<C>银行券：").append(bankPrice).append("</C><BR>");
            }
            if (koubeiPrice != null) {
                sb.append("<C>口碑券：").append(koubeiPrice).append("</C><BR>");
            }
            if (tiktokPrice != null) {
                sb.append("<C>抖音券：").append(tiktokPrice).append("</C><BR>");
            }
            if (otherPrice != null) {
                sb.append("<C>其他：").append(otherPrice).append("</C><BR>");
            }
            sb.append("<C>--------------------------------</C><BR>");
            sb.append("<C><font# bolder=1 height=2 width=2>总计金额：").append(realPrice).append("</font#></C><BR>");
            sb.append("<C>————————————————————————————————</C><BR>");
            sb.append("<BR>");
            if (PandStringUtils.isNotBlank(shopAddress)) {
                sb.append("<C>地址：").append(shopAddress).append("</C><BR>");
            }
            if (PandStringUtils.isNotBlank(shopMobile)) {
                sb.append("<C>电话：").append(shopMobile).append("</C><BR>");
            }
            sb.append("<C>").append(PandDateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss")).append("</C><BR>");

            logger.info("聚合呗组装台桌订单打印信息：{}", sb.toString());
            return sb.toString();
        } catch (Exception e) {
            logger.error("组装台桌打印内容异常" + e.getMessage(), e);
        }
        return null;
    }
    /**
     * 出单打印内容（堂食）--20220122版格式
     *
     * @param shopName           商户名称    “五天日记”  string必填
     * @param title              出单标题    “堂食72号”  string必填
     * @param remark             备注    “环保单，顾客不需要附带餐具”  string选填
     * @param voList             菜品集合       json字符串集合 string必填
     * @param dishesName         菜品名称
     * @param num                份数
     * @param price              价格
     * @param realPrice          实付金额        double必填
     * @param contactName        联系人姓名    string选填
     * @param address            联系人地址    string选填
     * @param phone              联系人电话    string必填(如果真实场景没有，建议***传入)
     * @param shopAddress        商户地址        string选填
     * @param shopMobile         商户电话        string选填
     * @param affiliatePriceJson 其他附属信息        double选填
     * @return
     */
    public static String getPrintIssueContent_20220122(String shopName, String title, String remark, List<DetailDishesVo> voList, double realPrice,
                                                       String contactName, String address, String phone, String shopAddress, String shopMobile, String affiliatePriceJson) {

        String content = null, mphone = phone;
        if (phone != null && phone.length() > 10 && phone.indexOf("*") == -1) {//隐藏手机号
            mphone = phone.substring(0, 3) + "****" + phone.substring(7);
        }
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("<C><font# height=2 width=2>" + shopName + "</font#></C><BR>");
            sb.append("<BR>");
            sb.append("<C><font# bolder=1 height=2 width=2>" + title + "</font#></C><BR>");
            sb.append("<C>--------------------------------</C><BR>");
            if (PandStringUtils.isNotBlank(remark)) {
                sb.append("<C><font# bolder=1 height=1 width=1>备注：" + remark + "</font#></C><BR>");
                sb.append("<C></C><BR>");
            }
            if (PandStringUtils.isNotBlank(contactName) && PandStringUtils.isNotBlank(address)) {
                sb.append("<C><font# bolder=1 height=1 width=1>配送：" + address + "</font#></C><BR>");
                sb.append("<C><font# bolder=1 height=1 width=1>" + contactName + "   " + mphone + "</font#></C><BR>");
            }
//			sb.append("<C><font# bolder=1 height=2 width=2>备注："+remark+"</font#></C><BR>");
            sb.append("<C>------------菜品------------</C><BR>");
            int length =  voList.stream().map(DetailDishesVo::getDishesName)
                    .collect(Collectors.maxBy(Comparator.comparing(String::length)))
                    .get().length();
            for (DetailDishesVo vo : voList) {
                // 补齐
                String dishesName = vo.getDishesName();
                int padding = length - dishesName.length();
                for (int i = 0; i < padding; i++) {
                    // 全角模式补齐
                    dishesName += "　";
                }

                sb.append("<LEFT><font# bolder=1 height=2 width=1>" +
                        PrintUtil.alignText(dishesName, 'l', 8) +
                        PrintUtil.alignText("×" + vo.getNum(), 'c', 6) +
                        PrintUtil.alignText("￥" + vo.getPrice(), 'r', 6) +
                        "</font#></LEFT><BR>");
            }
            sb.append("<C>--------------------------------</C><BR>");
            Double couponsPrice = null, updateWxpay = null;
            if (PandStringUtils.isNotBlank(affiliatePriceJson)) {
                JSONObject parseObject = JSON.parseObject(affiliatePriceJson);//打包费
                Double packagePrice = null;
                Double distributePrice = null;
                if (parseObject != null) {
                    packagePrice = parseObject.getDouble("package");//打包费
                    distributePrice = parseObject.getDouble("distribute");//配送费
                    couponsPrice = parseObject.getDouble("merCouponsPrice");//优惠折扣
                    updateWxpay = parseObject.getDouble("updateWxpay");//支付明细--微信支付补充
                }
                if (packagePrice != null && packagePrice > 0.0) {
                    sb.append("<C>打包费：" + packagePrice + "</C><BR>");
                }
                if (distributePrice != null && distributePrice > 0.0) {
                    sb.append("<C>配送费：" + distributePrice + "</C><BR>");
                }
            }
            if (couponsPrice == null) {
                couponsPrice = 0.00;
            }
            sb.append("<C>优惠/折扣：" + couponsPrice + "</C><BR>");
            if (updateWxpay != null) {
                sb.append("<C>微信支付：" + updateWxpay + "</C><BR>");
            }
            sb.append("<C>--------------------------------</C><BR>");
            sb.append("<C><font# bolder=1 height=2 width=2>实收：" + realPrice + "</font#></C><BR>");
            sb.append("<C>————————————————————————————————</C><BR>");
            sb.append("<BR>");
            if (PandStringUtils.isNotBlank(shopAddress)) {
                sb.append("<C>地址：" + shopAddress + "</C><BR>");
            }
            if (PandStringUtils.isNotBlank(shopMobile)) {
                sb.append("<C>电话：" + shopMobile + "</C><BR>");
            }
            sb.append("<C>" + PandDateUtils.dateToStr(new Date(), "yyyy-MM-dd HH:mm:ss") + "</C><BR>");
			/*sb.append("<LEFT>期望送达时间：立即送餐</LEFT><BR><C>--------------------------------</C><BR>");
			sb.append("<C>********************************</C><BR>");
//			sb.append("<C>--------------优惠--------------</C><BR>");
//			sb.append("<LEFT><font# bolder=0 height=2 width=1>[满1.0元减1.0元]</font#></LEFT><BR>");
			sb.append("<C>--------------------------------</C><BR>");
//			sb.append("<LEFT>配送费：￥0.01</LEFT><BR><LEFT>原价：￥45.01</LEFT><BR>");
			sb.append("<LEFT>实付：￥"+realPrice+"</LEFT><BR>");
			sb.append("<C>--------------------------------</C><BR>");
			sb.append("<LEFT><font# bolder=1 height=2 width=1>"+address+"</font#></LEFT><BR>");
			sb.append("<LEFT><font# bolder=1 height=2 width=1>"+contactName+"</font#></LEFT><BR>");
			sb.append("<LEFT><font# bolder=1 height=2 width=1>"+mphone+"</font#></LEFT><BR>");
			sb.append("<C>****#3完****</C><BR>");*/

            logger.info("聚合呗组装打印信息：{}", sb.toString());
            return sb.toString();
        } catch (Exception e) {
            logger.error("组装打印内容异常" + e.getMessage(), e);
        }
        return content;
    }


}
