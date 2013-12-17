package de.huberlin.algobio.ws1314.gruppe2.tools;

import java.util.ArrayList;
import java.util.List;

public class Bucket
{
    private int pointer;
    private int lastBucket;
    private int startInSuffixList;
    
    private List<Integer> suffixes;
    private List<Integer> nextBuckets;
    
    public Bucket() {
    	pointer = 0;
    	lastBucket = -2;
    	suffixes = new ArrayList<Integer>();
    	nextBuckets = new ArrayList<Integer>();
    }
    
    public Bucket(int start) {
    	pointer = 0;
    	lastBucket = -2;
    	suffixes = new ArrayList<Integer>();
    	nextBuckets = new ArrayList<Integer>();
    	startInSuffixList = start;
    }
    
    public void add(int suffixIndex) {
    	suffixes.add(suffixIndex);
    }
    
    public void add(int index, int suffixIndex) {
    	suffixes.add(index, suffixIndex);
    }
    
    public int getStart() {
    	return startInSuffixList;
    }
    
    public void addNextBucket(int sIndex) {
    	nextBuckets.add(sIndex);
    }
    
    public int getLength() {
    	return suffixes.size();
    }
    
    public int getIndex(int index) {
    	return suffixes.get(index);
    }
    
    public int getPointer() {
    	return pointer;
    }
    
    public int getLastBucket() {
    	return lastBucket;
    }
    
    public void setLastBucket(int indexLastBucket) {
    	if (lastBucket != indexLastBucket) {
    		nextBuckets.add(pointer);
    		lastBucket = indexLastBucket;
    	}
    }
    
    public int getLastNextBucket() {
    	return nextBuckets.get(nextBuckets.size() - 1);
    }
    
    public int removeLast() {
    	int index = suffixes.get(suffixes.size() - 1);
    	suffixes.remove(suffixes.size() - 1);
    	return index;
    }
    
    public void removeLastNextBucket() {
    	nextBuckets.remove(nextBuckets.size() - 1);
    }
    
    public void moveToPointer(int index) {
    	int from = suffixes.indexOf(index);
    	suffixes.add(pointer, index);
    	suffixes.remove(from + 1);
    	pointer++;
    }

    public void incPointer() {
    	pointer++;
    }
    
    public void resetPointer() {
    	pointer = 0;
    }
    
    public int willBeSplitted() {
    	return nextBuckets.size();
    }
    
    public void getNextBuckets() {
    	System.out.println("nextBuckets:");
    	for (int i = 0; i < nextBuckets.size(); i++) {
    		System.out.println(nextBuckets.get(i));
    	}
    	System.out.println("---");
    }
}
