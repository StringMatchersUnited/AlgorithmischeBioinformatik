public class TimeMeasure
{
    private static long start;

    public static void start()
    {
        start = System.nanoTime();
    }

    public static void stop()
    {
        System.out.println((System.nanoTime() - start)/1e6 + "ms");
    }
}
