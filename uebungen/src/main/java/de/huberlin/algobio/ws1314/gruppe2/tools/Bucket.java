package de.huberlin.algobio.ws1314.gruppe2.tools;

import java.util.ArrayList;
import java.util.List;

public class Bucket
{
    private int pointer;
    private List<Integer> suffixes;
    
    public Bucket() {
    	pointer = 0;
    	suffixes = new ArrayList<Integer>();
    }
    
    public void add(int suffixIndex) {
    	suffixes.add(suffixIndex);
    }
    
    public int length() {
    	return suffixes.size();
    }
    
    public int getPointer() {
    	return pointer;
    }
    
    public void incPointer() {
    	pointer++;
    }
}
