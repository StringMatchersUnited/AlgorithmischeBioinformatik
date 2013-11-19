package de.huberlin.algobio.ws1314.gruppe2.tools;

public class Tools
{
    public static String byteArrayToString( byte[] byteArray, int length )
    {
        StringBuilder string = new StringBuilder();
        for ( int i = 0; i < length; ++i )
        {
            string.append((char) byteArray[i]);
        }

        return string.toString();
    }
}
