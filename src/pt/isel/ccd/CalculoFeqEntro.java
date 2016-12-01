package pt.isel.ccd;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class CalculoFeqEntro {

	
	private final static String ficheiro = "lena.zip";
	private final static String path = "/Users/Mig/Dropbox/Mestrado Eng. Informática/1ºAno - 1ºSemestre/Compressão e Codificação de Dados/ficheiros/"
			+ ficheiro;
	private final static Util newUtil = new Util();
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File file = new File(path);

		HashMap<String, Double> pMap = readFile(file);
		
		
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
			
			Util.writeLine(pMap, path.substring(0, path.length()-4)+path.substring(path.length()-3)+".csv"); 
					//path.split("\\.")[0]+"_"+path.split("\\.")[1]+"csv");
			
			System.out.println("Entropia: " + entropia);

			System.out.println("Done " + bFile.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pMap;
	}
	
	
	private static double calcH(byte[] bFile, HashMap<String, Integer> map, HashMap<String, Double> pMap) throws IOException {

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

}
