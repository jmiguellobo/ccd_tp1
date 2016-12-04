package pt.isel.ccd.arithmetic;

import pt.isel.ccd.Util;

import java.io.IOException;
import java.math.BigDecimal;
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
        try {
            Byte[] sequence = util.readFile(lenaFile);
            System.out.println("sequence: " + Arrays.toString(sequence));
            HashMap<Byte, BigDecimal> probabilities = util.calcProbabilities(sequence);

            System.out.println("Probabilities:");
            util.printMap(probabilities);

            CodedData coded = encode(sequence, probabilities);
            List<Byte> originalSequece = decode(coded, probabilities);
            System.out.println("original Sequece = " + originalSequece.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private <T> List<T> decode(CodedData coded, HashMap<T, BigDecimal> probabilities) {
        Interval<T> interval = new Interval<>(new BigDecimal(0), new BigDecimal(1));
        List<T> originalSequence = new LinkedList<>();
        for (int i = 0; i < coded.getLength(); i++) {
            System.out.println(i);
            originalSequence.add(interval.decodeSymbol(probabilities, coded.getTag()));
        }

        return originalSequence;
    }

    private <T> CodedData encode(T[] sequence, HashMap<T, BigDecimal> probabilities) {
        Interval interval = new Interval<T>(new BigDecimal(0), new BigDecimal(1));
        for (T symbol : sequence) {
            System.out.println("encoding symbol: " + symbol);
            interval.encodeSymbol(probabilities, symbol);
        }

        System.out.println("The tag is: " + interval.getTag());
        return new CodedData(interval.getTag(), sequence.length);
    }

}
