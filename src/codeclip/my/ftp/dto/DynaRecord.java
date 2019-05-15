/**
 * 文件名:DynaRecord.java
 * 日期：2010-5-5
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.ftp.dto;

import java.util.ArrayList;
import java.util.List;

/**动态记录，数据项个数可以动态变化的记录*/
public class DynaRecord {
    /**记录名称*/
    private String name="";
    private List<String> items=new ArrayList<String>();
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<String> getItems() {
        return items;
    }
    
    /**格式化成字符串并返回*/
    public String format(){
        StringBuffer result=new StringBuffer();
        result.append(getName());
        
        for(int i=0;i<items.size();i++){
            result.append(",");
            result.append(items.get(i));
        }
        
        return result.toString();
    }
}
