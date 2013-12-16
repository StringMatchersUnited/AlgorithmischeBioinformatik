package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import java.util.ArrayList;
import java.util.List;


public class SuffixArray
{
//    private int[] suffix;
    private List<Integer> suffix;
    private List<Integer> pointer;
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
        suffix = new ArrayList<Integer>();
        pointer = new ArrayList<Integer>();
        
        System.out.println( tLength );
//        suffix = new int[tLength + 1]; // leere Suffix "$"
        clearSuffixList();
        for ( int i = 1; i < sLength; i++ )
        {
            suffix.add(i - 1);
        }
        
//        sysoSL();
        
//        for (int i = 1; i < suffix.size(); i++) {
//        	System.out.println(i + "--" + suffix.get(i) + "--" + pTemplate[suffix.get(i)] );
//        	
//        }

//        suffixArraySort();
        initialSort();
        
        curPos = 1;
//        while ((pointer.size() + 1) != suffix.size()) {
        	System.out.println(pointer.size());
//        	sysoSL();
//        	sysoPL();
        	sort(curPos);
        	curPos = curPos * 2;
//        }
        
//		sysoSL();
//    	sysoPL();
    }
    
    private void initialSort() {
    	List<List<Integer>> buckets = new ArrayList<List<Integer>>();
    	int suffixIndex = 1; // leeres Zeichen ganz vorn, fällt weg
    	
    	for ( int i = 0; i < sizeOfAlph; i++ )
    	{
    		buckets.add( new ArrayList<Integer>() );
    	}
    	
    	for ( int i = 0; i < tLength; i++ )
        { // leeres Suffix "$" bleibt ganz vorn | + 1 weil er das letzte Zeichen vergisst? (falsche tLength)
            buckets.get( template[i] ).add( suffix.get(i + 1) );
        }
    	
    	// suffix-liste neu machen
    	clearSuffixList();
    	
    	// Auf das suffix-Array zeigen
    	for (int i = 0; i < buckets.size(); i++) {
    		for (int j = 0; j < buckets.get(i).size(); j++) {
//    			System.out.println( buckets.get(i).get(j) + ".." + i + ".." + j);
    			suffix.add(buckets.get(i).get(j));
    		}
    		if (buckets.get(i).size() > 0) {
    			pointer.add(suffixIndex);
    			suffixIndex = suffixIndex + buckets.get(i).size();
    		}
    	}
    	
//    	sysoSL();
//    	sysoPL();
    }
    
    private void sort(Integer nextPos) {
    	
    	for (int i = 0; i < sLength; i++) {
    		
    	}
    	
    	
    	
//    	int indexOfBucket;
//    	int indexToMove;
//    	int counter;
//    	int offset;
//    	List<Integer> pointerInBucket = new ArrayList<Integer>();
////    	List<Integer> newPointer = new ArrayList<Integer>();
//    	
//    	for (int i = 0; i < pointer.size(); i++) {
//    		pointerInBucket.add(pointer.get(i));
////    		newPointer.add(pointer.get(i));
//    	}
//    	
//    	
//    	for ( int i = 0; i < sLength; i++ )
//        {
////            System.out.println("suffix.get(i)" + suffix.get(i) + ".." + (suffix.get(i) - 1));
//            indexOfBucket = getBucketIndex((suffix.get(i) - nextPos));
//            indexToMove = getSuffixIndex((suffix.get(i) - nextPos), indexOfBucket);
//            // umsortieren
//            if ((indexToMove > -1) && (indexOfBucket > -1)) {
//            	if (indexToMove != pointerInBucket.get(indexOfBucket)) {
//            		suffix.add(pointerInBucket.get(indexOfBucket), suffix.get(indexToMove));
//            		suffix.remove(indexToMove + 1);
//            	}
//            	pointerInBucket.set(indexOfBucket, (pointerInBucket.get(indexOfBucket) + 1));
//            }
//        }
//    	
////    	sysoSL();
////    	sysoPL();
//    	
//    	// pointer erweitern
//    	// von hinten nach vorn
//    	// für den letzten
//    	counter = 1;
//		while (pointer.get(pointer.size() - 1) + counter < sLength) {
//			if (isDifferent((pointer.get(pointer.size() - 1)), (pointer.get(pointer.size() - 1) + counter), nextPos)) {
//				pointer.add(pointer.size(), (pointer.get(pointer.size() - 1) + counter));
//			} else {
//				counter++;
//			}
//		}
//		
//		// 
//    	for (int i = pointer.size() - 2; i > - 1; i--) {
//    		if (pointer.get(i + 1) - pointer.get(i) == 1) continue;
//    		counter = 1;
//    		offset = 0;
//    		while (pointer.get(i + offset) + counter < pointer.get(i + 1 + offset)) {
//    			// vergleich
//    			if (isDifferent((pointer.get(i + offset)), (pointer.get(i + offset) + counter), nextPos)) {
//    				offset++;
////    				System.out.println(pointer.get(i + offset) + counter);
//    				pointer.add(i + offset, (pointer.get(i + offset - 1) + counter));
//    			} else {
//    				counter++;
//    			}
//    		}
//    	}
    	
//    	sysoSL();
//    	sysoPL();
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
//        // momentan zu viele Listeneintrï¿½ge... besserer Einfall?
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
