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
//        connectedSwitch.getHosts().add(this);
    }

    public String getMacAddress() {
        return macAddress;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void sendPackage(String destinationIp, String message) {
        // checar quem será chave e quem será valor (IP MUDA)
        var destinationMac = addressTable.get(destinationIp);

        if (destinationMac.isEmpty()) {
            destinationMac = "FF:FF:FF:FF";
        }
        var pack = new Package(message, this.macAddress, destinationMac, this.ipAddress, destinationIp);

//        sw.transmit(pack);
    }

    public void receiveMessage(Package pack) {
        addressTable.put(pack.originIp, pack.originMac);

        if ((Objects.equals(pack.destinationIp, this.ipAddress)) && (Objects.equals(pack.destinationMac, this.macAddress))) {
            //ENVIAR RESPOSTA (ARP REPLY)
            System.out.println(pack.payload);
        }
    }
}
