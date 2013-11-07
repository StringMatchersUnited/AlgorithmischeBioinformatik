import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

public class FASTAReader
{
    /**
     * 0: undefined
     * 1: in description
     * 2: in comment
     * 3: in sequence data
     */
    private byte parseState = 0;


    private class FASTASequence
    {
        byte[] description;
        byte[] comment;
        byte[] sequence;

        public FASTASequence()
        {
        }

        @Override
        public String toString()
        {
            return "description length: " + description.length + ", comment length: " + comment.length +
                   ", sequence length: " + sequence.length;
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


    public byte[] getData( int sequenceNr )
    {
        return fastaSequences.get(sequenceNr).sequence;
    }

    public byte[] getDescription( int sequenceNr )
    {
        return fastaSequences.get(sequenceNr).description;
    }

    public byte[] getComment( int sequenceNr )
    {
        return fastaSequences.get(sequenceNr).comment;
    }


    public FASTAReader( String fileName ) throws IOException
    {
        TimeMeasure.start("Open of " + fileName);
        open(fileName);
        TimeMeasure.stop("Open of " + fileName);
    }


    private void open( String fileName ) throws IOException
    {
        FileInputStream f = new FileInputStream(fileName);
        FileChannel ch = f.getChannel();
        MappedByteBuffer mb = ch.map(FileChannel.MapMode.READ_ONLY, 0L, ch.size());

        parse(mb);
    }


    private void setParseState( byte b )
    {
        switch ( b )
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


    private void parse( MappedByteBuffer mb )
    {
        // initalize parseState according to first char
        setParseState(mb.get(0));

        // data length
        int dataLength = mb.remaining();
        int lastIndex = dataLength - 1;

        // FASTASequence object
        FASTASequence currentFastaSequence = new FASTASequence();

        // index of last state change
        int lastStateChangeAt = 0;

        // byte-array for current data (which may be description, comment or sequence data)
        byte[] currentData = new byte[mb.remaining()];

        // count newlines to substract that value when filling the byte-array
        int newLines = 0;

        // parse whole file from index 0 to last index - 1
        for ( int i = 0; i < lastIndex; ++i )
        {
            // we found a newline (LF)
            if ( mb.get(i) == 10 )
            {
                ++newLines;

                // in description
                if ( parseState == 1 )
                {
                    currentFastaSequence.description = currentData;
                    currentData = new byte[mb.remaining()];
                    setParseState(mb.get(i + 1));
                    lastStateChangeAt = i + 1;
                    newLines = 0;
                }
                // in comment
                else if ( parseState == 2 )
                {
                    currentFastaSequence.comment = currentData;
                    currentData = new byte[mb.remaining()];
                    setParseState(mb.get(i + 1));
                    lastStateChangeAt = i + 1;
                    newLines = 0;
                }
                // in sequence data
                else if ( parseState == 3 )
                {
                    // new description after that line
                    if ( mb.get(i + 1) == 62 )
                    {
                        currentFastaSequence.sequence = currentData;
                        fastaSequences.add(currentFastaSequence);
                        currentData = new byte[mb.remaining()];
                        currentFastaSequence = new FASTASequence();
                        setParseState(mb.get(i + 1));
                        lastStateChangeAt = i + 1;
                        newLines = 0;
                    }
                }
            }
            else
            {
                currentData[i - lastStateChangeAt - newLines] = mb.get(i);
            }
        }

        // finish the last FASTASequence
        if (mb.get(lastIndex) != 10)
            currentData[lastIndex - lastStateChangeAt - newLines] = mb.get(lastIndex);
        currentFastaSequence.sequence = currentData;
        fastaSequences.add(currentFastaSequence);
    }


    public static void main( String[] args )
    {
        // Testing only
        try
        {
            FASTAReader fr = new FASTAReader("../resources/sequence.fasta");

            for ( int i = 0; i < fr.getFastaSequences().size(); ++i )
            {
                for ( byte b : fr.getDescription(i) )
                {
                    if (b != 0)
                        System.out.print((char) b);
                }
                System.out.println();

                for ( byte b : fr.getData(i) )
                {
                    if (b != 0)
                        System.out.print((char) b);
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
