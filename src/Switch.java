import java.util.ArrayList;
import java.util.HashMap;

public class Switch {
    private final HashMap<String, Port> addressTable;
    private final Integer portsQuantity;
    private final ArrayList<Port> ports;

    public Switch(Integer portsQuantity) {
        this.addressTable = new HashMap<>();
        this.portsQuantity = portsQuantity;
        this.ports = new ArrayList<>(portsQuantity);
    }

    public HashMap<String, Port> getAddressTable() {
        return addressTable;
    }

    public Integer getPortsQuantity() {
        return portsQuantity;
    }

    public ArrayList<Port> getPorts() {
        return ports;
    }

    public void transmit(Packet pack, Port caller) {
        addressTable.put(pack.originMac, caller);
        var destination = addressTable.get(pack.destinationMac);

        if (destination != null)
            destination.send(pack);
        else broadcast(pack, caller);
    }

    private void broadcast(Packet pack, Port caller) {
        for (Port p : ports) {
            if (p != caller)
                p.send(pack);
        }
    }
}
