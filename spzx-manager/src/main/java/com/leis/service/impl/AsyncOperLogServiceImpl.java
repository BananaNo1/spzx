package com.leis.service.impl;

import com.leis.common.log.service.IAsyncOperLogService;
import com.leis.mapper.SysOperLogMapper;
import com.leis.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncOperLogServiceImpl implements IAsyncOperLogService {

    @Autowired
    private SysOperLogMapper sysOperLogMapper;

    @Async
    @Override
    public void saveSysOperLog(SysOperLog sysOperLog) {
        sysOperLogMapper.insert(sysOperLog);
    }
}
