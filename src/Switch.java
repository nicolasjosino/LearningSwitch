import java.util.ArrayList;
import java.util.HashMap;

public class Switch {
    private final HashMap<String, Host> addressTable;
    private final Integer portsQuantity;
    private final ArrayList<Host> hosts;

    public Switch(Integer portsQuantity) {
        this.addressTable = new HashMap<>();
        this.portsQuantity = portsQuantity;
        this.hosts = new ArrayList<>(portsQuantity);
    }

    public HashMap<String, Host> getAddressTable() {
        return addressTable;
    }

    public Integer getPortsQuantity() {
        return portsQuantity;
    }

    public ArrayList<Host> getHosts() {
        return hosts;
    }

    public void transmit(Package pack, Host caller) {
        addressTable.put(pack.originMac, caller);

        if (addressTable.containsKey(pack.destinationMac) && (!pack.destinationMac.equals("FF:FF:FF:FF")))
            addressTable.get(pack.destinationMac).receiveMessage(pack);
        else broadcast(pack);
    }

    private void broadcast(Package pack) {
        for (Host h : hosts) {
            h.receiveMessage(pack);
        }
    }
}
