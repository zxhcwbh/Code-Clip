/**
 * 文件名:DaqResult.java
 * 日期：2010-5-20
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.bpo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import codeclip.my.daq.core.interfaces.DataItem;
import codeclip.my.daq.core.interfaces.DataRecord;

/**
 * 用于收集数据处理的结果
 */
public class DaqResult {
    /** 厂商名称 */
    private String providerName = "";

    /** 数据日期 */
    private Calendar dataTime;

    /** 文件名称 */
    private String fileName = "";

    /** 数据名称 */
    private String dataName = "";

    private int succNum = 0;

    private int failNum = 0;

    private List<FailRecord> fails = new ArrayList<FailRecord>();

    /** 失败量 */
    public int getFailNum() {
        return failNum;
    }

    /** 失败记录列表 */
    public List<FailRecord> getFails() {
        return fails;
    }

    /** 成功量 */
    public int getSuccNum() {
        return succNum;
    }

    /** 处理数据项失败 */
    public void failItem(DataItem di, int row) {
        FailRecord fr = new FailRecord(di, row);
        fails.add(fr);
    }

    /** 处理数据记录数据项个数不符的失败 */
    public void failRecord(DataRecord dr) {
        failInc();
        FailRecord fr = new FailRecord(dr);
        fails.add(fr);
    }

    public void succInc() {
        succNum++;
    }

    public void failInc() {
        failNum++;
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public Calendar getDataTime() {
        return dataTime;
    }

    public void setDataTime(Calendar dataTime) {
        this.dataTime = (Calendar) dataTime.clone();
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

}
