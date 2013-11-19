package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;

public class boyerMoore
{
    public boyerMoore( byte[] pattern, int patternLength, byte[] template, int templateLength)
    {
    	int skipTable[] = getSkipTable(pattern, patternLength);
    	int nextTable[] = getNextTable(pattern, patternLength);
    	int hits = 0;
    	int comp = 0;		              // vergleiche
    	int[] firstTenHits = new int[10]; // ersten 10 als Liste?
        double expectedHits = Tools.calcExpectation(pattern, patternLength, template, templateLength);

    	for (int i = patternLength - 1, j; i < templateLength;) {
    		for (j = patternLength - 1; pattern[j] == template[i]; --i, --j) {
    			comp++;	// hier richtig? glaube schon! ;)
    			if (j == 0) {
    				hits++;
                    if (hits <= 10)
                        firstTenHits[hits-1] = i+1;
    				break;
    			}
    		}
    		i += Math.max(nextTable[patternLength - 1 - j], skipTable[template[i]]);
    	}
    	
    	System.out.println("> " + Tools.byteArrayToString(pattern, patternLength));
    	System.out.println(">> Length: " + patternLength);
    	System.out.println(">> Occurrences: " + hits);
    	System.out.println(">> Expected: " + String.format("%f", expectedHits));  // erwartungswert aus count
    	System.out.println(">> Char-Comparisons: " + comp);
    	System.out.println(">> Positions: " + printPositions(firstTenHits)); // ersten 10 Positionen
    }

    private String printPositions(int[] positions)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(positions[0]);
        for (int i=1; i<positions.length; ++i)
        {
            sb.append(", ").append(positions[i]);
        }

        return sb.toString();
    }


    // "bad character matching"
    private int[] getSkipTable (byte[] pattern, int patternLength) {
    	final int ALPHABET_SIZE = 256;
    	int[] table = new int[ALPHABET_SIZE];
    	
    	for (int i = 0; i < table.length; ++i) {
    		table[i] = patternLength;
    	}
    	
    	for (int i = 0; i < patternLength; ++i) {
    		table[pattern[i]] = patternLength - 1 - i;
    	}
    	
    	// testing
//    	for (int i = 0; i < ALPHABET_SIZE; ++i) {
//    		System.out.println(i + "|" + table[i]);
//    	}
    	//
    	
    	return table;
    }
    
    // "good character matching"
    private int[] getNextTable (byte[] pattern, int patternLength) {
    	int[] table = new int[patternLength];
    	int lastPrefixPosition = patternLength;
    	
    	for (int i = patternLength - 1; i >= 0; --i) {
    		if (isPrefix(pattern, patternLength, i + 1)) {
    			lastPrefixPosition = i + 1;
    		}
    		table[patternLength - 1 - i] = lastPrefixPosition - i + patternLength - 1;
    	}
    	
    	for (int i = 0; i < patternLength - 1; ++i) {
    		int sLen = suffixLength(pattern, patternLength, i);
    		table[sLen] = patternLength - 1 - i + sLen;
    	}
    	
    	// testing
//    	for (int i = 0; i < table.length; ++i) {
//    		System.out.println(i + "|" + table[i]);
//    	}
    	//
    	
    	return table;
    }
    
    private boolean isPrefix(byte[] pattern, int patternLength, int position) {
    	for (int i = position, j = 0; i < patternLength; ++i, ++j) {
    		if (pattern[i] != pattern[j]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    private int suffixLength(byte[] pattern, int patternLength, int position) {
    	int length = 0;
    	for (int i = position, j = patternLength - 1; i >= 0 && pattern[i] == pattern[j]; --i, --j) {
    		length += 1;
    	}
    	return length;
    }
}
