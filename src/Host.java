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

    private void printPackage(Packet pack) {
        System.out.println("+-------------------+");
        System.out.println("| Pacote");
        System.out.println("| Payload: " + pack.payload);
        System.out.println("| OM: " + pack.originMac);
        System.out.println("| DM: " + pack.destinationMac);
        System.out.println("| OI: " + pack.originIp);
        System.out.println("| DI: " + pack.destinationIp);
        System.out.println("+-------------------+ \n");
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Port getPort() {
        // Quando tentamos criar outra porta no this.Host
        // a porta fica conectada em 2 portas ao mesmo tempo
        return port;
    }

    public void sendPackage(String destinationIp, String message) {
        var destinationMac = addressTable.get(destinationIp);

        if (destinationMac == null) {
            originalPayload = message;
            var arpPack = new Packet("REQUEST", this.macAddress, arpBroadcast, this.ipAddress, destinationIp);
            printPackage(arpPack);
            port.send(arpPack);
            // connectedSwitch.transmit(arpPack, this);
        } else {
            var pack = new Packet(message, this.macAddress, destinationMac, this.ipAddress, destinationIp);
            printPackage(pack);
            // connectedSwitch.transmit(pack, this);
            originalPayload = null;
        }
    }

    public void receiveMessage(Packet pack) {
        addressTable.put(pack.originIp, pack.originMac);

        if ((pack.destinationIp.equals(this.ipAddress))) {
            if (pack.destinationMac.equals(this.macAddress)) {
                System.out.println("Package received by intended Host!");
                System.out.println(pack.payload);
            } else if (pack.destinationMac.equals(arpBroadcast)) {
                if (pack.payload.equals("REQUEST")) {
                    Packet arpReply = new Packet("REPLY", this.macAddress, arpBroadcast, this.ipAddress, pack.originIp);
                    printPackage(arpReply);
                    port.send(arpReply);
                } else if (pack.payload.equals("REPLY")) {
                    sendPackage(pack.originIp, originalPayload);
                }
            }
        }
    }
}
