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
public class PrintMappingBiz {

    private static final Logger logger = LoggerFactory.getLogger(PrintMappingBiz.class);

    /**
     * 接口映射
     *
     * @param appId 接口id
     */
    public Response diduidu(String appId) {
        try {

        } catch (Exception e) {

        }
        return Response.ok();
    }

}
