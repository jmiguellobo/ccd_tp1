package pt.isel.ccd;

import java.io.File;
import java.util.HashMap;

public class ArithmeticAlgorithm {
	private final static String resourcesPath = "src/resources/";
	private final static String lenaFicheiro = "Util.java";
		
	String filePath;
	public static void main(String[] args) {
		new ArithmeticAlgorithm(resourcesPath +lenaFicheiro).start();
	}
	public ArithmeticAlgorithm(String filePath) {
		this.filePath = filePath;
	}
	
	private void start() {
		File file = new File(filePath);
		HashMap<Byte, Double> pMap = Util.readFile(file);
		
	}

}
