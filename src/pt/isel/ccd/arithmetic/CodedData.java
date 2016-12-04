package pt.isel.ccd.arithmetic;

import java.math.BigDecimal;

/**
 * Created by trinkes on 03/12/2016.
 */
public class CodedData {
    private int length;
    private BigDecimal tag;

    public CodedData(BigDecimal tag, int length) {
        this.tag = tag;
        this.length = length;
    }

    public BigDecimal getTag() {
        return tag;
    }

    public int getLength() {
        return length;
    }
}
