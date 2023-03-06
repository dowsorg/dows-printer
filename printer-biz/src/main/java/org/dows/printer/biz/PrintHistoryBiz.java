package org.dows.printer.biz;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
import org.dows.printer.entity.PrintHistoryEntity;
import org.dows.printer.service.PrintHistoryService;
import org.dows.printer.utils.third.print.JuhePrintUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class PrintHistoryBiz {

    private final PrintHistoryService printHistoryService;
    private final JuhePrintUtils juhePrintUtils;

    /**
     * 查看订单打印历史根据订单号
     *
     * @param orderNo 订单号
     * @return
     */
    public Response getHistoryByOrderNo(@RequestParam String orderNo) {
        List<PrintHistoryEntity> list = printHistoryService.lambdaQuery().eq(PrintHistoryEntity::getOrderNo, orderNo).list();
        list.forEach(ph -> {
            JSONObject res = juhePrintUtils.getPrintStatus(ph.getPrinterNo(), ph.getPrintId());
            ph.setStateCode(res.getJSONObject("data").getString("status"));
        });
        return Response.ok();
    }


}
