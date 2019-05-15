/**
 * 文件名:JdbcMgr.java
 * 日期：2010-5-5
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.ftp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import codeclip.my.ftp.log.LogTool;
import codeclip.my.ftp.util.Tools;

import com.sinovatech.datasource.DataSource;

/**
 * 提供jdbc数据访问功能
 */
public class JdbcMgr {
   
	/**日志*/
    private static final Logger log = LogTool.getLog("ftpdata");
	// 查询数据用到的表名
    public static final String table_date = "t_sus_date";

    public static final String table_month = "t_sus_this_month";

    public static final String table_3month = "t_sus_this_3month";

    public static final String table_year = "t_sus_this_year";

    /** jdbc连接 */
    private static Connection conn = null;

    private static Connection conn1 = null;

    private static Connection conn2 = null;
    static {
        String resource = "ltyyt_st";
        String resource2 = "efb";
        DataSource ds = new DataSource();
        try {
            conn1 = ds.getDataSource(resource);
            conn2 = ds.getDataSource(resource2);
        } catch (ClassNotFoundException e) {
            log.info(e.getMessage());
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    /** 通过jdbc获取数据 */
    public Map<String, String> getData1(String tableName, Calendar cal) {
        conn=conn1;
        return getData(tableName,cal);
    }
    /** 通过jdbc获取数据 */
    public Map<String, String> getData2(String tableName, Calendar cal) {
        conn=conn2;
        return getData(tableName,cal);
    }
    /** 执行sql获取数据 */
    public Map<String, String> getData1(String sql) {
        conn=conn1;
        return getData(sql);
    }
    /** 执行sql获取数据 */
    public Map<String, String> getData2(String sql) {
        conn=conn2;
        return getData(sql);
    }

    
    /** 通过jdbc获取数据 */
    public Map<String, String> getData(String tableName, Calendar cal) {
        /** 查询数据的sql语句 */
        String sql = makeSql(tableName, Tools.formatDate(cal));
        return getData(sql);
    }

    /** 执行sql获取数据 */
    public Map<String, String> getData(String sql) {
        log.info(sql);
        
        Map<String, String> result = new HashMap<String, String>();
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                String name = rs.getString(1);
                String value = rs.getString(2);
                result.put(name, value);
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            log.info(e.getMessage());
        }

        return result;
    }

    /**
     * 生成sql语句
     */
    protected String makeSql(String bm, String rq) {
        StringBuilder strBuf = new StringBuilder(16);

        strBuf.append("select t.application, sum(t.cou)");
        strBuf.append(" from " + bm + " t");
        strBuf.append(" where t.area_type = 'province'");
        strBuf.append(" and t.query_date = to_date('" + rq + "','yyyy-mm-dd')");
        strBuf.append(" group by t.application");
        return strBuf.toString();
    }

    /** 释放jdbc相关资源 */
    public void destory() {
        conn = null;
        if (conn1 == null && conn2 == null)
            return;
        try {
            if (conn1 != null)
                conn1.close();
            if (conn2 != null)
                conn2.close();
            conn1 = null;
            conn2 = null;
        } catch (SQLException e) {
            log.info(e.getMessage());
        }

    }
}
