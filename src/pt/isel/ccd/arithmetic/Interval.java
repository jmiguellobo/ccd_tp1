package pt.isel.ccd.arithmetic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by trinkes on 01/12/2016.
 */
class Interval<T> extends Section {
    private BigDecimal amplitude;

    Interval(BigDecimal low, BigDecimal high) {
        super(high, low);
        updateAmplitude(new Section(high, low));
    }

    private void updateAmplitude(Section selectedZone) {
        high = selectedZone.high;
        low = selectedZone.low;
        this.amplitude = high.subtract(low).abs();
    }

    private HashMap<T, Section> divideInterval(HashMap<T, BigDecimal> probabilities) {
        BigDecimal baseValue = low;
        HashMap<T, Section> zones = new HashMap<>();
        for (Map.Entry<T, BigDecimal> entry : probabilities.entrySet()) {
            BigDecimal ampValue = entry.getValue().multiply(amplitude);
            zones.put(entry.getKey(), new Section(ampValue.add(baseValue), baseValue));
            baseValue = baseValue.add(ampValue);
        }
        return zones;
    }

    void encodeSymbol(HashMap<T, BigDecimal> probabilities, T symbol) {
        HashMap<T, Section> zones = divideInterval(probabilities);
        Section selectedZone = zones.get(symbol);
        updateAmplitude(selectedZone);
    }

    BigDecimal getTag() {
        return low.add(amplitude.divide(new BigDecimal(2)));
    }

    T decodeSymbol(HashMap<T, BigDecimal> probabilities, BigDecimal tag) {
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
