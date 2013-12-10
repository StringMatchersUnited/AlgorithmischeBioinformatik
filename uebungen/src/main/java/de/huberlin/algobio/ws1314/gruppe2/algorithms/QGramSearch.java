package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.IntArray;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class QGramSearch
{
    public QGramSearch( HashMap<String, IntArray> qGramIndex, byte[] p, int p_len, int q, int t_len,
                        HashMap<String, Integer> tFreq )
    {
        if ( q > p_len )
            throw new IllegalArgumentException("Pattern is too short for index with q=" + q);

        Tools.tFreq = tFreq;
        double expectedHits = Tools.calcExpectation(p, p_len);

        ArrayList<Integer> results;

        String qGram = new String(Arrays.copyOfRange(p, 0, q));

        if ( !qGramIndex.containsKey(qGram) )
        {
            System.out.println("Pattern qGram not found in qGram index. Stopping the whole search, sorry bro!");
            return;
        }

        // all indexes for the first qGram are potential results
        results = qGramIndex.get(qGram).toArrayList();

        for ( int i = q; i < p_len - ( q - 1 ); i += q )
        {
            qGram = new String(Arrays.copyOfRange(p, i, i + q));

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
                    tmpResults.add(index);
                }
            }
            results = tmpResults;

            if ( results.isEmpty() )
            {
                System.out.println(
                        "Found no matches: " + results.size() + " for pattern: " + Tools.byteArrayToString(p, p_len));
                return;
            }
        }

        // last step if p_len modulo q not zero
        if ( p_len % q != 0 )
        {
            qGram = new String(Arrays.copyOfRange(p, p_len - q, p_len));

            if ( !qGramIndex.containsKey(qGram) )
            {
                System.out.println("Pattern qGram not found in qGram index. Stopping the whole search, sorry bro!");
                return;
            }

            ArrayList<Integer> tmpResults = new ArrayList<Integer>();
            for ( int index : results )
            {
                if ( qGramIndex.get(qGram).contains(index + p_len - q) )
                {
                    tmpResults.add(index);
                }
            }
            results = tmpResults;

            if ( results.isEmpty() )
            {
                System.out.println(
                        "Found no matches: " + results.size() + " for pattern: " + Tools.byteArrayToString(p, p_len));
                return;
            }

        }

        int ftp_len = Math.min(10, results.size());
        int[] firstTenPositions = new int[ftp_len];
        for ( int i = 0; i < ftp_len; ++i )
        {
            firstTenPositions[i] = results.get(i);
        }

        System.out.println("> " + Tools.byteArrayToString(p, p_len));
        System.out.println(">> Length: " + p_len);
        System.out.println(">> Occurrences: " + results.size());
        System.out.println(">> Expected: " + String.format("%f", expectedHits));
        System.out.println(">> Positions: " + Tools.printPositions(firstTenPositions));
        System.out.println();
    }
}
