/**
 * 文件名:CodeTransfer.java
 * 日期：2010-5-18
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.datatrans;

/**
 * 编码转换器
 */
public class CodeTransfer implements Transformer {
    /** 代码名称,表示做编码转换的是什么类型的代码 */
    private String name = "";

    /**
     * @see codeclip.my.daq.datatrans.Transformer#transfer(java.lang.String)
     */
    public String transfer(String src) {
        if(src==null||src.length()<=0)
            return src;
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
