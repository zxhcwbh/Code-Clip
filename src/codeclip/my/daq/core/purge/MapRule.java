/**
 * 文件名:MapRule.java
 * 日期：2010-5-17
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.purge;

import java.util.HashMap;
import java.util.Map;

/**实现基于代码映射表的校验规则*/
public class MapRule implements Rule {
    /**代码映射表*/
    private Map<String,String> codeTable=new HashMap<String,String>();
    
    /** 
     * @see codeclip.my.daq.purge.Rule#verify(java.lang.String)
     */
    public boolean verify(String content) {
        String value=codeTable.get(content);
        return value!=null;
    }

    public Map<String, String> getCodeTable() {
        return codeTable;
    }

    public void setCodeTable(Map<String, String> codeTable) {
        this.codeTable = codeTable;
    }

}
