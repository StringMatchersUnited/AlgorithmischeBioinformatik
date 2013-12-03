package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;
import java.util.*;
import javax.print.DocFlavor;

public class index {

        int q = 3;
        List<String> qGram = new ArrayList<String>();
        List<String> hits = new ArrayList<String>();
        byte[] lastHits = new byte[q - 2];
        
    public index(byte[] template, String indexFileName) {

        for (int i = 0; i < template.length; i++) {
            if (lastHits[q - 1] != 0) {
                byte[] hit = new byte[i];
            } else {
                lastHitted(template[i]);
            }
        }

    }

    private void lastHitted(byte hit) {
        for (int i = 0; i < lastHits.length - 1; i++) {
            lastHits[q-2-i] = lastHits[q-3-i];
        }
        for (int i = 0; i < lastHits.length; i++) {            
            System.out.println(lastHits[i]);
        }
        
    }
}
