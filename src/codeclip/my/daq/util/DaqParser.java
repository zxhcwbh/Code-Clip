/**
 * 文件名:DaqParser.java
 * 日期：2010-5-19
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import codeclip.my.daq.core.datamap.DataMap;
import codeclip.my.daq.core.datatrans.CodeTransfer;
import codeclip.my.daq.core.datatrans.FieldTransfer;
import codeclip.my.daq.core.datatrans.MetricsTransfer;
import codeclip.my.daq.core.datatrans.Transformer;
import codeclip.my.daq.core.interfaces.FieldSpec;
import codeclip.my.daq.core.interfaces.FtpDataSpec;
import codeclip.my.daq.core.purge.MapRule;
import codeclip.my.daq.core.purge.RegexRule;
import codeclip.my.daq.core.purge.Rule;
import codeclip.my.daq.core.purge.Verifier;

/**
 * 解析有关数据采集的配置
 */
public class DaqParser {
    private static final String pre_spec = "daq.spec.";

    private static final String pre_spec_fs = "daq.spec.fs.";

    private static final String pre_spec_field = "daq.spec.fs";

    private static final String pre_spec_vf = "daq.spec.vf.";

    private static final String pre_rule_regex = "daq.rule.regex.";

    private static final String pre_rule_map = "daq.rule.map.";

    private static final String pre_ftpdata = "daq.ftpdata.";

    private static final String pre_datatrans_code = "daq.datatrans.code.";

    private static final String pre_datatrans_metrics = "daq.datatrans.metrics.";

    private static final String pre_datatrans_field = "daq.datatrans.field.";

    private static final String daq_properties = "com/sinovatech/unicom/ecs/daq/daq.properties";

    //数据采集公用的配置
    private static Properties daq = new Properties();
    static {
        InputStream is = ClassLoader.getSystemResourceAsStream(daq_properties);
        try {
            daq.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /** 解析接口协议 */
    public static FtpDataSpec parseSpec(String fileName) {
        InputStream is = ClassLoader.getSystemResourceAsStream(fileName);
        Properties data_spec = new Properties();
        try {
            data_spec.load(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FtpDataSpec spec = new FtpDataSpec();
        spec.setDataName(data_spec.getProperty(pre_spec + "dataname"));

        List<FieldSpec> fs = spec.getFields();
        int count = 0;
        String field = data_spec.getProperty(pre_spec_field + count);
        while (field != null) {
            fs.add(parseFieldSpec(field, data_spec));
            count++;
            field = data_spec.getProperty(pre_spec_field + count);
        }
        return spec;
    }

    /** 解析字段规范 */
    public static FieldSpec parseFieldSpec(String field, Properties specProps) {
        String pre = pre_spec_fs + field + ".";
        String fname = specProps.getProperty(pre + "fname");
        String fdesc = specProps.getProperty(pre + "fdesc");
        String vf = specProps.getProperty(pre + "vf");

        FieldSpec rt = new FieldSpec();
        rt.setName(fname);
        rt.setDesc(fdesc);
        rt.setVerifier(getVf(vf, specProps));

        return rt;
    }

    /** 获取校验器 */
    public static Verifier getVf(String name, Properties specProps) {
        String pre = pre_spec_vf + name + ".";
        String dname = specProps.getProperty(pre + "dname");
        String ddesc = specProps.getProperty(pre + "ddesc");

        String rtype = specProps.getProperty(pre + "rtype");
        String rname = specProps.getProperty(pre + "rtype");

        Verifier vf = new Verifier();
        vf.setName(dname);
        vf.setDesc(ddesc);
        vf.setRule(getRule(rtype, rname));

        return vf;
    }

    /** 获取校验规则 */
    public static Rule getRule(String rtype, String rname) {
        Rule rt = null;
        if (rtype.equals("regex")) {
            rt = getRegexRule(rname);
        } else if (rtype.equals("map")) {
            rt = getMapRule(rname);
        }
        return rt;
    }

    /** 获取正则表达式校验规则 */
    public static Rule getRegexRule(String rname) {
        RegexRule rt = new RegexRule();
        String regex = pre_rule_regex + rname;
        rt.setRegex(daq.getProperty(regex));
        return rt;
    }

    /** 获取代码映射表校验规则 */
    public static Rule getMapRule(String rname) {
        String pre = pre_rule_map + rname + ".";
        MapRule rt = new MapRule();
        Map<String, String> table = rt.getCodeTable();

        int count = 1;
        String str = daq.getProperty(pre + "code" + count);
        while (str != null) {
            String[] keyval = str.split(",");
            table.put(keyval[0], keyval[1]);
            count++;
            str = daq.getProperty(pre + "code" + count);
        }

        return rt;
    }

    /** 获取代码转换器 */
    public static Transformer getCodeTransfer(String dataName, int dataIndex) {
        String pre = pre_ftpdata + dataName + "." + dataIndex + ".";
        String transName = daq.getProperty(pre + "code");

        CodeTransfer ct = new CodeTransfer();
        ct.setName(daq.getProperty(pre_datatrans_code + transName));

        return ct;
    }

    /** 获取度量转换器 */
    public static Transformer getMetricsTransfer(String dataName, int dataIndex) {
        String pre = pre_ftpdata + dataName + "." + dataIndex + ".";
        String transName = daq.getProperty(pre + "metrics");

        MetricsTransfer mt = new MetricsTransfer();
        float factor = Float.parseFloat(daq.getProperty(pre_datatrans_metrics
                + transName));
        mt.setFactor(factor);
        return mt;
    }

    /** 获取字段转换器 */
    public static Transformer getFieldTransfer(String dataName, int dataIndex) {
        String pre = pre_ftpdata + dataName + "." + dataIndex + ".";
        String transName = daq.getProperty(pre + "field");

        FieldTransfer ft = new FieldTransfer();
        String keyval = daq.getProperty(pre_datatrans_field + transName);
        ft.setAttr(transName);

        String[] sd = keyval.split(",");
        ft.setSrc(sd[0]);
        ft.setDest(sd[1]);

        return ft;
    }

    /** 获取数据映射 */
    public static DataMap getDataMap(String dataName) {
        DataMap dm = new DataMap();
        Map<String, String> table = dm.getTableDM();
        String preKey = dataName + ".";

        int count = 0;
        String key = preKey + count;
        String value = daq.getProperty(key);
        while (value != null) {
            table.put(key, value);
            count++;
            key = preKey + count;
            value = daq.getProperty(key);
        }

        return dm;
    }
}
