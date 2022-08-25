public class Package {
    public String payload;
    public String originMac;
    public String destinationMac;
    public String originIp;
    public String destinationIp;

    public Package(String payload, String originMac, String destinationMac, String originIp, String destinationIp) {
        this.payload = payload;
        this.originMac = originMac;
        this.destinationMac = destinationMac;
        this.originIp = originIp;
        this.destinationIp = destinationIp;
    }
}
