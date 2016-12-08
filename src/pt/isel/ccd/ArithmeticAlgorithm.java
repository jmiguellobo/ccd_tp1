package pt.isel.ccd;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;

public class ArithmeticAlgorithm {
    private final static String resourcesPath = "src/resources/";
    private final static String lenaFicheiro = "teste";

    String filePath;

    public ArithmeticAlgorithm(String filePath) {
        this.filePath = filePath;
    }

    public static void main(String[] args) {
        new ArithmeticAlgorithm(resourcesPath + lenaFicheiro).start();
    }

    private void start() {
        File file = new File(filePath);
        Util util = new Util();
        Integer[] sequence = util.readFile(lenaFicheiro);
        System.out.println("sequence: " + Arrays.toString(sequence));
        HashMap<Integer, Double> probabilities = util.calcProbabilities(sequence);

        System.out.println("Probabilities:");
        util.printMap(probabilities);

        encode(sequence, probabilities);
    }

    private <T> void encode(Integer[] sequence, HashMap<T, Double> probabilities) {
        Interval interval = new Interval<T>(0, 1);
        for (Integer symbol : sequence) {
            interval.divideInterval(probabilities);
            interval.encodeSymbol(symbol);
        }

        System.out.println("The tag is: " + interval.getTag());
    }

}
