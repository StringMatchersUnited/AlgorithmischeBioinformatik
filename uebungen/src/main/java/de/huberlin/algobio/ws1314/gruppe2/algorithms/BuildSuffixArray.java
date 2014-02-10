package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class BuildSuffixArray
{
	private int[] aSortedSuffix;
	private int[] aSuffixInBucket;
	private int[] aSuffixInBucket2;
	private int[] aBuckets;
	
    private byte[] template;
    private int tLength;
    private int sLength;
    private final int sizeOfAlph = 256;

    public BuildSuffixArray( byte[] pTemplate )
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
    
    public int[] getSuffixArray() {
    	return aSortedSuffix;
    }
}
