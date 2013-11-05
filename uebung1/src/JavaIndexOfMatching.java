public class JavaIndexOfMatching
{
    public JavaIndexOfMatching( byte[] pattern, byte[] template )
    {
        String patternStr = Tools.byteArrayToString(pattern);
        String templateStr = Tools.byteArrayToString(template);

        TimeMeasure.start("JavaIndexOf");
        System.out.println(templateStr.indexOf(patternStr));
        TimeMeasure.stop("JavaIndexOf");
    }
}
