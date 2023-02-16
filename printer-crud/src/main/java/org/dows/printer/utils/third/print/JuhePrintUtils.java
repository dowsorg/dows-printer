package org.dows.printer.utils.third.print;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.dows.printer.enums.ApiMethodTypeEnum;
import org.dows.printer.utils.PandStringUtils;
import org.dows.printer.utils.RestTemplateUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 聚合呗-打印机
 * 接口网址：https://gitee.com/jhbopenapi/juhebei-opendoc/tree/master
 *
 * @author user
 */
@Component
@Slf4j
public class JuhePrintUtils {

    @Value("${print.juhe.appid}")
    private String appId;
    @Value("${print.juhe.secret}")
    private String appSecret;

    @Value("${print.juhe.url.print}")//打印小票
    private String printUrl;
    @Value("${print.juhe.url.getPrintStatus}")//查询小票打印状态
    private String getPrintStatusUrl;
    @Value("${print.juhe.url.payInVoice}")//播放收银语音
    private String payInVoiceUrl;
    @Value("${print.juhe.url.addPrinter}")//增加打印机
    private String addPrinterUrl;
    @Value("${print.juhe.url.editPrinter}")//修改打印机
    private String editPrinterUrl;
    @Value("${print.juhe.url.delPrinter}")//删除打印机
    private String delPrinterUrl;
    @Value("${print.juhe.url.setDensity}")//设置打印浓度
    private String setDensityUrl;
    @Value("${print.juhe.url.setVolume}")//设置音量
    private String setVolumeUrl;
    @Value("${print.juhe.url.getDeviceStatus}")//查询打印机状态
    private String getDeviceStatusUrl;
    @Value("${print.juhe.url.cleanWaitingQueue}")//清空设备待打印队列
    private String cleanWaitingQueueUrl;
    @Value("${print.juhe.url.getSimInfo}")//查询设备流量卡信息(开发中)
    private String getSimInfoUrl;
    @Value("${print.juhe.url.setImage}")//打印图片设置
    private String setImageUrl;

//    public static void main(String[] args) {
//
////		getHeader();
//        String sn = "570020010299";
//        JSONObject printStatus = getPrintStatus(sn);
//        log.debug("查看打印机状态:{}", printStatus);
//
////          设置打印墨浓度
////        JSONObject setDensity = setDensity(sn, 5);
////        log.debug("设置打印墨浓度:{}", setDensity);
//
//        //添加打印机
////		String key="s6wr5w",name="101号测试打印机";
////		List<DaquPrintVo> voList = Lists.newArrayList();
////		DaquPrintVo vo = new DaquPrintVo();
////		vo.setKey(key);
////		vo.setName(name);
////		vo.setSn(sn);
////		voList.add(vo);
////		String addPrinter = addPrinter(voList);
////		System.out.println(addPrinter);
//
//        //修改打印机
////		String name="101测试打印机";
////		List<DaquPrintVo> voList = Lists.newArrayList();
////		DaquPrintVo vo = new DaquPrintVo();
////		vo.setName(name);
////		vo.setSn(sn);
////		voList.add(vo);
////		String editPrinter = editPrinter(voList);
////		System.out.println(editPrinter);
//
//        //删除打印机
////		List<String> snList = Lists.newArrayList();
////		snList.add(sn);
////		String delPrinter = delPrinter(snList);
////		System.out.println(delPrinter);
//
//        //打印小票
////		DetailDishesVo vv = new DetailDishesVo();
////		vv.setDishesName("黄焖鸡");
////		vv.setNum(3);
////		vv.setPrice(40.11);
////		List<DetailDishesVo> vlist = Lists.newArrayList();
////		vlist.add(vv);
////		vlist.add(vv);
////		vlist.add(vv);
////		String content = getPrintIssueContent_20220122("五天日记", "堂食：101号", "加饭", vlist, 25.00, null, null, null, "上海市先番城1号2座", "021-652401240");
////		System.out.println("打印信息："+content);
////		String print = print(sn, content, true);
////		System.out.println(print);
//
//        //查看小票打印状态
////		String getPrintStatus = getPrintStatus(sn, "927349035331792897");
////		System.out.println(getPrintStatus);
//
//        //播放收银语音
////		String getPrintStatus = payInVoice(sn, 2,2115);
////		System.out.println(getPrintStatus);
//
//    }

