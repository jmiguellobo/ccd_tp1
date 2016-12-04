package pt.isel.ccd.arithmetic;

import java.math.BigDecimal;

/**
 * Created by trinkes on 01/12/2016.
 */
public class Section {
    protected BigDecimal low;
    protected BigDecimal high;

    Section(BigDecimal high, BigDecimal low) {
        this.high = high;
        this.low = low;
    }

    boolean isIn(BigDecimal tag) {
        return tag.compareTo(high) < 0 && tag.compareTo(low) > 0;
    }
}
