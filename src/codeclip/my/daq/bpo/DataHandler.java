/**
 * 文件名:DataHandler.java
 * 日期：2010-5-18
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.bpo;

import java.util.List;

import codeclip.my.daq.core.datatrans.Transformer;
import codeclip.my.daq.core.interfaces.DataItem;
import codeclip.my.daq.core.interfaces.DataRecord;
import codeclip.my.daq.core.interfaces.FieldSpec;
import codeclip.my.daq.core.interfaces.FtpData;
import codeclip.my.daq.core.interfaces.FtpDataSpec;
import codeclip.my.daq.util.DaqParser;

/**
 * 为数据的集成处理提供统一的接口
 */
public class DataHandler {
    /**
     * 根据spec指定的接口协议对data数据进行处理
     * <p>
     * 所进行的处理过程包括清洗、转换
     */
    public DaqResult handle(FtpData data, FtpDataSpec spec) {
        DaqResult result = new DaqResult();
        boolean flag = false;//标记成功huo失败

        List<DataRecord> rs = data.getRecords();
        List<FieldSpec> fs = spec.getFields();
        int n = fs.size();

        for (int i = 0,num=0,count=rs.size(); num< count; num++) {//每条数据记录
            flag = true;
            DataRecord dr = rs.get(i);
            List<DataItem> items = dr.getItems();
            if (items.size() != n) {// 数据项个数不匹配
                result.failRecord(dr);
                flag = false;
                continue;
            }
            for (int j = 0; j < items.size(); j++) {//数据记录的每个数据项
                DataItem di = items.get(j);
                FieldSpec field = fs.get(j);
                if (!handle(spec.getDataName(), di, field)) {// 清洗、转换失败
                    result.failItem(di, dr.getIndex());
                    flag = false;
                    continue;
                }
            }

            //累加成功或失败量
            if (flag){
                result.succInc();
                i++;//下一条数据记录
            }
            else{
                result.failInc();
                rs.remove(i);//删除错误数据记录
            }
        }

        //为处理结果设置有关属性：文件名、数据名、提供商、数据日期
        result.setFileName(data.getFileName());
        result.setDataName(spec.getDataName());
        result.setProviderName(data.getProviderName());
        result.setDataTime(data.getDataTime());
        
        return result;
    }

    /** 校验数据项 */
    private boolean handle(String dataName, DataItem di, FieldSpec field) {
        String val = di.getContent();
        boolean f = field.getVerifier().verify(val);// 清洗
        if (!f)
            return false;

        // 代码转换
        int dataIndex = di.getIndex();
        Transformer trans = DaqParser.getCodeTransfer(dataName, dataIndex);
        if (trans != null)
            di.setContent(trans.transfer(di.getContent()));

        // 度量转换
        trans = DaqParser.getMetricsTransfer(dataName, dataIndex);
        if (trans != null)
            di.setContent(trans.transfer(di.getContent()));

        // 字段转换
        trans = DaqParser.getFieldTransfer(dataName, dataIndex);
        if (trans != null)
            di.setContent(trans.transfer(di.getContent()));

        return true;
    }
}
