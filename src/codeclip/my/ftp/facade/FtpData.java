/**
 * 文件名:FtpData.java
 * 日期：2010-5-5
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.ftp.facade;

import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import codeclip.my.ftp.bpo.DataMgr;
import codeclip.my.ftp.bpo.RecordHandler;
import codeclip.my.ftp.dto.DynaRecord;
import codeclip.my.ftp.log.LogTool;
import codeclip.my.ftp.util.Tools;

import com.sinovatech.common.Configuration;

/** 主类 */
public class FtpData {
    /**日志*/
    private static final Logger log = LogTool.getLog("ftpdata");
    /**数据文件配置项*/
    private static final String data_file_key="ftp.data.file";
    
    /**
     * 入口方法
     * 
     * @param args
     */
    public static void main(String[] args) {
        String[] rt=parseArgs(args);
        Calendar cal=parseDate(rt[0]);
        String fileName=parseFile(rt[1],cal);
        
        DataMgr mgr=new DataMgr();
       
        long start = System.currentTimeMillis();
        List<DynaRecord> data = mgr.getData(cal);
        long end = System.currentTimeMillis();
        log.info("data date:"+Tools.formatDate(cal)+";execute times:" + (end - start));

        mgr.destory();

        RecordHandler handler = new RecordHandler();
        handler.setFileName(fileName);//设置数据输出文件
        handler.outRecords(data);
    }
    
    /**解析文件*/
    private static String parseFile(String file,Calendar cal){
        String fname=file;
        if(fname.equals(""))
            fname=Configuration.getProperty("system",data_file_key);
        
        fname=Tools.makeFileName(fname,cal);
        return fname;
    }
    
    /**解析日期*/
    private static Calendar parseDate(String date){
        Calendar cal=Calendar.getInstance();
        String[] ymd=date.split("-");
        if(ymd.length<3){
            cal.add(Calendar.DATE, -1);
            return cal;
        }
        
        int y=Integer.parseInt(ymd[0]);
        if(y<0||y>2100)
            y=1970;
        int m=Integer.parseInt(ymd[1])-1;
        int d=Integer.parseInt(ymd[2]);
        
        cal.set(y,m,d);
        return cal;
    }
    
    /**解析参数*/
    private static String[] parseArgs(String[] args){
        String[] rt={"",""};
        int n=args.length;
        if(n<2)
            return rt;
        
        String name=args[0];
        if(name.equals("-d"))
            rt[0]=args[1];
        else if(name.equals("-f"))
            rt[1]=args[1];
        
        if(n<4)
            return rt;
        
        name=args[2];
        if(name.equals("-d"))
            rt[0]=args[3];
        else if(name.equals("-f"))
            rt[1]=args[3];
        
        return rt;
    }
}
