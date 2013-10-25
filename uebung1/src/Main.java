import java.nio.file.Files;
import java.nio.file.Paths;

public class Main
{
    public static void main(String[] args)
    {
        String pattern = "aatgcgttaa";

        if (args.length == 0)
        {
            System.out.println("Please specify text file.");
            return;
        }

        if (! Files.exists(Paths.get(args[0])))
        {
            System.out.println("File does not exists.");
            return;
        }

        new JavaIndexOfMatching(pattern, args[0]);
        new NaiveMatching(pattern, args[0]);
    }
}
