package de.huberlin.algobio.ws1314.gruppe2.algorithms;

public class boyerMoore
{
    public boyerMoore( byte[] pattern, int patternLength, byte[] template, int templateLength)
    {
    	int skipTable[] = getSkipTable(pattern, patternLength);
    	int nextTable[] = getNextTable(pattern, patternLength);
    	int hits = 0;
    	
    	for (int i = patternLength - 1, j; i < templateLength;) {
    		for (j = patternLength - 1; pattern[j] == template[i]; --i, --j) {
    			if (j == 0) {
    				hits++;
                    if (hits <= 10)
    				    System.out.println("Stelle:" + (i + 1) + " bis " + (i + patternLength));
    				break;
    			}
    		}
    		i += max(nextTable[patternLength - 1 - j], skipTable[template[i]]);
    	}
    	
    	System.out.println("Treffer: " + hits);
    }
    
    private int max(int first, int second) {
    	if (first >= second) {
    		return first;
    	} else {
    		return second;
    	}
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
