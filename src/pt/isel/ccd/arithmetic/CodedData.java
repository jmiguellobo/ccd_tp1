package pt.isel.ccd.arithmetic;

/**
 * Created by trinkes on 03/12/2016.
 */
public class CodedData {
    private int length;
    private Double tag;

    public CodedData(double tag, int length) {
        this.tag = tag;
        this.length = length;
    }

    public double getTag() {
        return tag;
    }

    public int getLength() {
        return length;
    }
}
