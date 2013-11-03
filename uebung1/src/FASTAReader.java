import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class FASTAReader
{
    private byte[] data;
    private int    position;
    private int    lineLength;

    private int  lastChangeIndex = 0;
    private int  parsePosition   = 1;
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

        public FASTASequence()
        {
        }

        public FASTASequence( int description, int comment, int sequence )
        {
            this.description = description;
            this.comment = comment;
            this.sequence = sequence;
        }

        @Override
        public String toString()
        {
            return "description: " + description + ", comment: " + comment + ", sequence data: " + sequence;
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


    public FASTAReader( String fileName ) throws IOException
    {
        long start = System.nanoTime();
        open(fileName);
        System.out.println("Open: " + ( System.nanoTime() - start ) / 1e6 + "ms");


        System.out.println("number of fasta seqs: " + fastaSequences.size());
        for (FASTASequence fs: fastaSequences)
        {
            System.out.println(fs);
        }
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

        parseSlow();
        while ( parsePosition > 0 )
        {
            if ( !parseFast() )
                parseSlow();
        }
    }


    private boolean parseFast()
    {
        return false;  //To change body of created methods use File | Settings | File Templates.
    }


    private void parseSlow()
    {
        int parseUntil = Math.min(parsePosition + 4096, data.length);

        for ( int i = parsePosition; i < parseUntil; ++i )
        {
            // we found a newline (LF)
            if ( data[i] == 10 || i >= data.length - 1 )
            {
                // in description
                if ( parseState == 1 )
                {
                    System.out.println("parseState=1");
                    System.out.println("index:" + i);
                    currentFastaSequence.description = lastChangeIndex;
                    lastChangeIndex = i + 1;
                    setParseState(i + 1);
                }
                // in comment
                else if ( parseState == 2 )
                {
                    System.out.println("parseState=2");
                    System.out.println("index:" + i);
                    currentFastaSequence.comment = lastChangeIndex;
                    lastChangeIndex = i + 1;
                    setParseState(i + 1);
                }
                // in sequence data
                else if ( parseState == 3 )
                {
//                    System.out.println("state 3: length=" + data.length + " i=" + i);
                    if ( i >= data.length - 1 || data[i + 1] == 62  )
                    {
                        System.out.println("end of sequence data");
                        System.out.println("Sequence start: " + lastChangeIndex);
                        System.out.println("sequence end: " + i);
                        currentFastaSequence.sequence = lastChangeIndex;
                        fastaSequences.add(currentFastaSequence);
                        currentFastaSequence = new FASTASequence();
                        lastChangeIndex = i + 1;

                        if (i < data.length - 1)
                            setParseState(i + 1);
                    }
                }

            }
        }
        if ( parseUntil == data.length )
        {
            parsePosition = 0;
        }
        else
        {
            parsePosition = parseUntil;
        }
    }


    public static void main( String[] args )
    {
        // Testing only
        try
        {
            FASTAReader fr = new FASTAReader("./resources/sequence.fasta");
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
    }
}
