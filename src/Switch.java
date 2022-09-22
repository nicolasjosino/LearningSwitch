import java.util.ArrayList;
import java.util.HashMap;

public class Switch extends Thread {
    private final HashMap<String, Port> addressTable;
    private Integer portsQuantity;
    private ArrayList<Port> ports;

    public Switch(Integer portsQuantity) {
        this.addressTable = new HashMap<>();
        this.portsQuantity = portsQuantity;
        this.ports = new ArrayList<>();
        setPorts(this.portsQuantity);
        start();
    }

    private void setPorts(Integer numbersPorts) {
        for (int i = 0; i < numbersPorts; i++) {
            Port port = new Port();
            this.ports.add(port);
        }

    }

    public Port getAvailablePort() {
        for (Port port : ports) {
            if (!port.isConnected())
                return port;
        }
        return null;
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

    @Override
    public void run() {

        while (true) {
            for (Port p : ports) {
                if (!p.getReceived().isEmpty()) {
                    transmit(p.getReceived().getFirst(), p);
                    p.getReceived().removeFirst();
                }
            }
        }
    }

    public void transmit(Packet pack, Port caller) {
        addressTable.put(pack.originMac, caller);
        Port destination = addressTable.get(pack.destinationMac);

        if (destination != null)
            destination.send(pack);
        else broadcast(pack, caller);
    }

    private void broadcast(Packet pack, Port caller) {
        for (Port p : ports) {
            if ((p != caller) && (p.isConnected()))
                p.send(pack);
        }
    }
}