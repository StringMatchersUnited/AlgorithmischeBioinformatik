package de.huberlin.algobio.ws1314.gruppe2.io;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVReader {

	private String zeile;
	private ArrayList<Integer> list;
	private String[] split;
	
	private int[][] matrix;
	private int mLength;
	
	private String fileName;
	
	public CSVReader(String pFileName) {
		fileName = pFileName;
		read();
	}
	
	public void read() {
		int j = 0;
		list = new ArrayList<Integer>();
		
		try {
				FileReader file = new FileReader(fileName);
				BufferedReader data = new BufferedReader(file);
				
				// erste Zeile einlesen, um Größe festzustellen
				zeile = data.readLine();
				split = zeile.split(";");
				for( int i = 0; i < split.length; i++ ) {
					//eventuelle Leerzeichen zwischen zwei ';' entfernen 
					//und Wert in Liste schreiben
					list.add(Integer.parseInt(split[i].trim()));
				}
				
				mLength = list.size();
				matrix = new int[mLength][mLength];
				for ( int i = 0; i < mLength; i++ ) {
					matrix[i][j] = list.get(i);
				}
				
				j++;
				list.clear();
				
				while ((zeile = data.readLine()) != null) {
					split = zeile.split(";");
					for( int i = 0; i < split.length; i++ ) {
						//eventuelle Leerzeichen zwischen zwei ';' entfernen 
						//und Wert in Liste schreiben
						matrix[i][j] = Integer.parseInt(split[i].trim());
					}
					j++;
				}
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden");
		} catch (IOException e) {
			System.out.println("E/A-Fehler");
		}
	}
	
	public int[][] getData() {
		return matrix;
	}
	
	public int getLength () {
		return mLength;
	}
}