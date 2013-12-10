package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import java.util.ArrayList;
import java.util.List;

public class SuffixArray
{
    private int[] suffix;
    private byte[] template;
    private int tLength;
    private final int sizeOfAlph = 256;

    public SuffixArray(byte[] pTemplate, int pTemplateLength)
    {
    	template = pTemplate;
    	tLength = pTemplateLength;
    	System.out.println(pTemplateLength);
        suffix = new int[tLength + 1]; // leere Suffix "$"
        for (int i = 1; i < tLength + 1; i++) {
        	suffix[i] = i - 1;
        	System.out.println(suffix[i] + "---" + pTemplate[i - 1]);
        }
        
        suffixArraySort();
    }
    
    private void suffixArraySort() {
		// bucketSort
    	// momentan zu viele Listeneinträge... besserer Einfall?
		List<List<Integer>> buckets = new ArrayList<List<Integer>>();
		
		for (int i = 0; i < sizeOfAlph; i++) {
			buckets.add(new ArrayList<Integer>());
		}
		
		for (int i = 0; i < tLength; i++) { // leeres Suffix "$" bleibt ganz vorn | + 1 weil er das letzte Zeichen vergisst? (falsche tLength)
			buckets.get(template[i]).add(suffix[i + 1]);
			System.out.println("..." + template[i]);
		}
		
		for (int i = 0; i < sizeOfAlph; i++) {
			bucketSort(buckets.get(i), 1);
		}
	}
    
    private void bucketSort(List<Integer> indizes, int nextPos) {
    	int endPos = 2 * nextPos - 1;
    	
    	// indizes.get(i) - nextPos im Bucket nach vorn schieben
    	
    	if (indizes.size() < 2) {
    		return;
    	} else {
    		for (int i = 0; i < indizes.size(); i++) {
    			for (int j = nextPos; j <= endPos; j++) {
    				System.out.println("pos:" + indizes.get(i) + "|" + template[indizes.get(i)] + "next:" + template[indizes.get(i) + j]);
    			}
    			// sortieren
    			//new bucketsort
    		}
    	}
    }
}
