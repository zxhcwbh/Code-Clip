/**
 * 文件名:MetricsTransfer.java
 * 日期：2010-5-18
 * @author：曾宪华
 * @version:1.0
 */

package codeclip.my.daq.core.datatrans;

/**
 * 度量转换器
 */
public class MetricsTransfer implements Transformer {
    /** 转换因子 */
    private float factor = 1;

    /**
     * @see codeclip.my.daq.datatrans.Transformer#transfer(java.lang.String)
     */
    public String transfer(String src) {
        if (src == null || src.length() <= 0)
            return src;
        
        float mysrc = Float.parseFloat(src);
        return "" + transfer(mysrc);
    }

    /** 进行转换,返回转换后的结果 */
    public float transfer(float src) {
        return src * factor;
    }

    public float getFactor() {
        return factor;
    }

    public void setFactor(float factor) {
        this.factor = factor;
    }

}
