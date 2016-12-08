package pt.isel.ccd.shannon;

import pt.isel.ccd.FilePaths;
import pt.isel.ccd.PrintToFile;
import pt.isel.ccd.Printer;
import pt.isel.ccd.Util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Created by trinkes on 08/12/2016.
 */
public class ShannonAlgorithm {

    private Util util;
    private Random random;
    private int order;
    private String filePath;
    private int generatedSequenceSize;
    private Printer printer;

    public ShannonAlgorithm(Util util, Random random, int order, String filePath, int
            generatedSequenceSize, Printer printer) {
        this.util = util;
        this.random = random;
        this.order = order;
        this.filePath = filePath;
        this.generatedSequenceSize = generatedSequenceSize;
        this.printer = printer;
    }

    public static void main(String[] args) {
        int generatedSequenceSize = 300000;
        int order = 0;
        String filePath = FilePaths.lenaFile;
        ShannonAlgorithm shannonAlgorithm = new ShannonAlgorithm(new Util(), new Random(), order,
                filePath, generatedSequenceSize, new PrintToFile(FilePaths
                .outputsPath +
                "/shannonAlg0" +
                ".txt"));
        order++;
        shannonAlgorithm.start(order, new PrintToFile(FilePaths.outputsPath +
                "/shannonAlg" +
                ".txt"));
        System.out.println(order + " order is done");
        order++;
        shannonAlgorithm.start(order, new PrintToFile(FilePaths.outputsPath +
                "/shannonAlg" + order + ".txt"));
        System.out.println(order + " order is done");
        order++;
        shannonAlgorithm.start(order, new PrintToFile(FilePaths.outputsPath +
                "/shannonAlg" + order + ".txt"));
        System.out.println(order + " order is done");
    }

    private void start() {
        List<Byte> sequence = util.readFile(filePath);

        List<Byte> generatedSequence = new ArrayList<>();
        for (int i = 0; i < generatedSequenceSize; i++) {
            generateInnerSequence(sequence, generatedSequence);
            System.out.println("order " + order + " is " + (i * 100 / generatedSequenceSize) + "% " +
                    "completed ");
        }
        printer.print(getBytes(generatedSequence));
    }

    private void start(int order, Printer printer) {
        this.order = order;
        this.printer = printer;
        start();
    }

    private void generateInnerSequence(List<Byte> sequence, List<Byte> generatedSequence) {
        int index = pickRandomLetter(sequence);
        generatedSequence.add(sequence.get(index));
        Byte symbol = pickRandomLetter(sequence, generatedSequence.subList(generatedSequence
                .size() - order - 1 > 0 ? generatedSequence.size() - order - 1 : 0, generatedSequence.size()));
        if (symbol != null) {
            generatedSequence.add(symbol);
        }
    }

    private byte[] getBytes(List<Byte> generatedSequence) {
        byte[] bytes = new byte[generatedSequence.size()];
        for (int i = 0; i < generatedSequence.size(); i++) {
            bytes[i] = generatedSequence.get(i);
        }
        return bytes;
    }

    private <T> T pickRandomLetter(List<T> sequence, List<T> symbols) {
        int i = pickRandomLetter(sequence);
        int index = Collections.indexOfSubList(sequence.subList(i, sequence.size()), symbols);
        if (index < 0) {
            index = Collections.indexOfSubList(sequence, symbols);
        } else {
            index += i;
        }
        return index + symbols.size() > sequence.size() - 1 || index == -1 ? null : sequence.get
                (index + symbols.size());
    }

    private <T> int pickRandomLetter(List<T> sequence) {
        return random.nextInt(sequence.size());
    }
}
