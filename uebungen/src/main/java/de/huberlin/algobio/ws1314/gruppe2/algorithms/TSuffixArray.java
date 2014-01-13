package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class TSuffixArray
{
	private int[] aSortedSuffix;
	private int[] aSuffixInBucket;
	private LinkedList<int[]> lBuckets; 
	
    private List<Integer> suffix;
    private List<Integer> pointer;
    private byte[] template;
    private int tLength;
    private int sLength;
    private final int sizeOfAlph = 256;

    public TSuffixArray( byte[] pTemplate )
    {
    	Integer curPos = 0;
    	
        template = pTemplate;
        tLength = pTemplate.length;
        sLength = tLength + 1;
        suffix = new ArrayList<Integer>();
        pointer = new ArrayList<Integer>();
        
        aSortedSuffix = new int[sLength];
        aSuffixInBucket = new int[sLength];
        lBuckets = new LinkedList<int[]>();
        
        aSortedSuffix[0] = tLength;
        for (int i = 1; i < sLength; i++) {
        	aSortedSuffix[i] = i - 1;
        }
        
        sort();
    }
    
    private void sort() {
    	List<List<Integer>> buckets = new ArrayList<List<Integer>>();
    	int suffixIndex = 1;
    	
    	for ( int i = 0; i < sizeOfAlph; i++ )
    	{
    		buckets.add( new ArrayList<Integer>() );
    	}
    	
    	for ( int i = 0; i < tLength; i++ )
        {
            buckets.get( template[i] ).add( aSortedSuffix[i + 1] );
        }
    	
    	lBuckets.add(new int[] {0, 0}); // leeres Zeichen
    	aSortedSuffix[0] = tLength; // leeres Zeichen
    	
    	int bucket = 0;
    	for (int i = 0; i < buckets.size(); i++) {
    		if (buckets.get(i).size() > 0) {
    			lBuckets.add(new int[] {suffixIndex, 0});
    			bucket++;
	    		for (int j = 0; j < buckets.get(i).size(); j++) {
	    			aSortedSuffix[suffixIndex] = buckets.get(i).get(j);
	    			aSuffixInBucket[buckets.get(i).get(j)] = bucket;
	    			suffixIndex++;
	    		}
    		}
    	}
    	
    	System.out.println("initialSort done");
    	
    	for (int i = 0; i < lBuckets.size(); i++) {
    		System.out.println(lBuckets.get(i)[0] + "." + lBuckets.get(i)[1]);
    		System.out.println(aSuffixInBucket[i]);
    	}
    	
    	// rest-sort
    	
    	int nPos = 1;
    	int[] aSortedSuffix2 = new int[sLength]; // hilfsarray
//    	LinkedList<int[]> lNewBuckets = (LinkedList<int[]>) lBuckets.clone(); // hilfsliste
    	
    	int cnt = 0;
    	int bucketSize; // hilfsint f�r anfangsBucketSize
    	int[] aRef;
    	int[] aSorted;
    	LinkedList<Integer> lHittedBuckets = new LinkedList<Integer>();
    	while (lBuckets.size() < sLength) {
    		cnt++;
    		bucketSize = lBuckets.size();
    		
//    		System.out.println("nextPos: " + nPos);
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
//    		System.out.println("aRef: " + aRef);
//    		System.out.println("aSorted: " + aSorted);^
    		
//    		System.out.println(sLength);
    		
    		int i = 0; // elemente in suffixarray
    		while (i < sLength) {
    			System.out.println("sortieren und neue Buckets");
    			for (int j = 0; j < bucketSize; j++) {
					if ((lBuckets.get(j)[0] + 1 < sLength) || (lBuckets.get(j)[0] + 1 != lBuckets.get(j + 1)[0])) { // bucket gr��er als 1 element
						// sortieren, pointer erh�hen
//						System.out.println("sortieren");
						int limit;
						if ((j + 1) == bucketSize) {
							limit = sLength;
						} else {
							limit = lBuckets.get(j + 1)[0];
						}
						
						for (int k = lBuckets.get(j)[0]; k < limit; k++) {
							if ((aRef[k] - nPos) > -1) {
//    			            	System.out.println("...");
//								System.out.println(k + "." + j + "." + (aRef[k] - nPos));
//    			            	System.out.println(aSuffixInBucket[aRef[i] - nPos]);
//    			            	System.out.println(lBuckets.get(aSuffixInBucket[aRef[i] - nPos] - 1)[0]);
//    			            	System.out.println(aRef[i] - nPos);
								aSorted[lBuckets.get(aSuffixInBucket[aRef[k] - nPos])[0] + lBuckets.get(aSuffixInBucket[aRef[k] - nPos])[1]] = aRef[k] - nPos;
								lBuckets.get(aSuffixInBucket[aRef[k] - nPos])[1]++;
								if (!lHittedBuckets.contains(aSuffixInBucket[aRef[k] - nPos])) {
									lHittedBuckets.add(aSuffixInBucket[aRef[k] - nPos]);
								}
							}
						}
						i = limit;
						
//						System.out.println("neue Buckets");
						
						int k = 0;
						while (lHittedBuckets.size() > 0) {
//							System.out.println(lHittedBuckets.get(k));
							if (lBuckets.get(lHittedBuckets.get(k))[0] + lBuckets.get(lHittedBuckets.get(k))[1] < sLength) { // pointer �berpr�fen nicht notwendig, ist zwansgl�ufig gr��er als 0
								if (lHittedBuckets.get(k) == lBuckets.size() - 1) {
									if (sLength != lBuckets.get(lHittedBuckets.get(k))[0] + lBuckets.get(lHittedBuckets.get(k))[1]) {
										lBuckets.add(new int[] {lBuckets.get(lHittedBuckets.get(k))[0] + lBuckets.get(lHittedBuckets.get(k))[1], lHittedBuckets.get(k)});
									}
								} else {
									if (lBuckets.get(lHittedBuckets.get(k) + 1)[0] != lBuckets.get(lHittedBuckets.get(k))[0] + lBuckets.get(lHittedBuckets.get(k))[1]) {
										lBuckets.add(new int[] {lBuckets.get(lHittedBuckets.get(k))[0] + lBuckets.get(lHittedBuckets.get(k))[1], lHittedBuckets.get(k)});
									}
								}
							}
							lHittedBuckets.remove(0);
						}
						
						// extrem langsam
						// liste mit betroffen Buckets
//			            int n;
//			            boolean exists;
//			            for (int k = 0; k < bucketSize; k++) {
//    						if ((lBuckets.get(k)[1] > 0) && (lBuckets.get(k)[0] + lBuckets.get(k)[1] < sLength)) { // keine neuen Buckets (-1) und keine Buckets in denen noch nichts passiert ist (0)
//    							exists = false;
//    							for (int l = 0; l < lBuckets.size(); l++) {
//    								if ((lBuckets.get(l)[0] == lBuckets.get(k)[0] + lBuckets.get(k)[1])) {
//    									exists = true;
//    									break;
//    								}
//    							}
//    							if (!exists) {
//    								lBuckets.add(new int[] {lBuckets.get(k)[0] + lBuckets.get(k)[1], k});
//    							}
//    						}
//			            }
					} else { // bucket nur 1 gro�
						i++;
					}
    			}
    		}
    		System.out.println("bucket sortieren");
    		
    		// buckets-reihenfolge sortieren
    		int[] splittedBuckets = new int[lBuckets.size() - bucketSize];
    		int[] moved;
    		// vorher auf 0 setzen
    		// aRef nutzen und aRef[x] um 1 erh�hen
    		// danach durchlauf und alles aufaddieren f�r jedes x
    		// splittedBuckets vorher sortieren?
    		int offset;
    		for (int k = 0; k < lBuckets.size() - bucketSize; k++) {
    			offset = 0;
    			moved = lBuckets.remove(k + bucketSize);
    			for (int l = 0; l < k; l++) {
    				if (splittedBuckets[l] < moved[1] + 1) {
    					offset++;
    				}
    			}
    			lBuckets.add(moved[1] + offset + 1, moved);
    			splittedBuckets[k] = moved[1]; 
    		}
    		System.out.println("RefBucket, BucketSize: " + lBuckets.size());
    		
    		// dauert, zu viele Zugriffe auf Array/Liste?
    		// nutzen des offsets aus bucket sortieren?
    		// neu in welchem bucket
    		int index;
    		for (int k = 1; k < lBuckets.size() - 1; k++) {
    			lBuckets.get(k)[1] = 0; // pointer zur�ck
    			index = lBuckets.get(k)[0];
    			if (k % 100 == 0) {
    				System.out.println(k);
    			}
    			for (int l = 0; l < lBuckets.get(k + 1)[0] - index; l++) {
    				aSuffixInBucket[aSorted[index + l]] = k;
    			}
    		}
    		
    		// f�r den letzten extra
    		lBuckets.getLast()[1] = 0; // pointer zur�ck
    		index = lBuckets.getLast()[0];
    		for (int l = 0; l < sLength - index; l++) {
				aSuffixInBucket[aSorted[index + l]] = lBuckets.size() - 1;
			}
    		
//    		// pointer zur�cksetzen
//    		for (int k = 0; k < lBuckets.size(); k++) {
//    			lBuckets.get(k)[1] = 0;
//    		}
    		
    		nPos = nPos * 2;
    		
    		System.out.println(nPos + " Zeichen");
    		
//    		for (int k = 0; k < sLength; k++) {
//    			System.out.println(aSorted[k]);
//    		}
//    		
//    		System.out.println("--");
//    		
//    		for (int k = 0; k < sLength; k++) {
//    			System.out.println(aSuffixInBucket[k]);
//    		}
//    		
//    		System.out.println("--");
//    		
//    		for (int k = 0; k < lBuckets.size(); k++) {
//    			System.out.println(lBuckets.get(k)[0] + "." + lBuckets.get(k)[1]);
//    		}
    		
    		// 
    		
//    		for (int i = 0; i < lBuckets.size(); i++) {
//    			// pr�fen ob erster wert von i == i+1 w�re
//    			lBuckets.add(i + 1, new int[] {0,0});
//    			i++;
//    		}
//    		
//    		for (int i = 0; i < lBuckets.size(); i++) {
//        		System.out.println(lBuckets.get(i)[0] + "." + lBuckets.get(i)[1]);
//    		}
    	}
    }
    
    private void sort(Integer nextPos) {
    	int indexOfBucket;
    	int indexToMove;
    	int counter;
    	int offset;
    	List<Integer> pointerInBucket = new ArrayList<Integer>();
    	
    	for (int i = 0; i < pointer.size(); i++) {
    		pointerInBucket.add(pointer.get(i));
    	}
    	
    	
    	for ( int i = 0; i < sLength; i++ )
        {
            indexOfBucket = getBucketIndex((suffix.get(i) - nextPos));
            indexToMove = getSuffixIndex((suffix.get(i) - nextPos), indexOfBucket);
            // umsortieren
            if ((indexToMove > -1) && (indexOfBucket > -1)) {
            	if (indexToMove != pointerInBucket.get(indexOfBucket)) {
            		suffix.add(pointerInBucket.get(indexOfBucket), suffix.get(indexToMove));
            		suffix.remove(indexToMove + 1);
            	}
            	pointerInBucket.set(indexOfBucket, (pointerInBucket.get(indexOfBucket) + 1));
            }
        }
    	
    	// pointer erweitern
    	// von hinten nach vorn
    	// f�r den letzten
    	counter = 1;
		while (pointer.get(pointer.size() - 1) + counter < sLength) {
			if (isDifferent((pointer.get(pointer.size() - 1)), (pointer.get(pointer.size() - 1) + counter), nextPos)) {
				pointer.add(pointer.size(), (pointer.get(pointer.size() - 1) + counter));
			} else {
				counter++;
			}
		}
		
    	for (int i = pointer.size() - 2; i > - 1; i--) {
    		if (pointer.get(i + 1) - pointer.get(i) == 1) continue;
    		counter = 1;
    		offset = 0;
    		while (pointer.get(i + offset) + counter < pointer.get(i + 1 + offset)) {
    			if (isDifferent((pointer.get(i + offset)), (pointer.get(i + offset) + counter), nextPos)) {
    				offset++;
    				pointer.add(i + offset, (pointer.get(i + offset - 1) + counter));
    			} else {
    				counter++;
    			}
    		}
    	}
    }
    
    private boolean isDifferent(int pIndex, int newPIndex, int nextPos) {
    	boolean diff = false;
//    	System.out.println(pIndex + ".." + suffix.get(pIndex) + ".." + newPIndex + ".." + suffix.get(newPIndex));
    	if ((suffix.get(newPIndex) + nextPos < tLength - 1) && (suffix.get(pIndex) + nextPos < tLength - 1)) {
    		if (template[suffix.get(pIndex) + nextPos] != template[suffix.get(newPIndex) + nextPos]) {
    			diff = true;
//    			System.out.println(template[suffix.get(pIndex) + nextPos] + "!=" + template[suffix.get(newPIndex) + nextPos]);
    		}
    	} else {
    		diff = true;
    	}
    	return diff;
    }
    
    private int getBucketIndex(int tIndex) {
    	int bucketIndex = -1;
    	
    	if ((tIndex > -1) && (tIndex < tLength)) {
    		// basis von pointern nach anfangsbuchstabe suchen
    		
    		for (int i = 0; i < pointer.size(); i++) {
//    			System.out.println(tIndex + ".." + template[tIndex] + ".." + template[suffix.get(pointer.get(i))]);
    			if (template[suffix.get(pointer.get(i))] == template[tIndex]) {
    				bucketIndex = i;
    				break;
    			}
    		}
    		
//    		System.out.println("pointer:" + bucketIndex);
    	}
    	return bucketIndex;
    }
    
    private int getSuffixIndex(int tIndex, int bucketIndex) {
    	int suffixIndex = -1;
//    	System.out.println(tIndex + ".." + bucketIndex);
    	
    	if ((tIndex > -1) && (bucketIndex > -1)) {
    		
    		// dann konkret suchen
    		for (int i = pointer.get(bucketIndex); i < sLength; i++) {
    			// BIN search
//    			System.out.println(sIndex + ".." + pointer.get(bucketPointer) + ".." + suffix.get(i));
    			if (suffix.get(i) == tIndex) {
    				suffixIndex = i;
    				break;
    			}
    		}
    		
//    		System.out.println("sIndex:" + suffixIndex);
    		
    	}
    	return suffixIndex;
    }
    
    private void clearSuffixList() {    	
    	suffix.clear();
    	suffix.add(tLength);
    }
    
    private void sysoSL() {
    	System.out.println("suffix:");
    	for (int i = 0; i < suffix.size(); i++) {
    		System.out.println("i:" + i + "|val:" + suffix.get(i));
    	}
    }
    
    private void sysoPL() {
    	System.out.println("pointer:");
    	for (int i = 0; i < pointer.size(); i++) {
    		System.out.println("i:" + i + "|val:" + pointer.get(i) + "sign" + suffix.get(pointer.get(i)));
    	}
    }
    

//    private void suffixArraySort()
//    {
//        // bucketSort
//        // momentan zu viele Listeneintr�ge... besserer Einfall?
//        List<List<Integer>> buckets = new ArrayList<List<Integer>>();
//
//        for ( int i = 0; i < sizeOfAlph; i++ )
//        {
//            buckets.add( new ArrayList<Integer>() );
//        }
//
//        for ( int i = 0; i < tLength; i++ )
//        { // leeres Suffix "$" bleibt ganz vorn | + 1 weil er das letzte Zeichen vergisst? (falsche tLength)
//            buckets.get( template[i] ).add( suffix[i + 1] );
//            System.out.println( "..." + template[i] );
//        }
//
//        for ( int i = 0; i < sizeOfAlph; i++ )
//        {
//            bucketSort( buckets.get( i ), 1 );
//        }
//    }
//
//    private void bucketSort( List<Integer> indizes, int nextPos )
//    {
//        int endPos = 2 * nextPos - 1;
//
//        // indizes.get(i) - nextPos im Bucket nach vorn schieben
//
//        if ( indizes.size() < 2 )
//        {
//            return;
//        } else
//        {
//            for ( int i = 0; i < indizes.size(); i++ )
//            {
//                for ( int j = nextPos; j <= endPos; j++ )
//                {
//                    System.out.println( "pos:" + indizes.get( i ) + "|" + template[indizes.get( i )] + "last:" + template[indizes.get( i ) - j] );
//                }
//                // sortieren
//                //new bucketsort
//            }
//        }
//    }
}
