package de.huberlin.algobio.ws1314.gruppe2.io;

import de.huberlin.algobio.ws1314.gruppe2.tools.TimeMeasure;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;

public class SubstitutionMatrixReader
{
    public HashMap<Byte, HashMap<Byte, Integer>> substitutionMatrix;

    public SubstitutionMatrixReader( String fileName ) throws IOException
    {
        TimeMeasure.start("Open of " + fileName);
        this.substitutionMatrix = open(fileName);
        TimeMeasure.stop("Open of " + fileName);
    }

    private HashMap<Byte, HashMap<Byte, Integer>> open( String fileName ) throws IOException
    {
        FileInputStream f = new FileInputStream(fileName);
        FileChannel ch = f.getChannel();
        MappedByteBuffer mb = ch.map(FileChannel.MapMode.READ_ONLY, 0L, ch.size());

        return parse(mb);
    }

    private HashMap<Byte, HashMap<Byte, Integer>> parse( MappedByteBuffer mb )
    {
        /**
         * 0 = reading spaces
         * 1 = reading int
         * 2 = reading newline
         */
        short state = 0;
        // data length
        int dataLength = mb.remaining();
        int lastIndex = dataLength - 1;

        // index of last state change
        int lastStateChangeAt = 0;

        HashMap<Byte, HashMap<Byte, Integer>> substitutionMatrix = new HashMap<Byte, HashMap<Byte, Integer>>();


        ArrayList<Byte> colHeader = new ArrayList<Byte>();
        // read first row and create HashMap for each column
        for ( int i = 0; i < dataLength; ++i )
        {
            byte b = mb.get(i);

            if ( state == 2 && b != 10 && b != 13 )
            {
                state = 0;
                lastStateChangeAt = i;
                break;
            }

            if ( b != 32 && b != 10 && b != 13 )
            {
                substitutionMatrix.put(b, new HashMap<Byte, Integer>());
                colHeader.add(b);
            }
            else if ( b == 10 || b == 13 )
            {
                state = 2;
            }
        }

        int col = -1;
        byte[] number = new byte[0];
        byte rowKey = 0;


        for ( int i = lastStateChangeAt; i < dataLength; ++i )
        {
            byte b = mb.get(i);

            if ( state == 0 )
            {
                if ( b == 32 )
                {
                    continue;
                }
                else if ( b != 10 && b != 13 )
                {
                    if ( col == -1 )
                    {
                        rowKey = b;
                    }
                    state = 1;

                    number = new byte[1];
                    number[0] = mb.get(i);
                }
                else
                {
                    state = 2;
                }
            }
            else if ( state == 1 )
            {
                if ( b == 32 || b == 10 || b == 13 )
                {
                    if ( b == 32 )
                    {
                        state = 0;
                    }
                    else
                    {
                        state = 2;
                    }

                    if ( col >= 0 )
                    {
                        substitutionMatrix.get(colHeader.get(col)).put(rowKey, Tools.byteArrayToInt(number));
                    }
                    ++col;
                }
                else
                {
                    byte[] temp = new byte[number.length + 1];
                    System.arraycopy(number, 0, temp, 0, number.length);
                    temp[temp.length - 1] = mb.get(i);
                    number = temp;
                }
            }
            else if ( state == 2 )
            {
                if ( b != 10 && b != 13 && b == 32 )
                {
                    state = 0;
                    col = -1;
                }
                else if ( b != 10 && b != 13 )
                {
                    state = 1;
                    rowKey = b;
                    col = -1;
                }
            }
        }

        return substitutionMatrix;
    }

    public static void main( String[] args )
    {
        try
        {
            new SubstitutionMatrixReader("/home/simone/web/git/hu/algbio/uebungen/src/main/resources/uebung5/Dna.txt");
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
