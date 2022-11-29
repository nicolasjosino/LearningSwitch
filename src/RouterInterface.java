public class RouterInterface {
    private final Port port;
    private final String ip;

    public RouterInterface(Port port, String ip) {
        this.port = port;
        this.ip = ip;
    }

    public Port getPort() {
        return port;
    }

    public String getIp() {
        return ip;
    }

}
