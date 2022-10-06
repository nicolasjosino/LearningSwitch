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

        h1.sendPacket(h2.getIpAddress(), "hello h2 from h1");
        h2.sendPacket(h1.getIpAddress(), "hello h1 from h2");
        h3.sendPacket(h2.getIpAddress(), "hello h2 from h3");

        System.out.println("H1's Address Table: " + h1.getAddressTable());
        System.out.println("H2's Address Table: " + h2.getAddressTable());
        System.out.println("H3's Address Table: " + h3.getAddressTable());
        System.out.println();

        System.out.println();
        System.out.println("Switch 1 Ports: " + switch1.getPorts());
        System.out.println("Switch 1's Address Table: " + switch1.getAddressTable());
        System.out.println();
        System.out.println("Switch 2 Ports: " + switch2.getPorts());
        System.out.println("Switch 2's Address Table: " + switch2.getAddressTable());
    }
}