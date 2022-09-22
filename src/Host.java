import java.util.HashMap;

public class Host extends Thread {
    private final String macAddress;
    private String ipAddress;
    private final Port port;
    private final HashMap<String, String> addressTable;
    private final String arpBroadcast = "FF:FF:FF:FF";
    private String originalPayload;

    public Host(String macAddress, String ipAddress) {
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.addressTable = new HashMap<>();
        this.port = new Port();
        start();
    }

    @Override
    public void run() {
        while (true) {

            if (!port.getReceived().isEmpty()) {
                receiveMessage(port.getReceived().getFirst());
                port.getReceived().removeFirst();
            }
        }
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Port getPort() {
        return port;
    }

    public void sendPacket(String destinationIp, String message) {
        var destinationMac = addressTable.get(destinationIp);

        if (destinationMac == null) {
            originalPayload = message;
            var arpPack = new Packet("REQUEST", this.macAddress, arpBroadcast, this.ipAddress, destinationIp);
            port.send(arpPack);
        } else {
            var pack = new Packet(message, this.macAddress, destinationMac, this.ipAddress, destinationIp);
            port.send(pack);
            originalPayload = null;
        }
    }

    public void receiveMessage(Packet pack) {
        addressTable.put(pack.originIp, pack.originMac);

        if ((pack.destinationIp.equals(this.ipAddress))) {
            if (pack.destinationMac.equals(this.macAddress)) {
                System.out.println("Packet received by intended Host!");
                System.out.println(pack.payload);
            } else if (pack.destinationMac.equals(arpBroadcast)) {
                if (pack.payload.equals("REQUEST")) {
                    Packet arpReply = new Packet("REPLY", this.macAddress, arpBroadcast, this.ipAddress, pack.originIp);
                    port.send(arpReply);
                } else if (pack.payload.equals("REPLY"))
                    sendPacket(pack.originIp, originalPayload);
            }
        }
    }
}