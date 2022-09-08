public class Cable {
    private final Port portA;
    private final Port portB;

    public Cable(Port portA, Port portB) {
        this.portA = portA;
        this.portB = portB;
    }

    public void transfer(Packet pack, Port sender) {
        if (sender == portA)
            portB.recv(pack);
        else
            portA.recv(pack);
    }
}
