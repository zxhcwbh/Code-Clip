/**
 * 文件名:Rule.java
 * 日期：2010-5-17
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.purge;

/**
 * 为校验定义接口
 */
public interface Rule {
    /**校验数据内容是否合法*/
    public boolean verify(String content);
}
