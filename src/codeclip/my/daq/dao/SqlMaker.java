/**
 * 文件名:SqlMaker.java
 * 日期：2010-5-20
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 批量生成生成插入数据的sql语句
 */
public class SqlMaker {
    private static final Logger log=Logger.getLogger("SqlMaker");
    private Statement st=null;
    private Map<String, String[]> tableSql = new HashMap<String, String[]>();
    
    public SqlMaker(Statement ps){
        this.st=ps;
    }

    /**添加数据项*/
    public void addItem(String[] fields, String content) {
        for (int i = 0; i < fields.length; i++) {
            String[] tf = fields[i].split(".");
            String[] fv = tableSql.get(tf[0]);
            if (fv == null) {
                fv = new String[] { "", "" };
                tableSql.put(tf[0], fv);
            }

            StringBuffer ff = new StringBuffer(fv[0]);
            StringBuffer vv = new StringBuffer(fv[1]);
            if (ff.length() > 0) {
                ff.append(",");
                vv.append(",");
            }
            fv[0] = ff.toString();
            fv[1] = vv.toString();
        }
    }

    /**生成批处理*/
    public void makeBatch() {
        Iterator<String> it = tableSql.keySet().iterator();
        while (it.hasNext()) {
            String tname = it.next();
            String[] subs = tableSql.get(tname);
            try {
                st.addBatch("insert into " + tname + "(ID," + subs[0]
                        + ") values(ods_seq.nextval," + subs[1] + ")");
            } catch (SQLException e) {
                log.info(e.getMessage());
            }
        }
        
        reset();
    }

    private void reset() {
        tableSql.clear();
    }
}
