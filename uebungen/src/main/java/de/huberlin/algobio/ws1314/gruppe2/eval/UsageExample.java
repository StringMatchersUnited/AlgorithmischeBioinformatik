package de.huberlin.algobio.ws1314.gruppe2.eval;

import de.huberlin.algobio.ws1314.gruppe2.eval.ResultChecker.Result;


public class UsageExample
{
    public static void main( String[] args )
    {
		/*
         * Compare two result files using ResultChecker.compareResultFiles
		 */
        Result result = ResultChecker.compareResultFiles( args[0], args[1] );

		/*
		 * You can output the result of the comparison this way ...
		 */
        System.out.println( result );

		/*
		 * If you like to, there is a simple pass-test function, too.
		 */
        if ( result.doesFilesContainSameContent() )
        {
            System.out.println( "You win." );
        } else
        {
            System.out.println( "You lose." );
        }
    }
}
