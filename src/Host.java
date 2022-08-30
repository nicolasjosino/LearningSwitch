import java.util.HashMap;
import java.util.Objects;

public class Host {
    private final String macAddress;
    private String ipAddress;
    private Switch connectedSwitch;
    private final HashMap<String, String> addressTable;

    public Host(String macAddress, String ipAddress, Switch connectedSwitch) {
        this.macAddress = macAddress;
        this.ipAddress = ipAddress;
        this.addressTable = new HashMap<>();
        if (connectedSwitch.getHosts().size() < connectedSwitch.getPortsQuantity()) {
            this.connectedSwitch = connectedSwitch;
            connectedSwitch.getHosts().add(this);
        }
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
            destinationMac = "FF:FF:FF:FF";
        }
        var pack = new Package(message, this.macAddress, destinationMac, this.ipAddress, destinationIp);

        connectedSwitch.transmit(pack, this);
    }

    public void receiveMessage(Package pack) {
        addressTable.put(pack.originIp, pack.originMac);

        if ((Objects.equals(pack.destinationIp, this.ipAddress)) ||
                (Objects.equals(pack.destinationMac, this.macAddress))) {
//            sendPackage(pack.originIp, "Received");
            System.out.println(pack.payload);
        }
    }
}
