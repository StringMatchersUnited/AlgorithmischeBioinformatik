package de.huberlin.algobio.ws1314.gruppe2.io;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CSVQueryReader {

	private String zeile;
	private ArrayList<Integer> list;
	private String[] split;
	
	private ArrayList<byte[]> dataList;
	private ArrayList<Integer> kList;
	
	private String fileName;
	
	public CSVQueryReader(String pFileName) {
		fileName = pFileName;
		read();
	}
	
	public void read() {
		dataList = new ArrayList<byte[]>();
		kList = new ArrayList<Integer>();
		
		try {
			FileReader file = new FileReader(fileName);
			BufferedReader data = new BufferedReader(file);
			
			while ((zeile = data.readLine()) != null) {
				split = zeile.split(":");
				split = split[1].split(",");
				dataList.add(split[0].getBytes());
				kList.add(Integer.parseInt(split[1]));
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
	
	public ArrayList<Integer> getK () {
		return kList;
	}
	
	public void print() {
		for (int i = 0; i < dataList.size(); i++) {
			System.out.print((i+1) + ":");
			for (int j = 0; j < dataList.get(i).length; j++) {
				System.out.print(dataList.get(i)[j]);
			}
			System.out.println("," + kList.get(i));
		}
	}
}