import java.util.ArrayList;
import java.util.HashMap;

public class Switch {
    private final HashMap<String,Integer> addressTable;
    private final Integer portsQuantity;
//    private final ArrayList<Host> hosts;

    public Switch(Integer portsQuantity) {
        this.addressTable = new HashMap<>();
        this.portsQuantity = portsQuantity;
//        this.hosts = new ArrayList<>(portsQuantity);
    }

    public HashMap<String, Integer> getAddressTable() {
        return addressTable;
    }

    public Integer getPortsQuantity() {
        return portsQuantity;
    }

//    public ArrayList<Host> getHosts() {
//        return hosts;
//    }

    public void transmit(Package pack) {
        //verificar como adicionar informação de mac com porta na tabela
    }
}
