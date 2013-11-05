

public class PatternAnalysing
{
	private int praefix[];
	
    public PatternAnalysing( byte[] pattern )
    {
    	int position = 0;
    	int praefixLength = -1;
    	
    	int patternLength = pattern.length; // (sichtbar) schneller als mehrfach methode aufzurufen?
    	
    	praefix = new int[patternLength + 1];
    	praefix[0] = praefixLength;
    	
    	while ( position < patternLength) {
    		
    		while (praefixLength >= 0 && pattern[position] != pattern[praefixLength]) {
    			praefixLength = praefix[praefixLength];
    		}
    		
    		position++;
    		praefixLength++;
    		praefix[position] = praefixLength;
    		
//    		System.out.println(praefix[position]);
    	}
    	
    }
    
    public int[] getPraefix() {
    	return praefix;
    }
}
