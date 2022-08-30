public class Main {
    public static void main(String[] args) {
        var switch1 = new Switch(4);
        Host h1 = new Host("h1", "10.15.20.1", switch1);
        Host h2 = new Host("h2", "10.15.20.2", switch1);
        Host h3 = new Host("h3", "10.15.20.3", switch1);
        Host h4 = new Host("h4", "10.15.20.4", switch1);
        h1.sendPackage(h2.getIpAddress(), "hello");
    }
}