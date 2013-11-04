import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class FASTAReader
{
    private byte[] data;
    private int    position;

    private int  lastChangeIndex = 0;
    /**
     * 0: undefined
     * 1: in description
     * 2: in comment
     * 3: in sequence data
     */
    private byte parseState      = 0;

    private FASTASequence currentFastaSequence = new FASTASequence();


    private class FASTASequence
    {
        int description;
        int comment = -1;
        int sequence;
        int sequenceLength;

        public FASTASequence()
        {
        }

        @Override
        public String toString()
        {
            return "description: " + description + ", comment: " + comment + ", sequence data: " + sequence +
                   ", sequence data length: " + sequenceLength;
        }
    }

    // List of found sequences
    private ArrayList<FASTASequence> fastaSequences = new ArrayList<FASTASequence>();


    public FASTASequence getFastaSequence( int index )
    {
        return fastaSequences.get(index);
    }

    public ArrayList<FASTASequence> getFastaSequences()
    {
        return fastaSequences;
    }


    public byte getFASTASequenceData( int sequenceNr, int byteIndex )
    {
        return data[byteIndex + fastaSequences.get(sequenceNr).sequence];
    }


    public FASTAReader( String fileName ) throws IOException
    {
        long start = System.nanoTime();
        open(fileName);
        System.out.println("Open: " + ( System.nanoTime() - start ) / 1e6 + "ms");
    }

    public byte readByte()
    {
        return data[position++];
    }

    private void open( String fileName ) throws IOException
    {
        FileInputStream f = new FileInputStream(fileName);
        FileChannel ch = f.getChannel();
        MappedByteBuffer mb = ch.map(FileChannel.MapMode.READ_ONLY, 0L, ch.size());

        data = new byte[mb.remaining()];
        mb.get(data);

        parse();

        position = 0;
    }


    private void setParseState( int i )
    {
        switch ( data[i] )
        {
            case 62:
                parseState = 1;
                break;
            case 59:
                parseState = 2;
                break;
            default:
                parseState = 3;
                break;
        }
    }


    private void parse()
    {
        // initalize parseState according to first char
        setParseState(0);

        // parse whole file from index 0 to last index - 1
        for ( int i = 0; i < data.length - 1; ++i )
        {
            // we found a newline (LF)
            if ( data[i] == 10 )
            {
                // in description
                if ( parseState == 1 )
                {
                    currentFastaSequence.description = lastChangeIndex;
                    lastChangeIndex = i + 1;
                    setParseState(i + 1);
                }
                // in comment
                else if ( parseState == 2 )
                {
                    currentFastaSequence.comment = lastChangeIndex;
                    lastChangeIndex = i + 1;
                    setParseState(i + 1);
                }
                // in sequence data
                else if ( parseState == 3 )
                {
                    // new description after that line
                    if ( data[i + 1] == 62 )
                    {
                        currentFastaSequence.sequence = lastChangeIndex;
                        currentFastaSequence.sequenceLength = i - lastChangeIndex;
                        fastaSequences.add(currentFastaSequence);
                        currentFastaSequence = new FASTASequence();
                        lastChangeIndex = i + 1;
                        setParseState(i + 1);
                    }
                }
            }
        }

        // finish the last FASTASequence
        currentFastaSequence.sequence = lastChangeIndex;
        fastaSequences.add(currentFastaSequence);
        currentFastaSequence.sequenceLength = data.length - lastChangeIndex - 1;
    }


    public static void main( String[] args )
    {
        // Testing only
        try
        {
            FASTAReader fr = new FASTAReader("./resources/pattern_aufgabe.fasta");
            for ( int i = 0; i < fr.getFastaSequences().size(); ++i )
            {
                for ( int j = 0; j < fr.getFastaSequence(i).sequenceLength; ++j )
                {
                    System.out.print((char) fr.getFASTASequenceData(i, j));
                }
                System.out.println();
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
