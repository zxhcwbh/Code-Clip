/**
 * 文件名:DataItem.java
 * 日期：2010-5-17
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.interfaces;

/**
 * 表示从ftp日志文件读取的数据单元.
 * <p>
 */
public class DataItem {
    /** 数据单元的列索引 */
    private int index;

    /** 数据单元的名称 */
    private String name = "";

    /** 数据单元的内容 */
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
