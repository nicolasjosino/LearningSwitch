public class Main {
    public static void connect(Host host, Switch switchL) {
        var cable = new Cable(host.getPort(), switchL.getAvailablePort());
        host.getPort().setCable(cable);
        host.getPort().connect();

        switchL.getAvailablePort().setCable(cable);
        switchL.getAvailablePort().connect();
    }

    public static void connect(Switch switch1, Switch switch2) {
        var cable = new Cable(switch1.getAvailablePort(), switch2.getAvailablePort());
        switch1.getAvailablePort().setCable(cable);
        switch1.getAvailablePort().connect();

        switch2.getAvailablePort().setCable(cable);
        switch2.getAvailablePort().connect();
    }

    public static void main(String[] args) {
        var switch1 = new Switch(4);
        var switch2 = new Switch(2);
        Host h1 = new Host("h1", "10.15.20.1");
        Host h2 = new Host("h2", "10.15.20.2");
        Host h3 = new Host("h3", "10.15.21.3");
        connect(h1, switch1);
        connect(h2, switch1);
        connect(h3, switch2);
        connect(switch1, switch2);
        h1.sendPacket(h2.getIpAddress(), "how u doin");
        h2.sendPacket(h1.getIpAddress(), "I'm fine bro");
        h3.sendPacket(h1.getIpAddress(), "hey y'all");
    }
}