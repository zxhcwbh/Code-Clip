/**
 * 文件名:Verifier.java
 * 日期：2010-5-17
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.purge;

/**
 * 验证器 
 * <p>为验证某种数据合法性定义接口
 */
public class Verifier {
    /**数据名称*/
    private String name = "";
    /**数据描述*/
    private String desc = "";
    /**校验规则*/
    private Rule myRule;
    
    /**验证数据内容是否符合指定的校验规则*/
    public boolean verify(String content){
        return myRule.verify(content);
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Rule getRule() {
        return myRule;
    }

    public void setRule(Rule myRule) {
        this.myRule = myRule;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
