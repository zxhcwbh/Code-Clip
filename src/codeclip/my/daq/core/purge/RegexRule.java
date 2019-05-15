/**
 * 文件名:RegexRule.java
 * 日期：2010-5-17
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.purge;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 实现基于正则表达式的校验规则
 */
public class RegexRule implements Rule {
    /** 正则表达式的模式字符串 */
    private String regex = "";

    /**
     * @see codeclip.my.daq.purge.Rule#verify(java.lang.String)
     */
    public boolean verify(String content) {
        Pattern p=Pattern.compile(regex);
        Matcher m=p.matcher(content);
        return m.matches();
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

}
