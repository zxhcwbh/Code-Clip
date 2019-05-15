/**
 * 文件名:DataReader.java
 * 日期：2010-5-18
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import codeclip.my.daq.core.interfaces.DataItem;
import codeclip.my.daq.core.interfaces.DataRecord;
import codeclip.my.daq.core.interfaces.FtpData;

/**
 * 读取ftp日志文件
 */
public class DataReader {
    /**读取全部数据*/
    public FtpData readFull(String fileName) {
        InputStream is=ClassLoader.getSystemResourceAsStream(fileName);
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        
        FtpData data = initData();
        List<DataRecord> rs = data.getRecords();

        try {
            String line = br.readLine();
            int count = 0;
            while (line != null) {
                count++;
                rs.add(parseAsRecord(line, count));

                line = br.readLine();
            }
            
            is.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return data; 
    }

    /**初始化日志数据*/
    protected FtpData initData() {
        FtpData rt = new FtpData();
        return rt;
    }

    /** 解析ftp日志记录 */
    protected DataRecord parseAsRecord(String line, int index) {
        DataRecord dr = new DataRecord();
        dr.setIndex(index);
        List<DataItem> items = dr.getItems();

        String[] cols = line.split(";");
        for (int i = 0; i < cols.length; i++) {
            DataItem di = new DataItem();
            di.setIndex(i);
            di.setContent(cols[i]);
            items.add(di);
        }

        return dr;
    }
}
