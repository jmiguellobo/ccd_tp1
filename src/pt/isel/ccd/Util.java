package pt.isel.ccd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Util {

    private static final char DEFAULT_SEPARATOR = ';';

    public static void writeLine(HashMap<String, Double> values, String path) throws IOException {
        writeLine(values, DEFAULT_SEPARATOR, ' ', path);
    }

    private static String followCVSformat(String value) {

        String result = value;
        if (result.contains("\"")) {
            result = result.replace("\"", "\"\"");
        }
        return result;

    }

    public static void writeLine(HashMap<String, Double> mapValues, char separators, char customQuote, String path)
            throws IOException {
        FileWriter w = new FileWriter(path);
        boolean first = true;
        Object[] values = mapValues.keySet().toArray(); // returns an array of
        // keys

        Object[] values2 = mapValues.values().toArray(); // returns an array of
        // keys
        int i = 0;
        // default customQuote is empty

        if (separators == ' ') {
            separators = DEFAULT_SEPARATOR;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Simbolo");
        sb.append(separators);
        sb.append("Probabilidade");
        sb.append(separators);
        sb.append("\n");
        for (Object value : values) {

            if (!first) {

                sb.append(separators);
                sb.append("\n");
            }
            if (customQuote == ' ') {
                sb.append(followCVSformat(value.toString()));
                sb.append(separators);
                sb.append(followCVSformat(values2[i].toString()));

            } else {
                sb.append(customQuote).append(followCVSformat(value.toString())).append(customQuote);
            }

            first = false;

            i++;
        }

        w.append(sb.toString());

        w.flush();
        w.close();

    }

    public static HashMap<Byte, Double> readTestingFile(File file) {
        FileInputStream fileInputStream;
        HashMap<Byte, Integer> map = new HashMap<Byte, Integer>();
        HashMap<Byte, Double> pMap = new HashMap<Byte, Double>();

        byte[] bFile = new byte[(int) file.length()];
        try {
            // convert file into array of bytes

            //SmallBinaryFiles binary = new SmallBinaryFiles();
            //	 byte[] bytes = binary.readSmallBinaryFile(FILE_NAME);


            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            byte[] values = new byte[10];
            while (fileInputStream.read(values) >= 0) {
                System.out.println("value = " + values);
            }

            fileInputStream.close();

            for (int i = 0; i < bFile.length; i++) {
                Integer cur = map.get(bFile[i]);
                // System.out.println("symb= " + bFile[i]);
                if (cur == null) {
                    map.put(bFile[i], 1);
                } else {
                    map.put(bFile[i], cur + 1);
                }
            }
            for (Byte symbol : map.keySet()) {
                double Px = (map.get(symbol).doubleValue() / bFile.length);
                pMap.put(symbol, Px);
                System.out.println("sym = " + symbol + "p= " + Px);
            }
            System.out.println("Done " + bFile.length);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return pMap;
    }

    public double log(double x) {
        return (Math.log(x) / Math.log(2));
    }

    public Integer[] readTestingFile(String filePath) {
        return new Integer[]{0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 1, 0, 0, 1, 0, 1, 0, 1};
    }

    public List<Byte> readFile(String filePath) {
        File file = new File(filePath);
        byte[] bFile = new byte[(int) file.length()];
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);
            List<Byte> list = new ArrayList<>();
            for (byte b : bFile) {
                list.add(b);
            }
            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }

    public <T> HashMap<T, Double> calcProbabilities(T[] sequence) {
        HashMap<T, Double> probabilities = new HashMap<>();
        HashMap<T, Integer> occurrences = getOccurrences(sequence);
        for (Map.Entry<T, Integer> entry : occurrences.entrySet()) {
            double probability = entry.getValue() / ((float) sequence.length);
            probabilities.put(entry.getKey(), probability);
        }

        return probabilities;
    }

    private <T> HashMap<T, Integer> getOccurrences(T[] sequence) {
        HashMap<T, Integer> occurrences = new HashMap<>();
        Integer occurrence;
        for (T value : sequence) {
            occurrence = occurrences.get(value);
            if (occurrence == null) {
                occurrence = 0;
            }
            occurrences.put(value, ++occurrence);
        }
        return occurrences;
    }

    public void printMap(HashMap<?, ?> occurrences) {
        for (Map.Entry<?, ?> entry : occurrences.entrySet()) {
            System.out.println(entry.getKey().getClass().getSimpleName() + " = " + entry.getKey() + " " + entry.getValue().getClass().getSimpleName() + " = " + entry.getValue());
        }
    }
}