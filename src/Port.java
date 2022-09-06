import java.util.LinkedList;

public class Port {
    private Cable cable;
    LinkedList<Packet> received;
    LinkedList<Packet> sendList;

    public Port() {
        this.cable = null;
        this.received = new LinkedList<>(); 
        this.sendList = new LinkedList<>();
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

    public LinkedList<Packet> getSent() {
        return this.sendList;
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
