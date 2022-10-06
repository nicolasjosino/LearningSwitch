import java.util.LinkedList;

public class Port {
    private Cable cable;
    private final LinkedList<Packet> received;
    private final LinkedList<Packet> sendList;
    private boolean connected;

    public Port() {
        this.cable = null;
        this.received = new LinkedList<>();
        this.sendList = new LinkedList<>();
        this.connected = false;
    }

    public Cable getCable() {
        return this.cable;
    }

    public void setCable(Cable cable) {
        this.cable = cable;
    }

    public LinkedList<Packet> getReceived() {
        return this.received;
    }

    public LinkedList<Packet> getSendList() {
        return this.sendList;
    }

    public boolean isConnected() {
        return this.connected;
    }

    public void connect() {
        this.connected = true;
    }

    public void send(Packet pack) {
        sendList.add(pack);
        for (Packet packet : sendList) {
            cable.transfer(packet, this);
            sendList.remove();
        }
    }

    public void recv(Packet pack) {
        received.add(pack);
    }
    
}
