/**
 * 文件名:DataStorer.java
 * 日期：2010-5-18
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Logger;

import codeclip.my.daq.bpo.DaqResult;
import codeclip.my.daq.bpo.FailRecord;
import codeclip.my.daq.core.datamap.DataMap;
import codeclip.my.daq.core.interfaces.DataItem;
import codeclip.my.daq.core.interfaces.DataRecord;
import codeclip.my.daq.core.interfaces.FtpData;
import codeclip.my.daq.util.JdbcTool;
import codeclip.my.daq.util.Tools;

/**
 * 为数据存储提供统一的接口
 */
public class DataStorer {
    private static final Logger log=Logger.getLogger("DataStorer");
    private Connection conn = null;

    private Statement st = null;

    /** 构造函数 */
    public DataStorer() {
        try {
            conn=JdbcTool.getConnection("cudw");
            st = conn.createStatement();
        } catch (SQLException e) {
            log.info(e.getMessage());
        } catch (ClassNotFoundException e) {
            log.info(e.getMessage());
        }
    }

    /** 存储数据 */
    public void store(String dataName, FtpData data, DataMap dm) {
        List<DataRecord> rs = data.getRecords();
        int n = rs.size();
        for (int i = 0; i < n; i++) {
            DataRecord dr = rs.get(i);
            makeSql(dataName, dr, dm);
        }

        if (n <= 0)
            return;

        try {
            st.executeBatch();
            st.close();
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    /**
     * 存储数据采集过程的日志监控数据
     */
    public void store(DaqResult result) {
        Statement stlog = null;
        try {
            stlog = conn.createStatement();
            makeSqlLog(result, stlog);
            stlog.executeBatch();
        } catch (SQLException e) {
            log.info(e.getMessage());
        }
    }

    /** 批量生成插入日志监控的sql语句 */
    private void makeSqlLog(DaqResult result, Statement stlog) {
        String rq = Tools.formatDate(result.getDataTime());
        String pname = result.getProviderName();
        String dname = result.getDataName();
        String fname = result.getFileName();

        int succNum = result.getSuccNum();
        int failNum = result.getFailNum();
        int count = succNum + failNum;

        //数据加载日志
        StringBuffer sql = new StringBuffer();
        sql.append("insert into T_DATA_LOG(");
        sql.append("ID,SEND_DATE,PROVIDER_ID,DATA_TYPE,FILE_NAME,");
        sql.append("SEND_COUNT,SUESS_COUNT,FAILE_COUNT)");
        sql.append(" values(ods_seq.nextval,");
        sql.append(rq + "," + pname + "," + dname + "," + fname + ",");
        sql.append(count + "," + succNum + "," + failNum + ")");

        try {
            stlog.addBatch(sql.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<FailRecord> frList = result.getFails();
        for (int i = 0; i < frList.size(); i++) {//失败记录表
            FailRecord fr = frList.get(i);
            int row=fr.getRow();
            String edetail=fr.getFailDesc();
            String edata=fr.getFailData();
            
            StringBuffer fsql = new StringBuffer();
            fsql.append("insert into T_FAILE_LOG(");
            fsql.append("ID,SEND_DATE,PROVIDER_ID,DATA_TYPE,FILE_NAME,");
            fsql.append("ERROR_ROW_NO,ERROR_DETAIL,ERROR_DATA)");
            fsql.append(" values(ods_seq.nextval,)");
            fsql.append(rq + "," + pname + "," + dname + "," + fname + ",");
            fsql.append(row + "," + edetail + "," + edata + ")");
            try {
                stlog.addBatch(fsql.toString());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /** 生成插入数据的sql语句 */
    private void makeSql(String dataName, DataRecord dr, DataMap dm) {
        List<DataItem> items = dr.getItems();
        SqlMaker sm = new SqlMaker(st);
        for (int i = 0; i < items.size(); i++) {
            DataItem di = items.get(i);
            int index = di.getIndex();
            String[] fields = dm.getFields(dataName, index);
            sm.addItem(fields, di.getContent());
        }
    }

}
