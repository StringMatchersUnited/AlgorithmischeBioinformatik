package de.huberlin.algobio.ws1314.gruppe2.algorithms;

import de.huberlin.algobio.ws1314.gruppe2.tools.TimeMeasure;
import de.huberlin.algobio.ws1314.gruppe2.tools.Tools;

public class JavaIndexOfMatching
{
    public JavaIndexOfMatching( byte[] p, int p_len, byte[] t, int t_len)
    {
        String patternStr = Tools.byteArrayToString(p, p_len);
        String templateStr = Tools.byteArrayToString(t, t_len);

        TimeMeasure.start("JavaIndexOf");
        System.out.println(templateStr.indexOf(patternStr));
        TimeMeasure.stop("JavaIndexOf");
    }
}
