package pt.isel.ccd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class CalculoFeqEntro {

	private final static String ficheiro = "alice29.txt";
	
	private final static String path = "src/resources/"
			+ ficheiro;
	private final static Util newUtil = new Util();

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		
		File file = new File(path);

		HashMap<String, Double> pMap = readFile(file);

		// Gerar ficheiro

		try {
			writeFile(file, pMap);

		} catch (IOException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static HashMap<String, Double> readFile(File file) {
		FileInputStream fileInputStream;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		HashMap<String, Double> pMap = new HashMap<String, Double>();

		byte[] bFile = new byte[(int) file.length()];
		try {
			// convert file into array of bytes
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bFile);
			fileInputStream.close();

			for (int i = 0; i < bFile.length; i++) {
				Integer cur = map.get(String.valueOf(bFile[i] & 0xFF));
				if (cur == null) {
					map.put(String.valueOf(bFile[i] & 0xFF), 1);
				} else {
					map.put(String.valueOf(bFile[i] & 0xFF), cur + 1);
				}

			}
			double entropia = calcH(bFile, map, pMap);

			Util.writeLine(pMap, path.substring(0, path.length() - 4) + path.substring(path.length() - 3) + ".csv");
			// path.split("\\.")[0]+"_"+path.split("\\.")[1]+"csv");

			System.out.println("Entropia: " + entropia);

			System.out.println("Done " + bFile.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pMap;
	}

	private static double calcH(byte[] bFile, HashMap<String, Integer> map, HashMap<String, Double> pMap)
			throws IOException {

		double H = 0;

		for (int h = 0; h <= 255; h++) {
			Integer simblo = map.get(String.valueOf(h));
			if (simblo != null) {
				double Px = (simblo.doubleValue() / bFile.length);
				double log = newUtil.log(1 / Px);
				H = H + (Px * log);
				System.out.println(h + "," + Px + "\n");

				pMap.put(String.valueOf(h), Px);

				System.out.println("index = " + h + " prob= " + pMap.get(String.valueOf(h)));

			} else {
				// System.out.println(h + "," + 0+"\n");
			}
		}
		return H;
	}

	private static void writeFile(File file, HashMap<String, Double> pMap)
			throws FileNotFoundException, IOException {
		StringBuilder builder = new StringBuilder();
		byte[] bFile = new byte[(int) file.length()];
		for (int i = 0; i < bFile.length; i++) {

			builder.append((char) (getSym(pMap)));

		}

		FileOutputStream out = new FileOutputStream(new File(
				path.substring(0, path.length() - 4) +"New"+ path.substring(path.length() - 4)));
		out.write(builder.toString().getBytes());
		out.close();
	}

	private static int getSym(HashMap<String, Double> pMap) {
		Random rand = new Random();
		Double valRand = rand.nextDouble();
		Double sumEntry = 0.0;

		for (Map.Entry<String, Double> entry : pMap.entrySet()) {
			sumEntry += entry.getValue();
			if (sumEntry >= valRand) {
				return Integer.valueOf(entry.getKey());
			}

		}

		return 0;
	}

}
