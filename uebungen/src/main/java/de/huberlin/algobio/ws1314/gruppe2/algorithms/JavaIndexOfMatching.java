package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.TimeMeasure;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;

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
