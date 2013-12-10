package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.IntArray;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class QGramSearch
{
    public QGramSearch( HashMap<String, IntArray> qGramIndex, byte[] p, int p_len )
    {
        ArrayList<Integer> results;

        String qGram = new String(Arrays.copyOfRange(p, 0, 3));

        if ( !qGramIndex.containsKey(qGram) )
        {
            System.out.println("Pattern qGram not found in qGram index. Stopping the whole search, sorry bro!");
            return;
        }

        // all indexes for the first qGram are potential results
        results = qGramIndex.get(qGram).toArrayList();

        for ( int i = 3; i < p_len - 2; i += 3 )
        {
            System.out.println("i=" + i);
            qGram = new String(Arrays.copyOfRange(p, i, i + 3));

            if ( !qGramIndex.containsKey(qGram) )
            {
                System.out.println("Pattern qGram not found in qGram index. Stopping the whole search, sorry bro!");
                return;
            }

            ArrayList<Integer> tmpResults = new ArrayList<Integer>();
            for ( int index : results )
            {
                if ( qGramIndex.get(qGram).contains(index + i) )
                {
//                    System.out.println("index=" + index);
                    tmpResults.add(index);
                }
            }
            results = tmpResults;

            if ( results.isEmpty() )
            {
                System.out.println("Found no matches: " + results.size() + " for pattern: " + Tools.byteArrayToString(p, p_len));
                return;
            }
        }
        System.out.println("Found matches: " + results.size() + " for pattern: " + Tools.byteArrayToString(p, p_len));
        for ( int matchIndex : results )
        {
            System.out.println("index=" + matchIndex);
        }
    }
}
