/**
 * 文件名:SmsQuery.java
 * 日期：2010-5-12
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.ftp.dao;

import java.util.Calendar;

import codeclip.my.ftp.util.Tools;

/** 短信用户数查询 */
public class SmsQuery {
	// 查询短信用户数用到的表
    public static final String table_date = "t_sms_usercount_date";

    public static final String table_month = "t_sms_usercount_month";

    public static final String table_3month = "t_sms_usercount_3month";

    public static final String table_year = "t_sms_usercount_year";

    /** 生成查询语句 */
    public static String makeSql(String bm, Calendar cal) {
        String rq = Tools.formatDate(cal);
        StringBuilder result = new StringBuilder();

        result.append("select 'sms',nvl(sum(used_count),0)");
        result.append(" from " + bm);
        result.append(" where area_type = 'province' and");
        result.append(" query_date = to_date('" + rq + "', 'yyyy-mm-dd')");
        return result.toString();
    }
}
