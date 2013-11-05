public class Tools
{
    public static String byteArrayToString( byte[] byteArray )
    {
        StringBuilder string = new StringBuilder();
        for ( byte b : byteArray )
        {
            string.append((char)b);
        }

        return string.toString();
    }
}
