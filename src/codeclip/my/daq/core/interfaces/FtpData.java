/**
 * 文件名:FtpData.java
 * 日期：2010-5-17
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.interfaces;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 缓存从ftp日志文件读取的数据记录
 */
public class FtpData {
    /** 文件名称 */
    private String fileName="";

    /** 厂商名称 */
    private String providerName="";

    /** 数据日期 */
    private Calendar dataTime;

    /** 记录集 */
    private List<DataRecord> rs = new ArrayList<DataRecord>();

    public Calendar getDataTime() {
        return dataTime;
    }

    public void setDataTime(Calendar dataTime) {
        this.dataTime = dataTime;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public List<DataRecord> getRecords() {
        return rs;
    }

    public void setRecords(List<DataRecord> rs) {
        this.rs = rs;
    }
}
