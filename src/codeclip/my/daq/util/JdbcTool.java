/**
 * 文件名:JdbcTool.java
 * 日期：2010-5-21
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 为获取jdbc连接提交接口
 */
public class JdbcTool {
    private static final Properties props = new Properties();

    private static final String jdbc_file = "jdbc.properties";
    static {
        InputStream is = ClassLoader.getSystemResourceAsStream(jdbc_file);
        try {
            props.load(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection getConnection(String dataSource)
            throws ClassNotFoundException, SQLException {
        String url = "";
        String username = "";
        String password = "";

        if ("ltzbyyt".equals(dataSource)) {
            url = props.getProperty("ltzbyyt.url");
            username = props.getProperty("ltzbyyt.username");
            password = props.getProperty("ltzbyyt.password");
        } else if ("ltyyt_st".equals(dataSource)) {
            url = props.getProperty("ltyyt_st.url");
            username = props.getProperty("ltyyt_st.username");
            password = props.getProperty("ltyyt_st.password");
        } else if ("clubt".equals(dataSource)) {
            url = props.getProperty("clubt.url");
            username = props.getProperty("clubt.username");
            password = props.getProperty("clubt.password");
        } else if ("smsehall".equals(dataSource)) {
            url = props.getProperty("smsehall.url");
            username = props.getProperty("smsehall.username");
            password = props.getProperty("smsehall.password");
        } else if ("efb".equals(dataSource)) {
            url = props.getProperty("efb.url");
            username = props.getProperty("efb.username");
            password = props.getProperty("efb.password");
        } else {
            return null;
        }

        String driver = props.getProperty("jdbc.driverClassName");
        Class.forName(driver);
        return DriverManager.getConnection(url, username, password);
    }
}
