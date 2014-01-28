package de.huberlin.algobio.ws1314.gruppe2.eval;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class ResultChecker
{
    private ResultChecker()
    {
    }

    public static Result compareResultFiles( String goldstandardFileName, String yourFilename )
    {
        ResultChecker check = new ResultChecker();

        Map<Integer, Set<Integer>> file1 = check.readFile( goldstandardFileName );
        Map<Integer, Set<Integer>> file2 = check.readFile( yourFilename );

        return check.compare( file1, file2 );
    }

    private Map<Integer, Set<Integer>> readFile( String filename )
    {
        Map<Integer, Set<Integer>> filecontent = new HashMap<Integer, Set<Integer>>();
        BufferedReader br;
        try
        {
            br = new BufferedReader( new FileReader( filename ) );
            String line;
            String[] splitline;
            int key;
            Set<Integer> values;
            while ( ( line = br.readLine() ) != null )
            {
                splitline = line.split( ":" );
                if ( splitline.length == 2 )
                {
                    key = Integer.parseInt( splitline[0] );
                    values = new HashSet<Integer>();
                    splitline = splitline[1].split( "," );
                    for ( String value : splitline )
                    {
                        values.add( Integer.parseInt( value ) );
                    }
                    if ( filecontent.containsKey( key ) )
                    {
                        filecontent.get( key ).addAll( values );
                    } else
                    {
                        filecontent.put( key, values );
                    }
                }
            }
            br.close();
        } catch ( FileNotFoundException e )
        {
            e.printStackTrace();
        } catch ( IOException e )
        {
            e.printStackTrace();
        }

        return filecontent;
    }


    private Result compare( Map<Integer, Set<Integer>> file1, Map<Integer, Set<Integer>> file2 )
    {
        Result result = new Result();

        // Match per key.
        for ( int key : file1.keySet() )
        {
            if ( file2.containsKey( key ) )
            {
                //Compare value sets of those keys that both files contain.
                compareValueSets( key, result, file1.get( key ), file2.get( key ) );
            } else
            {
                result.addMissingKey( 2, key );
            }
        }

        for ( int key : file2.keySet() )
        {
            if ( !file1.containsKey( key ) )
            {
                result.addMissingKey( 1, key );
            }
        }

        return result;
    }

    private void compareValueSets( int key, Result result, Set<Integer> set1, Set<Integer> set2 )
    {
        for ( int value : set1 )
        {
            if ( !set2.contains( value ) )
            {
                result.addMissingValue( 2, key, value );
            }
        }

        for ( int value : set2 )
        {
            if ( !set1.contains( value ) )
            {
                result.addMissingValue( 1, key, value );
            }
        }
    }

    /**
     * Result of a comparison of two files.
     */
    class Result
    {
        private Set<Integer>[] missingKeys;
        private HashMap<Integer, Set<Integer>>[] missingValues;

        @SuppressWarnings("unchecked")
        Result()
        {
            missingKeys = new HashSet[2];
            missingKeys[0] = new HashSet<Integer>();
            missingKeys[1] = new HashSet<Integer>();
            missingValues = new HashMap[2];
            missingValues[0] = new HashMap<Integer, Set<Integer>>();
            missingValues[1] = new HashMap<Integer, Set<Integer>>();
        }

        public boolean doesFilesContainSameContent()
        {
            return missingKeys[0].isEmpty() && missingKeys[1].isEmpty()
                    && missingValues[0].isEmpty() && missingValues[1].isEmpty();
        }

        public String toString()
        {
            if ( this.doesFilesContainSameContent() )
            {
                return "Both files contain the same keys and values.";
            }
            StringBuilder txt = new StringBuilder();
            if ( !missingKeys[1].isEmpty() )
            {
                txt.append( "Keys only at file 1: \n" );
                txt.append( missingKeys[1] );
            }
            if ( !missingKeys[0].isEmpty() )
            {
                txt.append( "Keys only at file 2: \n" );
                txt.append( missingKeys[0] );
            }

            if ( !missingValues[1].isEmpty() )
            {
                txt.append( "Values only at file 1: \n" );
                for ( int key : missingValues[1].keySet() )
                {
                    txt.append( key ).append( ": " ).append( missingValues[1].get( key ) );
                }
            }
            if ( !missingValues[0].isEmpty() )
            {
                txt.append( "Values only at file 2: \n" );
                for ( int key : missingValues[0].keySet() )
                {
                    txt.append( key ).append( ": " ).append( missingValues[0].get( key ) );
                }
            }
            return txt.toString();
        }


		/*
         * Functions used to fill the result object while comparing the files.
		 */

        void addMissingValue( int fileNr, int key, int value )
        {
            if ( fileNr == 1 || fileNr == 2 )
            {
                if ( !this.missingValues[fileNr - 1].containsKey( key ) )
                {
                    this.missingValues[fileNr - 1].put( key, new HashSet<Integer>() );
                }
                this.missingValues[fileNr - 1].get( key ).add( value );
            }
        }

        void addMissingKey( int fileNr, int key )
        {
            if ( fileNr == 1 || fileNr == 2 )
            {
                this.missingKeys[fileNr - 1].add( key );
            }
        }
    }
}
