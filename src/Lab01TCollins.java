public class Lab01TCollins {
    public static void main (String[] args)
    {
        int int1 = 8, int2 = 2;
        printDivisionReport(int1, int2);
    }

    public static double divAsDouble(int a, int b)
    {
        return (double) a / b;
    }
    public static int divAsInt(int a, int b)
    {
        return a / b;
    }
    public static String classifyDivision(int a, int b)
    {
        if (a % b == 0)
        {
            return "exact";
        }
        else
        {
            return "truncates";
        }
    }
    public static void printDivisionReport(int a, int b)
    {
        if (b==0)
        {
            System.out.println("ERROR: division by zero");
            System.exit(0);
        }
        System.out.printf(  """
                            a/b = %d
                            (double)a/b = %.2f
                            a/(double)b = %.2f
                            (double)(a/b) = %.2f
                            classification = %s""", 
                            divAsInt(a, b), divAsDouble(a, b), a / (double)b,
                            (double)a / b, classifyDivision(a,b));
    }
}
