/**
 * 文件名:FieldSpec.java
 * 日期：2010-5-17
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.interfaces;

import codeclip.my.daq.core.purge.Verifier;

/**
 * 定义字段规范
 * <p>字段的规范要求用验证器表达,
 * <p>该验证器也用来校验数据内容是否符合字段规范
 */
public class FieldSpec {
    /**字段名*/
    private String name="";
    /**字段描述*/
    private String desc="";
    /**验证器*/
    private Verifier vf=null;
    
    public String getDesc() {
        return desc;
    }
    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Verifier getVerifier() {
        return vf;
    }
    public void setVerifier(Verifier vf) {
        this.vf = vf;
    }
}
