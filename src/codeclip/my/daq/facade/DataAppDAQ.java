package codeclip.my.daq.facade;
import java.util.Calendar;

import codeclip.my.daq.bpo.AppController;

/**
 * 文件名:DataAppDAQ.java
 * 日期：2010-5-19
 * @author：曾宪华
 * @version:1.0
 */


/**
 * 数据采集主类
 */
public class DataAppDAQ {

    /**
     * 入口方法
     * 
     * @param args
     */
    public static void main(String[] args) {
        String pname = "my";
        String dname = "data";
        Calendar cal=Calendar.getInstance();
        cal.set(Calendar.DATE,19);
        
        AppController app=new AppController();
        app.doAction(dname,pname,cal);
    }
}
