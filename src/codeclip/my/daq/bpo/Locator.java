/**
 * 文件名:Locator.java
 * 日期：2010-5-19
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.bpo;

import java.util.Calendar;

import codeclip.my.daq.core.interfaces.FtpData;
import codeclip.my.daq.core.interfaces.FtpDataSpec;
import codeclip.my.daq.dao.DataReader;
import codeclip.my.daq.util.DaqParser;
import codeclip.my.daq.util.Tools;

/**
 * 定位器 找到特定的ftp日志数据和接口协议
 */
public class Locator {
    /** 数据名称 */
    private String dataName = "";

    /** 厂商名称 */
    private String providerName = "";

    /** 数据日期 */
    private Calendar dataTime;

    /** 返回日志数据 */
    public FtpData getData() {
        DataReader dr = new DataReader();
        String fileName = logFileName();
        FtpData rt = dr.readFull(fileName);

        rt.setFileName(fileName);
        rt.setProviderName(providerName);
        rt.setDataTime((Calendar) dataTime.clone());

        return rt;
    }

    /** 返回接口协议 */
    public FtpDataSpec getSpec() {
        String fileName = specFileName();
        return DaqParser.parseSpec(fileName);
    }

    /** 返回包含ftp日志数据的文件名 */
    protected String logFileName() {
        String rq = Tools.formatDate(dataTime);
        return providerName + "." + dataName + "." + rq + ".log";
    }

    /** 返回配置接口协议的文件名称 */
    protected String specFileName() {
        String pre = "com/sinovatech/unicom/ecs/daq/";
        return pre + dataName + ".spec.properties";
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        if (dataName == null || dataName.length() <= 0)
            return;
        this.dataName = dataName;
    }

    public Calendar getDataTime() {
        return dataTime;
    }

    public void setDataTime(Calendar dataTime) {
        if (dataTime == null) {
            this.dataTime = Calendar.getInstance();
            this.dataTime.add(Calendar.DATE, -1);
            return;
        }

        this.dataTime = (Calendar) dataTime.clone();
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        if (providerName == null || providerName.length() <= 0)
            return;
        this.providerName = providerName;
    }

}
