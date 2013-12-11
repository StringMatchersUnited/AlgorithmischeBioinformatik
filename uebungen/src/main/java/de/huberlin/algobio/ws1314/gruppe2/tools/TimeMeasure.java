package de.huberlin.algobio.ws1314.gruppe2.tools;

import java.util.HashMap;


public class TimeMeasure
{
    private static HashMap<String, Long> startTimes = new HashMap<String, Long>();

    public static void start( String what )
    {
        startTimes.put( what, System.nanoTime() );
    }

    public static void stop( String what )
    {
        if ( startTimes.containsKey( what ) )
        {
            System.out.println( what + ": " + ( System.nanoTime() - startTimes.get( what ) ) / 1e6 + "ms" );
        }
    }
}
