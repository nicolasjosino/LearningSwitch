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
        var switch1 = new Switch(2);
        var switch2 = new Switch(3);
        Host h1 = new Host("h1", "10.10.20.1");
        Host h2 = new Host("h2", "10.10.21.2");
        Host h3 = new Host("h3", "10.10.21.3");
        Router r1 = new Router(2, "r1");
        Router r2 = new Router(2, "r2");

        connect(h1, switch1);
        connect(switch1, r1);

        r1.addRoute("10.10.20.0/24", "10.10.20.2", r1.getPorts().get(0));
        r1.addRoute("10.10.21.0/24", "10.10.21.4", r1.getPorts().get(1));

        connect(r1, r2);

        connect(r2, switch2);
        connect(h2, switch2);
        connect(h3, switch2);

        r2.addRoute("10.10.20.0/24", "10.10.20.5", r2.getPorts().get(0));
        r2.addRoute("10.10.21.0/24", "10.10.21.3", r2.getPorts().get(1));

        h1.sendPacket(h2.getIpAddress(), "hello");
    }
}