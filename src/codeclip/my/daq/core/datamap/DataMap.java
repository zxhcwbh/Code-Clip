/**
 * 文件名:DataMap.java
 * 日期：2010-5-17
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.datamap;

import java.util.HashMap;
import java.util.Map;

/**
 * 定义数据映射
 * <p>
 * 这个类的每个实例表明某个ftp日志文件的某个数据跟某个表的某个字段映射
 */
public class DataMap {
    /** 数据映射表 */
    private Map<String, String> tableDM = new HashMap<String, String>();

    public Map<String, String> getTableDM() {
        return tableDM;
    }

    public void setTableDM(Map<String, String> tableDM) {
        this.tableDM = tableDM;
    }
    
    /**返回映射的字段*/
    public String[] getFields(String dataName,int index){
        String key=dataName+"."+index;
        String value=tableDM.get(key);
        String[] rt=value.split(",");
        return rt;
    }
}
