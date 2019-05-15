/**
 * 文件名:Tools.java
 * 日期：2010-5-12
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.ftp.util;

import java.util.Calendar;

/**提供一些辅助方法*/
public class Tools {
    /**格式符*/
    private static final String format_flag="%d";

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
    
    /**
     * 处理带格式符(%d)的文件名,生成并返回新的文件名
     */
    public static String makeFileName(String fileName){
        return makeFileName(fileName,Calendar.getInstance());
    }
    
    /**
     * 处理带格式符(%d)的文件名,生成并返回新的文件名
     */
    public static String makeFileName(String fileName,Calendar cal){
        String rt=fileName;
        int pos=rt.indexOf(format_flag);
        if(pos<0)
            return rt;
        
        String rq=formatDate(cal).replaceAll("-","");
        rt=rt.replaceAll(format_flag,rq);
        return rt;
    }
}
