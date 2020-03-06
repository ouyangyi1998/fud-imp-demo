package com.centerm.fud_demo.config;

import com.centerm.fud_demo.utils.FileListenerMonitorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.io.File;

/**
 * @author Sheva
 * @version 1.0
 * @date 2020/2/15 上午10:59
 */

@Component
@Slf4j
public class FileMonitorRegisterConfig {

    @Value("${filePath}")
    private String filePath;
    @Resource
    private FileListenerMonitorUtils fileListenerMonitorUtils;

    @PostConstruct
    private void register(){
        File directory = new File(filePath + "real");
        if (!directory.exists()){
            directory.mkdirs();
        }
        Long intervalSeconds = 5L;
        try{
            FileAlterationMonitor monitor = fileListenerMonitorUtils.getMonitor(directory, intervalSeconds);
            monitor.start();
        }catch (Exception e){
            log.error("Monitor error: " + e.getMessage());
        }
    }
}