    /**
     * 添加打印机
     *
     * @param sn   打印机编号	string	是
     * @param key  设备秘钥	string	是
     * @param name 设备名称或备注	string	否	最大长度 50
     * @param lang 优先使用语言	int	否	13-拉丁文；16-中文; 只有设备支持对应语言时才生效，不支持时使用默认设置
     * @return
     */
    public JSONObject addPrinter(List<DaquPrintVo> voList) {

        JSONObject res = null;

        try {

            String bodyJson = PandStringUtils.getJsonObj(voList);

            res = juheHttp(addPrinterUrl, bodyJson);

            log.info("聚合呗-添加打印机  res:{}", res);
        } catch (Exception e) {
            log.error("添加打印机异常" + e.getMessage(), e);
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
    public JSONObject setDensity(String sn, int density) {

        JSONObject res = null;

        try {

            Map<String, Object> paramters = Maps.newHashMap();
            paramters.put("sn", sn);
            paramters.put("density", density);

            String bodyJson = PandStringUtils.getJsonObj(paramters);

            res = juheHttp(setDensityUrl, bodyJson);

            log.info("聚合呗-设置打印机浓度  res:{}", res);

        } catch (Exception e) {
            log.error("设置打印机浓度异常" + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 查看打印机状态
     *
     * @param sn 设备编号
     * @return
     */
    public JSONObject getPrintStatus(String sn) {

        JSONObject res = null;

        try {
            Map<String, Object> paramters = Maps.newHashMap();
            paramters.put("sn", sn);

            String bodyJson = PandStringUtils.getJsonObj(paramters);

            res = juheHttp(getDeviceStatusUrl, bodyJson);

            //- onlineStatus	设备联网状态: 0 当前离线 1 在线	int
            //- workStatus	设备工作状态: 0 就绪, 1 打印中, 2 缺纸, 3 过温, 4 打印故障	int
            //- workStatusDesc	设备工作状态说明	string
            log.info("聚合呗-查看打印机状态  res:{}", res);

        } catch (Exception e) {
            log.error("查看打印机状态异常" + e.getMessage(), e);
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
    public JSONObject print(String sn, String content, boolean voice) {

        JSONObject res = null;

        try {
            Map<String, Object> parameters = Maps.newHashMap();
            parameters.put("sn", sn);
            parameters.put("content", content);
            if (voice) {
                parameters.put("voice", "10");
            }
            String bodyJson = PandStringUtils.getJsonObj(parameters);

            //返回示例：{"data":{"printId":"927349035331792897","queueSize":2},"message":"OK","code":0}
            res = juheHttp(printUrl, bodyJson);

            log.info("聚合呗-打印小票  res:{}", res);

        } catch (Exception e) {
            log.error("打印小票异常" + e.getMessage(), e);
        }
        return res;
    }

    /**
     * 请求header组装参数
     *
     * @param paramters
     * @return
     */
    private Map<String, String> getHeader(String bodyJson) {

        Map<String, String> maps = Maps.newHashMap();

        try {
            String uid = PandStringUtils.getUUID();
            Long stime = System.currentTimeMillis();

            String originContext = uid + appId + stime + appSecret;

            if (PandStringUtils.isNotBlank(bodyJson)) {
                originContext += bodyJson;
            }

            String sign = DigestUtils.md5Hex(originContext);

            maps.put("Content-Type", "application/json;charset=UTF-8");
            maps.put("appid", appId);
            maps.put("uid", uid);
            maps.put("stime", String.valueOf(stime));
            maps.put("sign", sign);

            log.info("聚合呗header信息：{}", PandStringUtils.getJsonObj(maps));

        } catch (Exception e) {
            log.error("获取聚合呗HEADER异常" + e.getMessage(), e);
        }
        return maps;
    }

    /**
     * http 执行获取数据
     *
     * @param url
     * @param body 请求体
     */
    public JSONObject juheHttp(String url, String body) {
        JSONObject result = new JSONObject();
        Map<String, String> headers = getHeader(body);
        List<JSONObject> jsonObjects = RestTemplateUtils.executeHttp(url, ApiMethodTypeEnum.POST.getCodeDesc(), headers, body, null);
        if (CollectionUtils.isNotEmpty(jsonObjects)) {
            result = jsonObjects.get(0);
        }
        return result;
    }

}
