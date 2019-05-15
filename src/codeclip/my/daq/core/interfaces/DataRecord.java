/**
 * 文件名:DataRecord.java
 * 日期：2010-5-17
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示从ftp日志文件读取的数据记录
 * <p>
 * 一条数据记录通常由若干数据单元(DataItem)组成
 */
public class DataRecord {
    /** 数据记录的行索引 */
    private int index;

    /** 数据记录包含的数据单元 */
    private List<DataItem> items = new ArrayList<DataItem>();

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<DataItem> getItems() {
        return items;
    }

    public void setItems(List<DataItem> items) {
        this.items = items;
    }
}
