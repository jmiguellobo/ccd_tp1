package pt.isel.ccd.arithmetic;

/**
 * Created by trinkes on 01/12/2016.
 */
public class Section {
    protected double low;
    protected double high;

    Section(double high, double low) {
        this.high = high;
        this.low = low;
    }

    boolean isIn(double tag) {
        return tag <= high && tag > low;
    }
}
