package pt.isel.ccd;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by trinkes on 01/12/2016.
 */
class Interval<T> extends Section {
    private double amplitude;
    private HashMap<T, Section> zones;

    Interval(int low, int high) {
        super(high, low);
        updateAmplitude();
        zones = new HashMap<>();
    }

    private void updateAmplitude() {
        this.amplitude = Math.abs(high - low);
    }

    void divideInterval(HashMap<T, Double> probabilities) {
        double baseValue = 0;
        for (Map.Entry<T, Double> entry : probabilities.entrySet()) {
            double ampValue = entry.getValue() * amplitude;
            zones.put(entry.getKey(), new Section(ampValue + baseValue, baseValue));
            baseValue += ampValue;
        }
    }

    public void encodeSymbol(T symbol) {
        Section selectedZone = zones.get(symbol);
        high = selectedZone.high;
        low = selectedZone.low;
        updateAmplitude();
    }

    double getTag() {
        return low + (amplitude / 2);
    }
}
