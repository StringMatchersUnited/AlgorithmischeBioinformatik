package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class index {

    int q = 3;
    int nextIndex = 0;
    HashMap<String, Integer> qGram = new HashMap<String, Integer>();
    String[] hits = new String[(int) Math.pow(5, q)];
    String[] lastHits = new String[q - 2];

    public index(byte[] template, String indexFileName) {

        for (int i = 0; i < q - 1; i++) {
            lastHitted(template[i]);
        }

        for (int i = q - 1; i < template.length; i++) {
            int index = exist(new Byte(template[i]));
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

    private void lastHitted(Byte hit) {
        for (int i = 0; i < lastHits.length - 1; i++) {
            lastHits[q - 2 - i] = lastHits[q - 3 - i];
        }
        lastHits[0] = hit.toString();
//        for (int i = 0; i < lastHits.length; i++) {            
//            System.out.println(lastHits[i]);
//        }

    }

    private int exist(Byte s) {
        String search = "";
        
        for (int i = lastHits.length - 1; i >= 0; i--) {
            search = search + lastHits[i];
        }
        search = search + s.toString();
        
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
