/**
 * 文件名:FieldTransfer.java
 * 日期：2010-5-18
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.datatrans;

/**
 * 字段转换器
 */
public class FieldTransfer implements Transformer {
    /**字段的内容属性,表明什么样的字段内容其字段名需要转换*/
    private String attr="";
    private String src="";
    private String dest="";
    /** 
     * @see codeclip.my.daq.datatrans.Transformer#transfer(java.lang.String)
     */
    public String transfer(String src) {
        return null;
    }

    public String getAttr() {
        return attr;
    }

    public void setAttr(String attr) {
        this.attr = attr;
    }

    public String getDest() {
        return dest;
    }

    public void setDest(String dest) {
        this.dest = dest;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

}
