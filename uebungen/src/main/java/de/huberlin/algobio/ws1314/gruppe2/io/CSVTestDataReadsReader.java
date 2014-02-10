package de.huberlin.algobio.ws1314.gruppe2.io;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVTestDataReadsReader {

	private String zeile;
	private ArrayList<Integer> list;
	private String[] split;
	
	private ArrayList<byte[]> dataList;
	
	private String fileName;
	
	public CSVTestDataReadsReader(String pFileName) {
		fileName = pFileName;
		read();
	}
	
	public void read() {
		dataList = new ArrayList<byte[]>();
		
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader data = new BufferedReader(file);
			
			while ((zeile = data.readLine()) != null) {
				split = zeile.split(",");
				dataList.add(split[1].getBytes());
			}
		} catch (FileNotFoundException e) {
			System.out.println("Datei nicht gefunden");
		} catch (IOException e) {
			System.out.println("E/A-Fehler");
		}
	}
	
	public ArrayList<byte[]> getData() {
		return dataList;
	}
	
	public byte[] getData(int index) {
		return dataList.get(index);
	}
	
	public void print() {
		for (int i = 0; i < 5; i++) {
			System.out.print((i+1) + ":");
			for (int j = 0; j < dataList.get(i).length; j++) {
				System.out.println(dataList.get(i)[j]);
			}
		}
	}
}