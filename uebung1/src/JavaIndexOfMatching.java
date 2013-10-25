import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class JavaIndexOfMatching
{
    public JavaIndexOfMatching( String pattern, String textFileName )
    {
        System.out.println("JavaIndexOfMatching");
        try
        {
            int index = match(pattern, open(textFileName));
            System.out.println("Found a match at position: " + index);

        }
        catch ( IOException e )
        {
            e.printStackTrace();
        }
        System.out.println();
    }


    private int match( String pattern, String text )
    {
        TimeMeasure.start();
        int index = text.indexOf(pattern);
        TimeMeasure.stop();

        return index;
    }


    public String open( String fileName ) throws IOException
    {
        Charset charset = Charset.forName("UTF-8");
        byte[] encoded = Files.readAllBytes(Paths.get(fileName));
        return charset.decode(ByteBuffer.wrap(encoded)).toString();
    }
}
