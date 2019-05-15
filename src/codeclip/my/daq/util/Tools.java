/**
 * 文件名:Tools.java
 * 日期：2010-5-21
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.util;

import java.util.Calendar;

/**
 * 提供一些辅助方法
 */
public class Tools {

    /** 格式化日期，格式为yyyy-mm-dd */
    public static String formatDate(Calendar cal) {
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
    
        StringBuilder strBuf = new StringBuilder();
        strBuf.append(year);
        strBuf.append("-");
        if (month < 10)
            strBuf.append("0");
        strBuf.append(month);
        strBuf.append("-");
        if (day < 10)
            strBuf.append("0");
        strBuf.append(day);
    
        return strBuf.toString();
    }

}
