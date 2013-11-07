package de.huberlin.algobio.ws1314.gruppe2.algorithms;

public class NaiveMatching
{
    private int matchCount = 0;

    public NaiveMatching( byte[] pattern, byte[] template )
    {
        match(pattern, template);
    }


    private void match( byte[] pattern, byte[] template )
    {
        for ( int t = 0; t < template.length - pattern.length; ++t )
        {
            int p;
            for ( p = 0; p < pattern.length; ++p )
            {
                if ( pattern[p] != template[t + p] )
                {
                    break;
                }
            }

            if ( p == pattern.length )
            {
                ++matchCount;
            }
        }
        System.out.println("Found " + matchCount + " matches.");
    }
}
