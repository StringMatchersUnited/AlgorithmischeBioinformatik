package de.huberlin.algobio.ws1314.gruppe2.tools;

import java.util.HashMap;


public class Tools
{
    public static HashMap<String, Integer> tFreq;

    public static String byteArrayToString( byte[] byteArray )
    {
        StringBuilder string = new StringBuilder();
        for ( byte b : byteArray )
        {
            string.append( (char) b );
        }

        return string.toString();
    }

    public static HashMap<String, Integer> getFrequencies( byte[] template )
    {
        HashMap<String, Integer> frequencies = new HashMap<String, Integer>();

        int a = 0;
        int c = 0;
        int g = 0;
        int t = 0;
        int n = 0;

        for ( byte b : template )
        {
            switch ( b )
            {
                case 'a':
                    a++;
                    break;
                case 'c':
                    c++;
                    break;
                case 'g':
                    g++;
                    break;
                case 't':
                    t++;
                    break;
                case 'n':
                    n++;
                    break;
            }
        }

        frequencies.put( "a", a );
        frequencies.put( "c", c );
        frequencies.put( "g", g );
        frequencies.put( "n", n );
        frequencies.put( "t", t );
        frequencies.put( "all", a + c + g + n + t );

        return frequencies;
    }

    public static double calcExpectation( byte[] pattern )
    {
        double tall = tFreq.get( "all" );
        double ta = tFreq.get( "a" ) / tall;
        double tc = tFreq.get( "c" ) / tall;
        double tg = tFreq.get( "g" ) / tall;
        double tn = tFreq.get( "n" ) / tall;
        double tt = tFreq.get( "t" ) / tall;

        HashMap<String, Integer> pFreq = getFrequencies( pattern );

        return Math.pow( ta, pFreq.get( "a" ) ) *
                Math.pow( tc, pFreq.get( "c" ) ) *
                Math.pow( tg, pFreq.get( "g" ) ) *
                Math.pow( tn, pFreq.get( "n" ) ) *
                Math.pow( tt, pFreq.get( "t" ) ) *
                tall;
    }

    public static String printPositions( int[] positions )
    {
        StringBuilder sb = new StringBuilder();
        sb.append( positions[0] );
        for ( int i = 1; i < positions.length; ++i )
        {
            sb.append( ", " ).append( positions[i] );
        }

        return sb.toString();
    }
}
