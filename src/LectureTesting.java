public class LectureTesting {

    public static void main (String[] args) 
    {
        StringBuilder s, t;
        s = new StringBuilder("Hello");
        t = s;
        t.delete(3,5);
        System.out.println(t + "\n" + s);

    }

}
