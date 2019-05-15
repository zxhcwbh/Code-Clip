/**
 * 文件名:DataMgr.java
 * 日期：2010-5-5
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.ftp.bpo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import codeclip.my.ftp.dao.JdbcMgr;
import codeclip.my.ftp.dao.Query4i;
import codeclip.my.ftp.dao.SmsQuery;
import codeclip.my.ftp.dto.DynaRecord;

/**
 * 提供获取数据的接口
 */
public class DataMgr {
    /** 当日 */
    private static final Calendar refCal = Calendar.getInstance();

    /** 数据项个数 */
    private int itemCount = 7;

    /** 渠道 */
    private String[] channels = { "web", "sms", "wap", "mini", "ma-register", "ma-recharge",
            "ma-largess", "ma-cash" };

    /** 数据访问对象 */
    private JdbcMgr dbMgr = new JdbcMgr();

    /** 组装合并数据 */
    protected void mergData(List<DynaRecord> result,
            Map<String, String> valMap, int index) {

        Iterator<String> it = valMap.keySet().iterator();
        List<String> items = null;
        while (it.hasNext()) {
            String name = it.next();
            String val = valMap.get(name);
            if (name.equals("mini"))
                name = "wap";
            int pos = indexOf(name);
            if (pos < 0)
                continue;

            DynaRecord dr = result.get(pos);
            items = dr.getItems();

            if (index != 6) {
                long d1 = Long.parseLong(items.get(index));
                long d2 = Long.parseLong(val);
                val = "" + (d1 + d2);
            }

            items.set(index, val);
        }
    }

    /** 组装合并数据("注册","充值","赠款","现金") */
    protected void mergData2(List<DynaRecord> result,
            Map<String, String> valMap, int index) {

        Iterator<String> it = valMap.keySet().iterator();
        List<String> items = null;
        while (it.hasNext()) {
            String name = it.next();
            String val = valMap.get(name);
            int pos = indexOf(name);
            if (pos < 0)
                continue;

            DynaRecord dr = result.get(pos);
            items = dr.getItems();
            items.set(index, val);
        }
    }

    /**
     * 获取数据 以昨日为日期参照标准取数据
     */
    public List<DynaRecord> getData() {
        Calendar cal = (Calendar) refCal.clone();
        cal.add(Calendar.DATE, -1);
        return getData(cal);
    }

    /**
     * 获取数据
     * 
     * @param cal
     *            数据的查询日期
     */
    public List<DynaRecord> getData(Calendar cal) {
        List<DynaRecord> result = initData();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);
        int index = 0;

        // 本日、昨日
        loadData(result, cal, index);

        index++;
        cal.add(Calendar.DATE, -1);
        loadData(result, cal, index);

        // 本月、上月
        index++;
        cal.set(year, month, day);
        loadData(result, cal, index);

        index++;
        cal.add(Calendar.MONTH, -1);
        loadData(result, cal, index);

        // 近三月、上三月
        index++;
        cal.set(year, month, day);
        loadData(result, cal, index);

        index++;
        cal.add(Calendar.MONTH, -1);
        loadData(result, cal, index);

        // 本年、上年
        index++;
        cal.set(year, month, day);
        loadData(result, cal, index);

        return result;
    }

    /**
     * 加载数据并合并
     */
    protected void loadData(List<DynaRecord> result, Calendar cal, int index) {
        String bm1 = JdbcMgr.table_date;
        String bm2 = SmsQuery.table_date;

        switch (index) {
        case 0:
        // 本日
        case 1:
            bm1 = JdbcMgr.table_date;
            bm2 = SmsQuery.table_date;
            // 昨日
            break;
        case 2:
        // 本月
        case 3:
            bm1 = JdbcMgr.table_month;
            bm2 = SmsQuery.table_month;
            // 上月
            break;
        case 4:
        // 近三月
        case 5:
            // 上三月
            bm1 = JdbcMgr.table_3month;
            bm2 = SmsQuery.table_3month;
            break;
        case 6:
            bm1 = JdbcMgr.table_year;
            bm2 = SmsQuery.table_year;
            // 本年
            break;
        default:
            break;
        }

        // "web", "wap", "mini"
        if (index == 6) {

        } else {
            mergData(result, dbMgr.getData1(bm1, cal), index);
        }

        // "sms"
        if (index == 6) {

        } else {
            String smsSql = SmsQuery.makeSql(bm2, cal);
            mergData(result, dbMgr.getData1(smsSql), index);
        }

        // 4i
        if (index == 4 || index == 5) {

        } else {
            String theSql = Query4i.makeSql1(cal, index);
            mergData2(result, dbMgr.getData2(theSql), index);

            theSql = Query4i.makeSql2(cal, index);
            mergData2(result, dbMgr.getData2(theSql), index);

            theSql = Query4i.makeSql3(cal, index);
            mergData2(result, dbMgr.getData2(theSql), index);

            theSql = Query4i.makeSql4(cal, index);
            mergData2(result, dbMgr.getData2(theSql), index);
        }
    }

    /** 初始化数据并返回 */
    protected List<DynaRecord> initData() {
        List<DynaRecord> result = new ArrayList<DynaRecord>();
        for (int i = 0; i < channels.length; i++) {
            String name = channels[i];
            DynaRecord dr = initRecord(name);
            result.add(dr);
        }
        return result;
    }

    /** 创建且初始化数据记录，然后返回数据记录 */
    protected DynaRecord initRecord(String name) {
        DynaRecord result = new DynaRecord();
        result.setName(name);
        List<String> items = result.getItems();
        for (int i = 0; i < itemCount; i++)
            items.add("0");

        int pos = indexOf(name);
        if (pos < 4) {
            items.set(6, "-");
        } else if (pos <= 7) {
            items.set(4, "-");
            items.set(5, "-");
        }
        return result;
    }

    /** 释放相关资源 */
    public void destory() {
        dbMgr.destory();
    }

    /** 获取数据项格式 */
    public int getItemCount() {
        return itemCount;
    }

    /** 设置数据项个数 */
    public void setItemCount(int itemCount) {
        if (itemCount < 1)
            return;
        this.itemCount = itemCount;
    }

    /** 获取渠道 */
    public String[] getChannels() {
        return channels;
    }

    /** 设置渠道 */
    public void setChannels(String[] channels) {
        if (channels == null || channels.length <= 0)
            return;
        this.channels = channels;
    }

    /** 设置数据访问对象 */
    public void setDbMgr(JdbcMgr dbMgr) {
        if (dbMgr == null)
            return;
        this.dbMgr = dbMgr;
    }

    /** 返回指定渠道的顺序 */
    private int indexOf(String name) {
        int rt = -1;
        for (int i = 0; i < channels.length; i++) {
            if (channels[i].equals(name)) {
                rt = i;
                break;
            }
        }

        return rt;
    }
}
