package com.centerm.fud_demo.utils;

import com.centerm.fud_demo.listener.FileListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * 文件夹监听工具类
 * @author Sheva
 * @version 1.0
 * @date 2020/2/15 上午11:01
 */
@Component
@Slf4j
public class FileListenerMonitorUtils {
    @Resource
    private FileListener fileListener;

    /**
     * 实现文件夹监听
     * @param directory 文件目录
     * @param intervalSeconds 当前时间
     * @return
     */
    public FileAlterationMonitor getMonitor(File directory, Long intervalSeconds){
        long interval = TimeUnit.SECONDS.toMillis(intervalSeconds);
        IOFileFilter directories = FileFilterUtils.and(FileFilterUtils.directoryFileFilter());
        IOFileFilter files = FileFilterUtils.and(FileFilterUtils.fileFileFilter());
        IOFileFilter filter = FileFilterUtils.or(directories, files);
        FileAlterationObserver observer = new FileAlterationObserver(directory, filter);
        observer.addListener(fileListener);
        FileAlterationMonitor monitor = new FileAlterationMonitor(interval, observer);
        return monitor;
    }
}
