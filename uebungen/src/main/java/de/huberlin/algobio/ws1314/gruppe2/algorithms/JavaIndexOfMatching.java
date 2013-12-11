package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.TimeMeasure;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;


public class JavaIndexOfMatching
{
    public JavaIndexOfMatching( byte[] p, byte[] t )
    {
        String patternStr = Tools.byteArrayToString( p );
        String templateStr = Tools.byteArrayToString( t );

        TimeMeasure.start( "JavaIndexOf" );
        System.out.println( templateStr.indexOf( patternStr ) );
        TimeMeasure.stop( "JavaIndexOf" );
    }
}
