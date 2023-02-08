package org.dows.printer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dows.framework.api.Response;
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

}
