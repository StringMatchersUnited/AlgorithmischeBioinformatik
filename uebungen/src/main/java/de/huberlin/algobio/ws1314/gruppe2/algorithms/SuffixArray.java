package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class SuffixArray
{
	private int[] aSortedSuffix;
	private int[] aSuffixInBucket;
	private int[] aSuffixInBucket2;
	private int[] aBuckets;
	
    private byte[] template;
    private int tLength;
    private int sLength;
    private final int sizeOfAlph = 256;

    public SuffixArray( byte[] pTemplate )
    {
    	Integer curPos = 0;
    	
        template = pTemplate;
        tLength = pTemplate.length;
        sLength = tLength + 1;
        
        aSortedSuffix = new int[sLength];
        aSuffixInBucket = new int[sLength];
        aSuffixInBucket2 = new int[sLength];
        aBuckets = new int[sLength];
        
        aSortedSuffix[0] = tLength;
        for (int i = 1; i < sLength; i++) {
        	aSortedSuffix[i] = i - 1;
        }
        
        sort();
    }
    
    private void sort() {
    	List<List<Integer>> buckets = new ArrayList<List<Integer>>();
    	int suffixIndex = 1;
    	int bucketSize; // hilfsint für BucketSize
    	
    	for ( int i = 0; i < sizeOfAlph; i++ )
    	{
    		buckets.add( new ArrayList<Integer>() );
    	}
    	
    	for ( int i = 0; i < tLength; i++ )
        {
            buckets.get( template[i] ).add( aSortedSuffix[i + 1] );
        }
    	
    	aSortedSuffix[0] = tLength; // leeres Zeichen
    	
    	// initialisieren mit -1, leeres Zeichen drin [0] = 0
    	for (int i = 1; i < sLength; i++) {
    		aBuckets[i] = -1;
    	}
    	
    	int bucket = 0;
    	int bucketIndex;
    	for (int i = 0; i < buckets.size(); i++) {
    		if (buckets.get(i).size() > 0) {
    			bucketIndex = suffixIndex;
    			aBuckets[bucketIndex] = 0;
    			bucket++;
	    		for (int j = 0; j < buckets.get(i).size(); j++) {
	    			aSortedSuffix[suffixIndex] = buckets.get(i).get(j);
	    			aSuffixInBucket[buckets.get(i).get(j)] = bucket;
	    			aSuffixInBucket2[buckets.get(i).get(j)] = bucketIndex;
	    			suffixIndex++;
	    		}
    		}
    	}
    	bucketSize = bucket + 1;
    	
    	// rest-sort
    	
    	int nPos = 1;
    	int[] aSortedSuffix2 = new int[sLength]; // hilfsarray
    	
    	int cnt = 0;
    	int[] aRef;
    	int[] aSorted = aSortedSuffix2;
    	LinkedList<Integer> lHittedBuckets = new LinkedList<Integer>();
		while (bucketSize < sLength) {
    		cnt++;
    		
    		bucketIndex = 0;
    		
    		if (cnt % 2 == 0) {
    			aRef = aSortedSuffix2;
    			aSorted = aSortedSuffix;
    		} else {
    			aRef = aSortedSuffix;
    			aSorted = aSortedSuffix2;
    		}
    		
    		// kopieren
    		for (int j = 0; j < sLength; j++) {
    			aSorted[j] = aRef[j];
    		}
    		
    		for (int i = 0; i < sLength; i++) {
    			if (aBuckets[i] > -1) { // wenn bucket - ende
    				// neue Buckets
    				int k = 0;
					while (lHittedBuckets.size() > 0) {
						if (sLength > aBuckets[lHittedBuckets.get(k)] + lHittedBuckets.get(k)) {
							if (aBuckets[aBuckets[lHittedBuckets.get(k)] + lHittedBuckets.get(k)] == - 1 ) {
								aBuckets[aBuckets[lHittedBuckets.get(k)] + lHittedBuckets.get(k)] = -2;
							}
						}
						lHittedBuckets.remove(k);
					}
    				bucketIndex = i;
    			}
    			// sortieren
    			if ( (aRef[i] - nPos) > - 1 ) {
    				aSorted[aSuffixInBucket2[aRef[i] - nPos] + aBuckets[aSuffixInBucket2[aRef[i] - nPos]]] = aRef[i] - nPos;
					aBuckets[aSuffixInBucket2[aRef[i] - nPos]]++;
					if (!lHittedBuckets.contains(aSuffixInBucket2[aRef[i] - nPos])) {
						lHittedBuckets.add(aSuffixInBucket2[aRef[i] - nPos]);
					}
    			}
    		}
    		
    		int lastBucket = 0;
    		bucketSize = 0;
    		for (int i = 0; i < sLength; i++) {
    			if (aBuckets[i] != -1) {
    				aBuckets[i] = 0;
    				lastBucket = i;
    				bucketSize++;
    			}
    			aSuffixInBucket2[aSorted[i]] = lastBucket;
    		}
    		
    		nPos = nPos * 2;
    	}
		aSortedSuffix = aSorted;
    }
    
    public void search(byte[] pattern) {
    	int l = 1;
    	int r = tLength;
    	int m = 0;
    	int f;
    	LinkedList<Integer> lHits = new LinkedList<Integer>();
    	int[] aFirstTen = new int[10];
    	
    	for (int i = 0; i < aFirstTen.length; i++) {
    		aFirstTen[i] = -1;
    	}
    	
    	int index = 0;
    	while (l < r) {
    		m = l + ((r - l) / 2);
    		f = searchFunction(pattern, m);
    		if (f > 0) { // go up
    			r = m - 1;
    		} else if (f < 0) { // go down
    			l = m + 1;
    		} else { // occurence
    			int n = m;
    			while (searchFunction(pattern, n) == 0) {
    				index = 0;
    				for (int i = 0; ( (i < lHits.size()) && i < 10); i++) {
    					if (lHits.get(i) < aSortedSuffix[n]) {
    						index++;
    					} else {
    						break;
    					}
    				}
    				lHits.add(index, aSortedSuffix[n]);
    				n--;
    			}
    			n = m + 1;
    			while ((searchFunction(pattern, n) == 0)) {
    				index = 0;
    				for (int i = 0; ( (i < lHits.size()) && i < 10); i++) {
    					if (lHits.get(i) < aSortedSuffix[n]) {
    						index++;
    					} else {
    						break;
    					}
    				}
    				lHits.add(index, aSortedSuffix[n]);
    				n++;
    			}
    			break;
    		}
    	}
    	
    	for (int i = 0; ( (i < lHits.size()) && i < 10); i++) {
    		aFirstTen[i] = lHits.get(i);
    	}
    	
    	System.out.println( "> " + Tools.byteArrayToString( pattern ) );
        System.out.println( ">> Length: " + pattern.length );
        System.out.println( ">> Occurrences: " + lHits.size() );
        System.out.println( ">> Positions: " + Tools.printPositions( aFirstTen ) );
        System.out.println();
    }
    
    private int searchFunction(byte[] pattern, int index) {
    	// - 1: kleiner als Pattern | 1: größer als pattern | 0: treffer 
    	int returnVal = 0;
    	if (index > sLength - 1) {
    		returnVal = -1;
    	} else if (aSortedSuffix[index] + pattern.length > sLength - 1) {
    		returnVal = -1;
    	} else {
    		for (int i = 0; i < pattern.length; i++) {
    			if (template[aSortedSuffix[index] + i] != pattern[i]) {
    				if (template[aSortedSuffix[index] + i] < pattern[i]) {
    					returnVal = -1;
    				} else {
    					returnVal = 1;
    				}
    				break;
    			}
    		}
    	}
    	return returnVal;
    }
}
