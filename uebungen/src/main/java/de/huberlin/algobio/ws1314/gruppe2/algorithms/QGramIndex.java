package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.IntArray;
import de.huberlin.algobio.ws1314.gruppe2.tools.TimeMeasure;

import java.util.Arrays;
import java.util.HashMap;

public class QGramIndex
{
    private HashMap<String, IntArray> qGramIndex = new HashMap<String, IntArray>();

    public HashMap<String, IntArray> getQgramIndex()
    {
        return qGramIndex;
    }

    public QGramIndex( int q, byte[] template, int templateLength, String indexFileName )
    {
        TimeMeasure.start("create_index");
        create_index(q, template, templateLength);
        TimeMeasure.stop("create_index");
    }


    private void create_index( int q, byte[] t, int t_len )
    {
        for ( int i = 0; i < t_len - ( q - 1 ); ++i )
        {
            String qGram = new String(Arrays.copyOfRange(t, i, i + q));

            if ( !qGramIndex.containsKey(qGram) )
                qGramIndex.put(qGram, new IntArray(10000));

            qGramIndex.get(qGram).add(i);
        }
    }
}
