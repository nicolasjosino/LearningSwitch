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
        var destinationHost = addressTable.get(pack.destinationMac);

        if (destinationHost != null)
            destinationHost.receiveMessage(pack);
        else broadcast(pack, caller);
    }

    private void broadcast(Package pack, Host caller) {
        for (Host h : hosts) {
            if (h != caller)
                h.receiveMessage(pack);
        }
    }
}
