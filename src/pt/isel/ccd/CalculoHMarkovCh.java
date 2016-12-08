package pt.isel.ccd;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Map.Entry;

public class CalculoHMarkovCh {

	private final static String ficheiro = "alice29.txt";
	
	private final static String path = "src/resources/"
			+ ficheiro;
	
	
		public static void main(String[] args) {
			// TODO Auto-generated method stub

			File file = new File(path);

			byte[] bFile = new byte[(int) file.length()];

			HashMap<String, HashMap<String, Double>> pMap = readFileMem(file, bFile);

			// Gravar ficheiro

	}
		
		private static HashMap<String, HashMap<String, Double>> readFileMem(File file, byte[] bFile) {
			FileInputStream fileInputStream;
			HashMap<String, HashMap<String, Integer>> map = new HashMap<String, HashMap<String, Integer>>();

			HashMap<String, HashMap<String, Double>> probablityMap = new HashMap<String, HashMap<String, Double>>();

			HashMap<String, Integer> occurMap = new HashMap<String, Integer>();
			double Hx = 0;

			try {
				// convert file into array of bytes
				fileInputStream = new FileInputStream(file);
				fileInputStream.read(bFile);
				fileInputStream.close();

				for (int i = 1; i < bFile.length; i++) {
					HashMap<String, Integer> cur = map.get(String.valueOf(bFile[i] & 0xFF));
					if (cur == null) {
						HashMap<String, Integer> secMap = new HashMap<String, Integer>();
						secMap.put(String.valueOf(bFile[i - 1] & 0xFF), 1);
						map.put(String.valueOf(bFile[i - 1] & 0xFF), secMap);
					} else {
						Integer lastValue = cur.get(String.valueOf(bFile[i - 1] & 0xFF));
						if (lastValue == null) {
							lastValue = 0;
						}
						cur.put(String.valueOf(bFile[i - 1] & 0xFF), lastValue + 1);

					}

				}

				String lastKey = "";
				int count = 0;
				int countTotal = 0;

				for (Entry<String, HashMap<String, Integer>> entry : map.entrySet()) {

					HashMap<String, Integer> cur = entry.getValue();

					System.out.println("Simbolo : -------" + entry.getKey() + "--------- ");

					for (Entry<String, Integer> b : cur.entrySet()) {
						System.out.println("[" + entry.getKey() + "," + b.getKey() + "] = " + b.getValue());

						if (entry.getKey() == lastKey || lastKey == "") {
							count = count + b.getValue();

						} else {
							occurMap.put(lastKey, count);
							count = 0;
							count = count + b.getValue();

						}
						countTotal = countTotal + b.getValue();
						lastKey = entry.getKey();
						// if(pCur == null){
						// pCur = new HashMap<String, Double>();
						// b.getValue();
						// pCur.put(entry.getValue());

						// }
						// Hx += calcH(bFile, cur, pCur);
					}
				}

				occurMap.put(lastKey, count);
				count = 0;

				// Calcular entropia

				for (Entry<String, Integer> b : occurMap.entrySet()) {
					System.out.println("Simbolo: " + b.getKey() + " Total: " + b.getValue());

				}
				System.out.println("Count Total: " + countTotal);
				// System.out.println(H);
				//
				// System.out.println("Done " + bFile.length);

				for (Entry<String, HashMap<String, Integer>> entry : map.entrySet()) {

					HashMap<String, Integer> cur = entry.getValue();
					HashMap<String, Double> newHashMap = new HashMap<String, Double>();
					System.out.println("----------------------");
					System.out.println("Key: " + entry.getKey());

					for (Entry<String, Integer> b : cur.entrySet()) {
						// System.out.println("b.getValue()" + b.getValue());
						// System.out.println("entry.getKey()" + entry.getKey());
						// System.out.println("occurMap.get(entry.getKey())" +
						// occurMap.get(entry.getKey()));
						// System.out.println("countTotal" + countTotal);
						double z = (b.getValue().doubleValue() / occurMap.get(entry.getKey()).doubleValue());

						Hx = Hx + (b.getValue().doubleValue() / countTotal) * log(1 / z);

						/// Escrever ficheiro

						newHashMap.put(b.getKey(), z);
						System.out.println("          SubKey: "+ b.getKey());
						System.out.println(" Prob: "+z);
					}
					probablityMap.put(entry.getKey(), newHashMap);

				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("Hx=" + Hx);

			try {
				writeFileMEM(bFile, probablityMap);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return probablityMap;
		}

		

		private static void writeFileMEM(byte[] bFile, HashMap<String, HashMap<String, Double>> pMap)
				throws FileNotFoundException, IOException {
			StringBuilder builder = new StringBuilder();
			int last = 0;
			for (int i = 0; i < bFile.length; i++) {
				last = getSymM(pMap, last);
				builder.append((char) last);

			}

			FileOutputStream out = new FileOutputStream(new File(
					path+"NEW"+ficheiro));
			out.write(builder.toString().getBytes());
			out.close();
		}
		
		private static int getSymM(HashMap<String, HashMap<String, Double>> pMap, int last) {
			Random rand = new Random();
			Double valRand = rand.nextDouble();
			Double sumEntry = 0.0;
			if (last != 0) {
				HashMap<String, Double> problidades = new HashMap<String, Double>();
				problidades = pMap.get(String.valueOf(last));

				for (Map.Entry<String, Double> entry : problidades.entrySet()) {
					sumEntry += entry.getValue();
					if (sumEntry >= valRand) {
						return Integer.valueOf(entry.getKey());
					}

				}
			} else {
				return 121;
			}
			return 0;

		}
		static double log(double x) {
			return (Math.log(x) / Math.log(2));
		}
		
		
}
