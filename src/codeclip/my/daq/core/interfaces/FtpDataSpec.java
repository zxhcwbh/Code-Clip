/**
 * 文件名:FtpDataSpec.java
 * 日期：2010-5-17
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.interfaces;

import java.util.ArrayList;
import java.util.List;

/**
 * 表示ftp接口协议，配置数据的校验需求。
 * <p>
 * 这个类的实例对应ftp日志文件的数据记录的结构
 */
public class FtpDataSpec {
    /**数据名称,表明协议规范的是什么数据*/
    private String dataName = "";
    /**字段规范列表,一个接口协议有若干字段规范组成*/
    private List<FieldSpec> fields = new ArrayList<FieldSpec>();

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        if(dataName==null||dataName.length()<=0)
            return;
        this.dataName = dataName;
    }

    public List<FieldSpec> getFields() {
        return fields;
    }

    public void setFields(List<FieldSpec> fields) {
        this.fields = fields;
    }
}
