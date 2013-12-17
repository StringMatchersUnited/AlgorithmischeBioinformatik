package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.Bucket;
import de.huberlin.algobio.ws1314.gruppe2.tools.BucketIndexPair;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;
import java.util.ArrayList;
import java.util.List;


public class SuffixArray
{
    private int[] suffix;
    private List<Bucket> buckets;
    private int[] inBucket;
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
        suffix = new int[sLength];
        buckets = new ArrayList<Bucket>();
        inBucket = new int[sLength];
        
        for ( int i = 1; i < sLength; i++ )
        {
            suffix[i] = i - 1;
        }
        suffix[0] = tLength;
        inBucket[sLength - 1] = -1;
        
        initialSort();
        
        curPos = 1;
        while ((buckets.size() + 1) < sLength) {
        	System.out.println(buckets.size());
        	sort(curPos);
        	curPos = curPos * 2;
        }
    }
    
    private void initialSort() {
    	List<List<Integer>> initialBuckets = new ArrayList<List<Integer>>();
    	int suffixIndex = 1; // leeres Zeichen ganz vorn, fällt weg
    	int counter = -1;
    	int cntSuffix = 1;
    	
    	for ( int i = 0; i < sizeOfAlph; i++ )
    	{
    		initialBuckets.add( new ArrayList<Integer>() );
    	}
    	
    	for ( int i = 0; i < tLength; i++ )
        { // leeres Suffix "$" bleibt ganz vorn | + 1 weil er das letzte Zeichen vergisst? (falsche tLength)
    		initialBuckets.get( template[i] ).add( suffix[i + 1] );
        }
    	
    	// Auf das suffix-Array zeigen
    	for (int i = 0; i < initialBuckets.size(); i++) {
    		if (initialBuckets.get(i).size() > 0) {
    			counter++;
    			buckets.add(new Bucket(cntSuffix));
    		}
    		for (int j = 0; j < initialBuckets.get(i).size(); j++) {
    			suffix[cntSuffix] = initialBuckets.get(i).get(j);
    			buckets.get(counter).add(initialBuckets.get(i).get(j));
    			inBucket[initialBuckets.get(i).get(j)] = counter;
    			cntSuffix++;
    		}
    	}
    }
    
    private void sort(Integer nextPos) {
    	int[] sortedSuffix = new int[sLength];
    	int cnt = -1;
    	
    	for (int i = 0; i < sLength; i++) {
    		if (suffix[i] - nextPos > -1) {
    			// Buckets unterteilen (während des Sortierens?)
    			// Bucket hat Pointer für zukünftige Buckets? (index merken)
    			// merken, welcher Bucket gerade ist: Bucket hat letzten Bucket als int (init: -2)
    			// -> int anders (größer) als vermerkt -> neuer pointer für zukünftigen Bucket
    			buckets.get(inBucket[suffix[i]-nextPos]).setLastBucket(inBucket[suffix[i]]);
    			
    			// Buckets sortieren, suffix als referenz
    			buckets.get(inBucket[suffix[i]-nextPos]).moveToPointer(suffix[i]-nextPos); // buckets sortiert
    			
    			// Buckets in Suffix-Array
    			sortedSuffix[buckets.get(inBucket[suffix[i]-nextPos]).getStart() + buckets.get(inBucket[suffix[i]-nextPos]).getPointer() - 1] = buckets.get(inBucket[suffix[i]-nextPos]).getIndex((buckets.get(inBucket[suffix[i]-nextPos]).getPointer() - 1));
    		}
    	}
    	
    	
    	// Buckets aufsplitten
    	for (int i = 0; i < buckets.size(); i++) {
			while (buckets.get(i).willBeSplitted() > 1) {
				buckets.add(i + 1, new Bucket(buckets.get(i).getStart() + buckets.get(i).getLastNextBucket()));
				cnt++;
				while (buckets.get(i).getLastNextBucket() < buckets.get(i).getLength()) {
					buckets.get(i + 1).add(0, buckets.get(i).removeLast());
					inBucket[buckets.get(i + 1).getIndex(0)] = inBucket[buckets.get(i + 1).getIndex(0)] + buckets.get(i).willBeSplitted() - 1 + cnt;
				}
				buckets.get(i).removeLastNextBucket();
			}
			
			for (int j = 0; j < buckets.get(i).getLength(); j++) {
				inBucket[buckets.get(i).getIndex(j)] = i;
				buckets.get(i).resetPointer();
			}
    	}
    	
    	suffix = null;
    	suffix = sortedSuffix;
    	sortedSuffix = null;
    }
    
    public void search(byte[] p) {
        double expectedHits = Tools.calcExpectation( p );

        ArrayList<Integer> results = new ArrayList<Integer>();
        int l = 0;
        int r = sLength - 1;
        int m = r / 2;
        int lp = 0;
        int rp = 0;
        int cur;
        boolean finish = false;
    	
        while (l <= r) {
        	cur = suffix[m];
        	
        	for(int i = Math.min(lp, rp); i < p.length; i++)
			{
        		if(p[i] < template[cur + i])
        		{
        			r = m - 1;
        			rp = i;
        			break;
        		}
        		else if(p[i] > template[cur + i])
        		{
        			l = m + 1;
        			lp = i;
        			break;
        		}
        		else if(i == p.length - 1)
        		{
					for(int j = i - 1; j >= 0; j--)
					{
						cur = suffix[j];

						if(cur + p.length - 1 > tLength) break;

						for(int k = 0; k < p.length; k++)
						{
							if(template[cur + k] != p[k]) break;
							else if(k == p.length - 1)
							{
								results.add(cur);
							}
						}
					}
					
					for(int j = i + 1; j < tLength - p.length; j++)
					{
						cur = suffix[j];

						if(cur + p.length - 1 > tLength) break;

						for(int k = 0; k < p.length; k++)
						{
							if(template[cur + k] != p[k]) break;
							else if(k == p.length - 1)
							{
								results.add(cur);
							}
						}
					}
					finish = true;
					break;
        		}
			}
        	
        	if (finish) {
        		break;
        	}
        	m = l + (r - l) / 2;
        }
    	
    	int ftp_len = Math.min( 10, results.size() );
        int[] firstTenPositions = new int[ftp_len];
        for ( int i = 0; i < ftp_len; ++i )
        {
            firstTenPositions[i] = results.get( i );
        }
    	
    	System.out.println( "> " + Tools.byteArrayToString( p ) );
        System.out.println( ">> Length: " + p.length );
        System.out.println( ">> Occurrences: " + results.size() );
        System.out.println( ">> Positions: " + Tools.printPositions( firstTenPositions ) );
        System.out.println();
    }
}
