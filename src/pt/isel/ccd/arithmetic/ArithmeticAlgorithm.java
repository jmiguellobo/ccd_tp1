package pt.isel.ccd.arithmetic;

import pt.isel.ccd.Util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ArithmeticAlgorithm {
    private final static String resourcesPath = "src/resources/";
    private final static String lenaFile = resourcesPath + "alice29.txt";

    String filePath;

    public ArithmeticAlgorithm(String filePath) {
        this.filePath = filePath;
    }

    public static void main(String[] args) {
        new ArithmeticAlgorithm(lenaFile).start();
    }

    private void start() {
        Util util = new Util();
        Integer[] sequence = util.readFile(lenaFile);
        System.out.println("sequence: " + Arrays.toString(sequence));
        HashMap<Integer, Double> probabilities = util.calcProbabilities(sequence);

        System.out.println("Probabilities:");
        util.printMap(probabilities);

        CodedData coded = encode(sequence, probabilities);
        List<Integer> originalSequece = decode(coded, probabilities);
        System.out.println("original Sequece = " + originalSequece.toString());
    }

    private <T> List<T> decode(CodedData coded, HashMap<T, Double> probabilities) {
        Interval<T> interval = new Interval<>(0, 1);
        List<T> originalSequence = new LinkedList<>();
        for (int i = 0; i < coded.getLength(); i++) {
            originalSequence.add(interval.decodeSymbol(probabilities, coded.getTag()));
        }

        return originalSequence;
    }

    private <T> CodedData encode(Integer[] sequence, HashMap<T, Double> probabilities) {
        Interval interval = new Interval<T>(0, 1);
        for (Integer symbol : sequence) {
            interval.encodeSymbol(probabilities, symbol);
        }

        System.out.println("The tag is: " + interval.getTag());
        return new CodedData(interval.getTag(), sequence.length);
    }

}
