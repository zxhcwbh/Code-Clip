/**
 * 文件名:FailRecord.java
 * 日期：2010-5-20
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.bpo;

import codeclip.my.daq.core.interfaces.DataItem;
import codeclip.my.daq.core.interfaces.DataRecord;

/**
 * 记录数据处理过程中的失败
 */
public class FailRecord {
    /** 错误所在文件行 */
    private int row = 0;

    /** 错误描述 */
    private String failDesc = "";

    /** 错误数据 */
    private String failData = "";

    public FailRecord(DataItem di, int row) {
        this.row = row;
        this.failDesc = "col " + di.getIndex() + " 校验失败.";
        this.failData = di.getContent();
    }

    public FailRecord(DataRecord dr) {
        this.row = dr.getIndex();
        this.failDesc = "数据项个数不符";
    }

    public String getFailData() {
        return failData;
    }

    public String getFailDesc() {
        return failDesc;
    }

    public int getRow() {
        return row;
    }
}
