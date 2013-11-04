

public class KMP
{
    public KMP( byte[] pattern, int[] praefix, byte[] text)
    {
    	int textPosition = 0;
    	int patternPosition = 0;
    	int hits = 0;
//    	int textlength = text.length;	// schneller?
    	
    	while (textPosition < text.length) {
    		
    		while (patternPosition >= 0 && text[textPosition] != pattern[patternPosition]) {
    			patternPosition = praefix[patternPosition];
    		}
    		
    		textPosition++;
    		patternPosition++;
    		
    		if (patternPosition == pattern.length) {
    			System.out.println("Treffer von: " + (textPosition - pattern.length + 1) + " bis " + textPosition);
    			hits++;
    			patternPosition = praefix[patternPosition];
    		}
    	}
    	
    	System.out.println("Treffer: " + hits);
    }
}
