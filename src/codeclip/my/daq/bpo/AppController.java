/**
 * 文件名:AppController.java
 * 日期：2010-5-19
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.bpo;

import java.util.Calendar;

import codeclip.my.daq.core.datamap.DataMap;
import codeclip.my.daq.core.interfaces.FtpData;
import codeclip.my.daq.core.interfaces.FtpDataSpec;
import codeclip.my.daq.dao.DataStorer;
import codeclip.my.daq.util.DaqParser;

/**
 * 应用控制器 为数据采集提供统一入口
 */
public class AppController {
    /** 执行数据采集 */
    public void doAction(String dname, String pname, Calendar cal) {
        Locator finder = new Locator();
        finder.setDataName(dname);
        finder.setProviderName(pname);
        finder.setDataTime(cal);

        FtpData data = finder.getData();
        FtpDataSpec spec = finder.getSpec();

        // 处理数据
        DataHandler handler = new DataHandler();
        DaqResult result=handler.handle(data, spec);

        // 存储数据
        DataStorer storer = new DataStorer();
        storer.store(result);
        
        DataMap dm = DaqParser.getDataMap(dname);
        storer.store(spec.getDataName(),data, dm);
    }
}
