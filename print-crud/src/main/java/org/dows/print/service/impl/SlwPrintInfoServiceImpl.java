package org.dows.print.service.impl;

import org.dows.framework.crud.mybatis.MybatisCrudServiceImpl;
import org.dows.print.entity.SlwPrintInfo;
import org.dows.print.mapper.SlwPrintInfoMapper;
import org.dows.print.service.SlwPrintInfoService;
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

