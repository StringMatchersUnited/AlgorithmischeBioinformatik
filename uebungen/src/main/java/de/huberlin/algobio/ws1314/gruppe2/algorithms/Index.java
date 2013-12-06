package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.IntArray;
import de.huberlin.algobio.ws1314.gruppe2.tools.TimeMeasure;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;

public class Index
{
    private HashMap<String, IntArray> qGramIndex = new HashMap<String, IntArray>();

    public Index( int q, byte[] template, int templateLength, String indexFileName )
    {
        TimeMeasure.start("create_index");
        create_index(q, template, templateLength);
        TimeMeasure.stop("create_index");

        TimeMeasure.start("write_index");
        write_index(indexFileName);
        TimeMeasure.stop("write_index");

        TimeMeasure.start("write_index_2");
        write_index_2(indexFileName + "_2");
        TimeMeasure.stop("write_index_2");
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


    private void write_index_2( String indexFileName )
    {
        try
        {
            ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(indexFileName));
            oo.writeObject(qGramIndex);
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }


    private void write_index( String indexFileName )
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(indexFileName), 64 * 1024);
            for ( String qGram : qGramIndex.keySet() )
            {
                bw.write(qGram);
                bw.write(':');

                IntArray indexes = qGramIndex.get(qGram);
                bw.write(Integer.toString(indexes.get(0)));
                for ( int i = 1; i < indexes.size(); ++i )
                {
                    bw.write(':');
                    bw.write(Integer.toString(indexes.get(i)));
                }

                bw.write("\n");
            }
            bw.close();
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}