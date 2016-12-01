package pt.isel.ccd;

import java.io.File;
import java.util.HashMap;

public class ArithmeticAlgorithm {
	private final static String path = "/Users/Mig/Dropbox/Mestrado Eng. Informática/1ºAno - 1ºSemestre/Compressão e Codificação de Dados/ficheiros/";
	private final static String lenaFicheiro = "lena.zip";
		
	String filePath;
	public static void main(String[] args) {
		new ArithmeticAlgorithm(path+lenaFicheiro).start();
	}
	public ArithmeticAlgorithm(String filePath) {
		this.filePath = filePath;
	}
	
	private void start() {
		File file = new File(filePath);
		HashMap<Byte, Double> pMap = Util.readFile(file);
		
	}

}
