/**
 * 文件名:RecordHandler.java
 * 日期：2010-5-5
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.ftp.bpo;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import codeclip.my.ftp.dto.DynaRecord;

/** 处理记录,将数据记录输出到指定的文件 */
public class RecordHandler {
    /**数据文件名*/
    private String fileName = "data.log";
    
    /** 输出记录 */
    public void outRecords(List<DynaRecord> data) {
        if(data==null)
            return;
        
        try {
            FileOutputStream file = new FileOutputStream(fileName);
            for(int i=0;i<data.size();i++){
                DynaRecord dr=data.get(i);
                String str=dr.format()+"\n";
                file.write(str.getBytes());
            }
         
            file.flush();
            file.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /** 设置输出记录的文件名 */
    public void setFileName(String name) {
        if (name == null || name.length() <= 0)
            return;
        fileName = name;
    }
}
