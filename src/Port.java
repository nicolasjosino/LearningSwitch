import java.util.LinkedList;

public class Port {
    private Cable cable;
    private LinkedList<Packet> received;
    private LinkedList<Packet> sendList;
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

    public void setCable(Port port) {
        // if (port == null) {
        //     Exceção para tentativa de conexão com porta inexistente
        // }
        var cable = new Cable(this, port);
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
        }
    }

    public void recv(Packet pack) {
        received.add(pack);
    }
    
}
