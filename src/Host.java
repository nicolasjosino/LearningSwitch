import java.util.HashMap;
import java.util.Objects;

public class Host {
    private final String macAddress;
    private String ipAddress;
    private Switch connectedSwitch;
    private final HashMap<String, String> addressTable;
    private final String arpBroadcast = "FF:FF:FF:FF";
    private String originalPayload;

    public Host(String macAddress, String ipAddress, Switch connectedSwitch) {
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.addressTable = new HashMap<>();
        this.connectedSwitch = connectedSwitch;
        connectedSwitch.getHosts().add(this);
    }

    private void printPackage(Package pack) {
        System.out.println("+-------------------+");
        System.out.println("| Pacote");
        System.out.println("| Payload: "+pack.payload);
        System.out.println("| OM: "+pack.originMac);
        System.out.println("| DM: "+pack.destinationMac);
        System.out.println("| OI: "+pack.originIp);
        System.out.println("| DI: "+pack.destinationIp);
        System.out.println("+-------------------+ \n");
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void sendPackage(String destinationIp, String message) {
        var destinationMac = addressTable.get(destinationIp);

        if (destinationMac == null) {
            originalPayload = message;
            var arpPack = new Package("REQUEST", this.macAddress, arpBroadcast, this.ipAddress, destinationIp);
            printPackage(arpPack);
            connectedSwitch.transmit(arpPack, this);
        }
        else {
            var pack = new Package(message, this.macAddress, destinationMac, this.ipAddress, destinationIp);
            printPackage(pack);
            connectedSwitch.transmit(pack, this);
            originalPayload = null;   
        }
    }

    public void receiveMessage(Package pack) {
        addressTable.put(pack.originIp, pack.originMac);

        if ((Objects.equals(pack.destinationIp, this.ipAddress))) {
            if (pack.destinationMac == this.macAddress)
                System.out.println(pack.payload);
            else if (pack.destinationMac == arpBroadcast){
                if (pack.payload == "REQUEST"){
                    Package arpReply = new Package("REPLY", this.macAddress, arpBroadcast, this.ipAddress, pack.originIp);
                    printPackage(arpReply);
                    connectedSwitch.transmit(arpReply, this);
                }
                else if (pack.payload == "REPLY") {
                    sendPackage(pack.originIp, originalPayload);
                }
            }
        }
    }
}
