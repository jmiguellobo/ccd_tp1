package pt.isel.ccd.shannon;

import pt.isel.ccd.FilePaths;
import pt.isel.ccd.PrintToFile;
import pt.isel.ccd.Printer;
import pt.isel.ccd.Util;

import java.util.ArrayList;
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
        ShannonAlgorithm shannonAlgorithm = new ShannonAlgorithm(new Util(), new Random(), 50,
                FilePaths.lenaFile, 100, new PrintToFile(FilePaths.outputsPath + "/shannonAlg" +
                ".txt"));
        shannonAlgorithm.start();
    }

    private void start() {
        List<Byte> sequence = util.readFile(filePath);

        List<Byte> generatedSequence = new ArrayList<>();
        for (int i = 0; i < generatedSequenceSize; i++) {
            generateInnerSequence(sequence, generatedSequence);
        }
        printer.print(getBytes(generatedSequence));
    }

    private void generateInnerSequence(List<Byte> sequence, List<Byte> generatedSequence) {
        int index = pickRandomLetter(sequence);
        generatedSequence.add(sequence.get(index));
        generatedSequence.add(pickRandomLetter(sequence, generatedSequence.subList(generatedSequence.size() - order - 1 > 0 ? generatedSequence.size() - order - 1 : 0, generatedSequence.size())));
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
        int j;
        for (; i < sequence.size() - symbols.size(); i++) {
            for (j = 0; j < symbols.size(); j++) {
                if (symbols.get(j).equals(sequence.get(j + i))) {
                    return sequence.get(i + symbols.size());
                }
            }
        }
        return pickRandomLetter(sequence, symbols);
    }

    private <T> int pickRandomLetter(List<T> sequence) {
        return random.nextInt(sequence.size());
    }
}
