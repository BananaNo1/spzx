package com.leis.common.log.service;

import com.leis.model.entity.system.SysOperLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface IAsyncOperLogService {
    void saveSysOperLog(SysOperLog sysOperLog);
}


