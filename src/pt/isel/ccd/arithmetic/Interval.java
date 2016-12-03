package pt.isel.ccd.arithmetic;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by trinkes on 01/12/2016.
 */
class Interval<T> extends Section {
    private double amplitude;

    Interval(int low, int high) {
        super(high, low);
        updateAmplitude(new Section(high, low));
    }

    private void updateAmplitude(Section selectedZone) {
        high = selectedZone.high;
        low = selectedZone.low;
        this.amplitude = Math.abs(high - low);
    }

    private HashMap<T, Section> divideInterval(HashMap<T, Double> probabilities) {
        double baseValue = low;
        HashMap<T, Section> zones = new HashMap<>();
        for (Map.Entry<T, Double> entry : probabilities.entrySet()) {
            double ampValue = entry.getValue() * amplitude;
            zones.put(entry.getKey(), new Section(ampValue + baseValue, baseValue));
            baseValue += ampValue;
        }
        return zones;
    }

    void encodeSymbol(HashMap<T, Double> probabilities, T symbol) {
        HashMap<T, Section> zones = divideInterval(probabilities);
        Section selectedZone = zones.get(symbol);
        updateAmplitude(selectedZone);
    }

    double getTag() {
        return low + (amplitude / 2);
    }

    T decodeSymbol(HashMap<T, Double> probabilities, double tag) {
        HashMap<T, Section> zones = divideInterval(probabilities);
        Map.Entry<T, Section> symbol = null;
        for (Map.Entry<T, Section> entry : zones.entrySet()) {
            Section section = entry.getValue();
            if (section.isIn(tag)) {
                symbol = entry;
                break;
            }
        }
        updateAmplitude(symbol.getValue());
        return symbol.getKey();
    }
}
