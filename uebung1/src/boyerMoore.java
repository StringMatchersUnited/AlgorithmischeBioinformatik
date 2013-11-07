import java.util.Arrays;

public class boyerMoore
{
    public boyerMoore( byte[] patternLong, byte[] text)
    {
        int end = 0;
        for (int i = patternLong.length -1; i>=0; --i)
        {
            if (patternLong[i] == 0)
                end = i;
            else
                break;
        }

        byte[] pattern = Arrays.copyOfRange(patternLong, 0, end);

    	int skipTable[] = getSkipTable(pattern);
    	int nextTable[] = getNextTable(pattern);
    	int hits = 0;
    	
    	for (int i = pattern.length - 1, j; i < text.length;) {
    		for (j = pattern.length - 1; pattern[j] == text[i]; --i, --j) {
    			if (j == 0) {
    				hits++;
                    if (hits <= 10)
    				    System.out.println("Stelle:" + (i + 1) + " bis " + (i + pattern.length));
    				break;
    			}
    		}
    		i += max(nextTable[pattern.length - 1 - j], skipTable[text[i]]);
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
    private int[] getSkipTable (byte[] pattern) {
    	final int ALPHABET_SIZE = 256;
    	int[] table = new int[ALPHABET_SIZE];
    	
    	for (int i = 0; i < table.length; ++i) {
    		table[i] = pattern.length;
    	}
    	
    	for (int i = 0; i < pattern.length; ++i) {
    		table[pattern[i]] = pattern.length - 1 - i;
    	}
    	
    	// testing
//    	for (int i = 0; i < ALPHABET_SIZE; ++i) {
//    		System.out.println(i + "|" + table[i]);
//    	}
    	//
    	
    	return table;
    }
    
    // "good character matching"
    private int[] getNextTable (byte[] pattern) {
    	int[] table = new int[pattern.length];
    	int lastPrefixPosition = pattern.length;
    	
    	for (int i = pattern.length - 1; i >= 0; --i) {
    		if (isPrefix(pattern, i + 1)) {
    			lastPrefixPosition = i + 1;
    		}
    		table[pattern.length - 1 - i] = lastPrefixPosition - i + pattern.length - 1;
    	}
    	
    	for (int i = 0; i < pattern.length - 1; ++i) {
    		int sLen = suffixLength(pattern, i);
    		table[sLen] = pattern.length - 1 - i + sLen;
    	}
    	
    	// testing
//    	for (int i = 0; i < table.length; ++i) {
//    		System.out.println(i + "|" + table[i]);
//    	}
    	//
    	
    	return table;
    }
    
    private boolean isPrefix(byte[] pattern, int position) {
    	for (int i = position, j = 0; i < pattern.length; ++i, ++j) {
    		if (pattern[i] != pattern[j]) {
    			return false;
    		}
    	}
    	return true;
    }
    
    private int suffixLength(byte[] pattern, int position) {
    	int length = 0;
    	for (int i = position, j = pattern.length - 1; i >= 0 && pattern[i] == pattern[j]; --i, --j) {
    		length += 1;
    	}
    	return length;
    }
}
