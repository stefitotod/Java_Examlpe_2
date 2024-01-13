public class Regex {
    int id;
    String pattern;
    String description;
    int rating;
    static int nextId = 0;

    public Regex(String pattern, String description) {
        this.pattern = pattern;
        this.description = description;
        rating = 0;
        id = nextId + 1;
        // Regex.nextId += 1;
        nextId ++;
    }

    // public static void main(String[] args) {
    //     Regex a = new Regex("sdfdsf", "jdfskjdsk");
    //     Regex b = new Regex("SDKJKJSDKFJSDK", "jkskdfjsdk");
    //     System.out.println(b.id);
    // }
}