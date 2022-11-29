import java.util.HashMap;
import java.util.LinkedList;

public class Router extends Switch {

    private final HashMap<String, String> arpTable;
    private final HashMap<String, RouterInterface> routerTable;
    private final String macAddress;
    private final HashMap<String, RouterInterface> interfaces;

    public Router(Integer portsQuantity, String macAddress) {
        super(portsQuantity);
        this.macAddress = macAddress;
        this.arpTable = new HashMap<>();
        this.routerTable = new HashMap<>();
        this.interfaces = new HashMap<>();
    }

    public void addRoute(String netAddress, String ip) {
        addInterface(ip);
        routerTable.put(netAddress, interfaces.get(ip));
    }

    public void addInterface(String ip) {
        var routerInterface = new RouterInterface(getAvailablePort(), ip);
        interfaces.put(ip, routerInterface);
        routerInterface.getPort().connect();
    }

    public void transmit(Packet pack, Port caller) {
        addressTable.put(pack.originMac, caller);
        arpTable.put(pack.originIp, pack.originMac);

        if (ipWithinTable(pack.destinationIp)) {
            if (pack.payload.equals("REQUEST")) {
                Packet reply = new Packet("REPLY", this.macAddress, "FF:FF:FF", pack.originIp, pack.destinationIp);
                caller.send(reply);
            } else {
                LinkedList<String> netAddressList = new LinkedList<>(routerTable.keySet());
                String longestPrefix = Ipv4.getLongestPrefix(netAddressList, pack.destinationIp);
                pack.originMac = this.macAddress;
                routerTable.get(longestPrefix).getPort().send(pack);
            }
        }
    }

    private boolean ipWithinTable(String ipAddress) {
        boolean result = false;
        for (String netAddress : routerTable.keySet()) {
            result = Ipv4.IpBelongsToNetwork(netAddress, ipAddress);
            if (result) break;
        }
        return result;
    }

}
