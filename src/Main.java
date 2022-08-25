public class Main {
    public static void main(String[] args) {
        var switch1 = new Switch(4);
        var h1 = new Host("h1", "10.15.20.1", switch1);
        var h2 = new Host("h2", "10.15.20.2", switch1);
    }
}
