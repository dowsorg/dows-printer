package org.dows.printer.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.printer.entity.SlwPrintInfo;
import org.dows.printer.mapper.SlwPrintInfoMapper;
import org.dows.printer.service.SlwPrintInfoService;
import org.springframework.stereotype.Service;


/**
 * 门店-业态字典(StoreEco)表服务实现类
 *
 * @author lait.zhang
 * @since 2022-10-31 13:56:49
 */
@Service("slwPrintInfoService")
public class SlwPrintInfoServiceImpl extends MybatisCrudServiceImpl<SlwPrintInfoMapper, SlwPrintInfo> implements SlwPrintInfoService {

}

