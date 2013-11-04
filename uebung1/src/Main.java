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
        
        
        // testing
        
        pattern = "aat";
        String text = "caaatacaagggagaaattatgatattctcagataaacaaaagctaacagagttcatgaccattaaaccttgcctacaagaaatgctaaagggagtgcttcaggtggaaatgaaaggatgctatatggcaactcaaagttgtatgaagaaataaagatatccagttatgtgtacattttttaaaaatcatgcttcaatatcccacaaacaagctgtgagcaccctgcccaggtgaccaggtaaccagtattttaaagctggtccctgccacaattcctcttcttgtcttcccctactgtgacctagtgacattgaaacactctagtaaaagtgtttttgcttgtgtcctccaccttgactcccagtaaaggcacttgcccagggatcccatctctcttgcctacccatctgcttgattgagcctgctccttgggagctccttccacatggcttcctgcatggcctgccctacttctgtctctaggaactgtgagtatgcctcttcatatgaacttttcatggccatgttagagtgatcctctaagattcaaccagcaggagtcactctaacattttctatagtaatggcaatgaggatgggatcattctaaactcaggtggaaagttcctagggaatgcctttgactcatgacttactgatttgctactctgattggctccacaatttgagaaggctttttaccctgatgactttcttgtgctatgctatgtacagctttgtgttgccttttggagtgctgccatggctctccatcctgaagcgaggatcctgttccaatatcaataatgacatccacctttctccaagagcacaagatgatataacattggtaacacctcttcaaagaagattcatgtgatatgcatattaatgtcacataatccagcacctgtaagttttttttgggatgcctgtggcaacatattcttcatttacaaattttacttacaaattaaaatcaacagacactcctgttagtgtcattaaatgaattaaacaaaaatatctctcctctgtaaagactttggctgtgtctttcatgcctcttggagtctctagaccactcatgatggctatatatttcctcagggccttgatgcaaaaataatgctctttatggcctacgctgtactccattaaaacaactgactggttacccactagaatctcctgatttggattccagatttgaagataataccctcctgtttcaatcattaaccataaattgccccacaggccaggctctgctcatctgaaatgacacccatcggaatttaaacttccactgcaacacaaaattaaaattgctgccaggtacagctgcacacctttctgttgacagttggcattattactacttggtttgttgatgcaacaatcctcaaccaaatactagcaaactgaattgaacagca";
        
        byte aPattern[] = new byte[pattern.length()];
        for (int i = 0; i < pattern.length(); i++ ) {
        	aPattern[i] = (byte) pattern.charAt(i);
        	System.out.println(aPattern[i]);
        }
        
        byte aText[] = new byte[text.length()];
        for (int i = 0; i < text.length(); i++ ) {
        	aText[i] = (byte) text.charAt(i);
        	System.out.println(aText[i]);
        }
        
        new KMP(aPattern, new PatternAnalysing(aPattern).getPraefix(), aText);
    }
}
