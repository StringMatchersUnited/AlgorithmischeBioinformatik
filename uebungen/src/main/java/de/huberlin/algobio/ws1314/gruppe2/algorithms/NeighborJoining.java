package de.huberlin.algobio.ws1314.gruppe2.algorithms;


import java.util.Arrays;
import java.util.HashMap;


public class NeighborJoining
{
    public NeighborJoining( int[][] matrix,  int mLength)
    {
    	int[][] aMatrix = matrix; // eine als referenz
    	int[][] aMatrix2 = new int[mLength][mLength]; // und eine als neue
    	
    	int[][] aRef;
    	int[][] aNew;
    	
    	String[] sequence = new String[mLength]; // für bezeichnung
    	
    	int[] distance = new int[mLength]; // für u
    	int curLength = mLength; // länge nimmt um 1 je iteration ab
    	
    	int curMinU; // aktuell kleinstes u
    	int curMinX; // dazu das x
    	int curMinY; // dazu das y
    	int u;
    	int offset; 
    	
    	// initial spalten benennen
    	for ( int i = 0; i < mLength; i++ ) {
    		sequence[i] = String.valueOf( i + 1 );
    	}
    	
    	while ( curLength > 2 ) { // solange mehr als 2 spalten/zeilen existieren
    		
    		if ( ( mLength - curLength ) % 2 == 0) {
    			aRef = aMatrix;
    			aNew = aMatrix2;
    		} else {
    			aRef = aMatrix2;
    			aNew = aMatrix;
    		}
    		
    		// 1. berechne alle u(i) (für jede zeile)
    		for ( int i = 0; i < curLength; i++ ) {
    			u = 0;
    			// berechne u(i) für jetziges i
    			for ( int j = 0; j < curLength; j++ ) {
    				u = u + aRef[i][j];
    			}
    			u = u - aRef[i][i];
    			u = u / ( curLength - 2 );
    			distance[i] = u;
    		}
    		
    		// 2. berechne alle D[i,j] und suche kleinstes
    		curMinU = 0;
    		curMinX = 0;
    		curMinY = 0;
    		for ( int i = 0; i < ( curLength - 1 ); i++ ) { // für jede zeile, außer die letzte
    			for (int j = ( i + 1 ); j < curLength; j++) { // für jede Kombination, keine dopplungen
    				// "u" wird missbraucht
    				u = aRef[i][j] - distance[i] - distance[j];
    				if ( u <= curMinU ) {
    					curMinU = u;
    					curMinX = i;
    			    	curMinY = j;
    				}
    			}
    		}
    		
    		// 3. neuer Knoten, neue Distanzen
    		// füllen der neuen matrix
    		offset = 0;
    		
    		for ( int i = 0; i < curLength; i++ ) { // für jede zeile
    			if ( ( i == curMinX ) || ( i == curMinY ) ) { // wenn zusammengelegt werden soll
    				offset++;
    			} else { // sonst einsortieren
    				// altes einsortieren
    				aNew[i - offset][i - offset] = aRef[i][i];
    				for ( int j = i + 1; j < curLength; j++) {
						aNew[i - offset][j - offset] = aRef[i][j];
						aNew[j - offset][i - offset] = aRef[i][j];
    				}
    				// zusammenlegen und neu berechnen
    				aNew[curLength - 2][i - offset] = ( aRef[curMinX][i] + aRef[i][curMinY] - aRef[curMinX][curMinY] ) / 2;
    				aNew[i - offset][curLength - 2] = ( aRef[curMinX][i] + aRef[i][curMinY] - aRef[curMinX][curMinY] ) / 2;
    			}
    		}
    		// hoher wert für gleiche sequenz
    		aNew[curLength - 2][curLength - 2] = 100;
    		
    		// (4.) alte Knoten loeschen -> entfaellt da 2 matrixen
    		
    		// 5. Ausgabe
    		System.out.println("(" + sequence[curMinY] + "," + sequence[curMinX] + ")");
    		
    		// bezeichnungen als letztes ändern
    		offset = 0;
    		for ( int i = 0; i < curLength; i++ ) {
    			if ( ( i == curMinX ) || ( i == curMinY ) ) {
    				offset++;
    			} else {
    				sequence[i - offset] = sequence[i];
    			}
    		}
    		sequence[curLength - 2] = sequence[curMinY] + sequence[curMinX];
    		
    		// curLength runtersetzen
    		curLength--;
    		
//    		for ( int i = 0; i < curLength; i++ ) {
//    			for ( int j = 0; j < curLength; j++ ) {
//    				System.out.print(aNew[i][j] + "  ");
//    			}
//    			System.out.println();
//    		}
    	}
    	
    	// letzte Ausgabe der beiden letzten Spalten
		System.out.println("(" + sequence[1] + "," + sequence[0] + ")");
    }
}
