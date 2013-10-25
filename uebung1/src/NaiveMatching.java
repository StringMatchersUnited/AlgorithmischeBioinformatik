import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.FileSystems;
import java.nio.file.Files;


public class NaiveMatching
{
    public NaiveMatching( String pattern, String textFileName )
    {
        System.out.println("NaiveMatching");
        try
        {
            match(pattern.getBytes(), open(textFileName), getFileLength(textFileName));

        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        System.out.println();
    }


    private Integer getFileLength( String fileName )
    {
        try
        {
            long length = Files.size(FileSystems.getDefault().getPath(fileName));
            if ( length <= Integer.MAX_VALUE )
            {
                return (int) length;
            }
            else
            {
                System.out.println("Filesize greater than " + Integer.MAX_VALUE + ". Sorry we can't handle that.");
            }
        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        return null;
    }


    private void match( byte[] pattern, MappedByteBuffer text, int fileLength )
    {
        TimeMeasure.start();
        for ( long t_index = 0; t_index < fileLength; ++t_index )
        {
            int p_index;
            for ( p_index = 0; p_index < pattern.length; ++p_index )
            {
                if ( pattern[p_index] != text.get((int) t_index + p_index) )
                {
                    break;
                }
            }

            if ( p_index == pattern.length )
            {
                System.out.println("Found a match at position: " + t_index);
                TimeMeasure.stop();
            }
        }
    }


    public MappedByteBuffer open( String fileName ) throws IOException
    {
        FileInputStream f = new FileInputStream(fileName);
        FileChannel ch = f.getChannel();
        return ch.map(FileChannel.MapMode.READ_ONLY, 0L, ch.size());
    }
}
