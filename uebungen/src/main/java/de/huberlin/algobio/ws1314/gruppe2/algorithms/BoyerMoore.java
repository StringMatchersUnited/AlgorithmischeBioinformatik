package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;


public class BoyerMoore
{
    public BoyerMoore( byte[] pattern, byte[] template )
    {
        int skipTable[] = getSkipTable( pattern );
        int nextTable[] = getNextTable( pattern );
        int hits = 0;
        int comp = 0;                      // vergleiche
        int[] firstTenHits = new int[10]; // ersten 10 als Liste?
        double expectedHits = Tools.calcExpectation( pattern );

        for ( int i = pattern.length - 1, j; i < template.length; )
        {
            for ( j = pattern.length - 1; pattern[j] == template[i]; --i, --j )
            {
                comp++;    // hier richtig? glaube schon! ;)
                if ( j == 0 )
                {
                    hits++;
                    if ( hits <= 10 )
                        firstTenHits[hits - 1] = i + 1;
                    break;
                }
            }
            i += Math.max( nextTable[pattern.length - 1 - j], skipTable[template[i]] );
        }

        System.out.println( "> " + Tools.byteArrayToString( pattern ) );
        System.out.println( ">> Length: " + pattern.length );
        System.out.println( ">> Occurrences: " + hits );
        System.out.println( ">> Expected: " + String.format( "%f", expectedHits ) );  // erwartungswert aus count
        System.out.println( ">> Char-Comparisons: " + comp );
        System.out.println( ">> Positions: " + Tools.printPositions( firstTenHits ) ); // ersten 10 Positionen
    }

    // "bad character matching"
    private int[] getSkipTable( byte[] pattern )
    {
        final int ALPHABET_SIZE = 256;
        int[] table = new int[ALPHABET_SIZE];

        for ( int i = 0; i < table.length; ++i )
        {
            table[i] = pattern.length;
        }

        for ( int i = 0; i < pattern.length; ++i )
        {
            table[pattern[i]] = pattern.length - 1 - i;
        }

        // testing
//    	for (int i = 0; i < ALPHABET_SIZE; ++i) {
//    		System.out.println(i + "|" + table[i]);
//    	}
        //

        return table;
    }

    // "good character matching"
    private int[] getNextTable( byte[] pattern )
    {
        int[] table = new int[pattern.length];
        int lastPrefixPosition = pattern.length;

        for ( int i = pattern.length - 1; i >= 0; --i )
        {
            if ( isPrefix( pattern, i + 1 ) )
            {
                lastPrefixPosition = i + 1;
            }
            table[pattern.length - 1 - i] = lastPrefixPosition - i + pattern.length - 1;
        }

        for ( int i = 0; i < pattern.length - 1; ++i )
        {
            int sLen = suffixLength( pattern, i );
            table[sLen] = pattern.length - 1 - i + sLen;
        }

        // testing
//    	for (int i = 0; i < table.length; ++i) {
//    		System.out.println(i + "|" + table[i]);
//    	}
        //

        return table;
    }

    private boolean isPrefix( byte[] pattern, int position )
    {
        for ( int i = position, j = 0; i < pattern.length; ++i, ++j )
        {
            if ( pattern[i] != pattern[j] )
            {
                return false;
            }
        }
        return true;
    }

    private int suffixLength( byte[] pattern, int position )
    {
        int length = 0;
        for ( int i = position, j = pattern.length - 1; i >= 0 && pattern[i] == pattern[j]; --i, --j )
        {
            length += 1;
        }
        return length;
    }
}
