/**
 * 文件名:LogTool.java
 * 日期：2010-5-5
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.ftp.log;

import java.io.IOException;
import java.io.InputStream;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import codeclip.my.ftp.util.Tools;

import com.sinovatech.common.Configuration;

/** 为日志功能提供入口 */
public class LogTool {
    /** 日志配置文件 */
    private static final String log_conf_file = "log.properties";
    private static final String log_file_key="ftp.log.file";
    private static final String log_file="ftp%d.log";

    /** 日志管理器,读取日志配置 */
    private static final LogManager logMgr = LogManager.getLogManager();
    static {
        try {
            InputStream is = LogTool.class.getResourceAsStream("../"
                    + log_conf_file);
            logMgr.readConfiguration(is);
        } catch (Exception e) {
            //
        }
    }

    /** 文件处理器，配置日志输出的文件与格式 */
    private static FileHandler handler;
    static {
        try {
            String fileName = Configuration.getProperty("system",log_file_key);
            if(fileName.equals(""))
                fileName=log_file;
            fileName=Tools.makeFileName(fileName);
            
            handler = new FileHandler(fileName, true);
            handler.setFormatter(new SimpleFormatter());
        } catch (SecurityException e) {
            //
        } catch (IOException e) {
            //
        }
    }

    /** 返回日志器 */
    public static Logger getLog(String name) {
        Logger log = Logger.getLogger(name);
        log.addHandler(handler);
        return log;
    }
}
