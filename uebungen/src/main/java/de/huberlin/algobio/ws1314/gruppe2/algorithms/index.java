package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class index {

    int q = 2;
    int nextIndex = 0;
    HashMap<String, Integer> qGram = new HashMap<String, Integer>();
    String[] hits = new String[(int) Math.pow(5, q)];
    char[] lastHits = new char[q - 1];

    public index(byte[] template, int templateLength, String indexFileName) {

        for (int i = 0; i < q - 1; i++) {
            lastHitted(template[i]);
        }

        for (int i = q - 1; i < templateLength; i++) {
            int index = exist(template[i]);
            lastHitted(template[i]);
            hits[index] = hits[index] + ":" + i;
        }
        
        File file = new File(indexFileName);
        FileWriter writer;
        
        try {
            writer = new FileWriter(file);
            for (int i = 0; i < qGram.size(); i++) {
                writer.write(hits[i]);
                writer.write(System.getProperty("line.separator"));
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
          
        }
    }

    private void lastHitted(byte hit) {
        for (int i = lastHits.length - 1; i >= 1; i++) {
            lastHits[i] = lastHits[i - 1];
        }
        lastHits[0] = (char) hit;
//        for (int i = 0; i < lastHits.length; i++) {            
//            System.out.println(lastHits[i]);
//        }

    }

    private int exist(byte s) {
        String search = "";
        
        for (int i = lastHits.length - 1; i >= 0; i--) {
            search = search + lastHits[i];
        }
        search = search + (char) s;
        System.out.println(s + "..." + search);
        
        if (qGram.containsKey(search)) {
            return qGram.get(search);
        } else {
            qGram.put(search, nextIndex);
            hits[nextIndex] = search;
            nextIndex++;
            return qGram.get(search);
        }
    }
}
