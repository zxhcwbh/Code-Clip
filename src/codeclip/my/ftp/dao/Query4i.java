/**
 * 文件名:Query4i.java
 * 日期：2010-5-20
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.ftp.dao;

import java.util.Calendar;

import codeclip.my.ftp.util.Tools;

/**
 * 为4i生成sql语句
 */
public class Query4i {
    // 查询用到的表
    public static final String table_1 = "EFB.EFB_USER tt";

    public static final String table_2 = "EFB_ACCOUNT_IN_FLOOD t1, EFB_ACCOUNT_DETAIL t2";

    public static final String table_3 = "EFB_ACCOUNT_OUT_FLOOD t1,EFB_ACCOUNT_OUT_DETAIL t2";

    public static final String table_4 = "EFB_ACCOUNT_OUT_FLOOD t1,EFB_ACCOUNT_OUT_DETAIL t2";

    /** 生成查询语句 注册 */
    public static String makeSql1(Calendar cal, int index) {
        StringBuffer rt = new StringBuffer();
        String[] dates = makeDateFormat(cal, index);
        rt.append("select 'ma-register',nvl(count(1),0) from　" + table_1
                + " where　tt.state = '00'");
        rt.append(" and tt.REG_TIME >= to_date('" + dates[0]
                + "', 'yyyy-mm-dd')");
        rt.append(" and tt.REG_TIME < to_date('" + dates[1]
                + "', 'yyyy-mm-dd')");
        return rt.toString();
    }

    /** 生成查询语句 充值 */
    public static String makeSql2(Calendar cal, int index) {
        StringBuffer rt = new StringBuffer();
        String[] dates = makeDateFormat(cal, index);

        rt.append("select 'ma-recharge',nvl(sum(t1.BALANCE/1000),0)");
        rt.append(" from " + table_2);
        rt.append(" where t1.account_detail_id = t2.id");
        rt.append(" and (t1.change_type = '01' or t1.change_type = '03' or");
        rt.append("      t1.change_type = '04')");
        rt.append(" and (t2.account_type_id ='2' or t2.account_type_id ='3')");
        rt.append(" and t1.recharge_time>=");
        rt.append("     to_date('" + dates[0] + "', 'yyyy-mm-dd')");
        rt.append(" and t1.recharge_time<");
        rt.append("     to_date('" + dates[1] + "', 'yyyy-mm-dd')");
        return rt.toString();
    }

    /** 生成查询语句 赠款 */
    public static String makeSql3(Calendar cal, int index) {
        StringBuffer rt = new StringBuffer();
        String[] dates = makeDateFormat(cal, index);

        rt.append("select 'ma-largess',nvl(-sum( t1.BALANCE / 1000),0)");
        rt.append(" from " + table_3 + " where t1.id = t2.out_flood_id");
        rt.append(" and(t1.change_type='02'  or t1.change_type='03'");
        rt.append(" or t1.change_type='04')");
        rt.append(" and t1.out_order_time>=");
        rt.append("     to_date('" + dates[0] + "', 'yyyy-mm-dd')");
        rt.append(" and t1.out_order_time<");
        rt.append("     to_date('" + dates[1] + "', 'yyyy-mm-dd')");
        rt.append(" and t2.account_type_id='1'");
        return rt.toString();
    }

    /** 生成查询语句 现金 */
    public static String makeSql4(Calendar cal, int index) {
        StringBuffer rt = new StringBuffer();
        String[] dates = makeDateFormat(cal, index);

        rt.append("select 'ma-cash',nvl(-sum( t1.BALANCE / 1000),0)");
        rt.append("from " + table_4 + " where t1.id = t2.out_flood_id");
        rt.append("  and(t1.change_type='02'  or t1.change_type='03'");
        rt.append("  or t1.change_type='04')");
        rt.append("  and t1.out_order_time>=");
        rt.append("      to_date('" + dates[0] + "', 'yyyy-mm-dd')");
        rt.append("  and t1.out_order_time<");
        rt.append("      to_date('" + dates[1] + "', 'yyyy-mm-dd')");
        rt.append(" and t2.account_type_id='2'");
        return rt.toString();
    }

    /** 生成2个日期格式串 */
    private static String[] makeDateFormat(Calendar cal, int index) {
        String[] rt = new String[2];
        Calendar mycal = (Calendar) cal.clone();
        mycal.add(Calendar.DATE,1);
        rt[1] = Tools.formatDate(mycal);

        switch (index) {
        case 0:
        // 本日
        case 1:
            mycal.add(Calendar.DATE, -1);
            rt[0] = Tools.formatDate(mycal);
            // 昨日
            break;
        case 2:
        // 本月
        case 3:
            mycal.set(Calendar.DATE, 1);
            rt[0] = Tools.formatDate(mycal);

            // 上月
            break;
        case 4:
        // 近三月
        case 5:
            // 上三月
            break;
        case 6:
            mycal.set(Calendar.MONTH, 0);
            mycal.set(Calendar.DATE, 1);
            rt[0] = Tools.formatDate(mycal);
            // 本年
            break;
        default:
            mycal.add(Calendar.DATE, -1);
            rt[1] = Tools.formatDate(mycal);
            break;
        }

        return rt;
    }
}
