public class Main {
    private static int n;

    public static void main(String[] args) {
        String txt = "some text 1000";
        System.out.println(txt.chars().filter(dots -> dots == '.').count() != 0);
    }
}
