import java.util.ArrayList;
import java.util.List;

public class Element {
    private String name;
    private double tnext;
    private double tcurr;
    private int quantity;
    private List<Delay> delays;
    private List<Device> devices;
    private List<Process> nextElement;
    private static int nextId=0;
    private int id;

    public Element(String nameOfElement, List<Delay> delays, int numberOfDevices){
        this.name = nameOfElement;
        this.tnext = 0.0;
        this.delays = delays;
        this.tcurr = tnext;
        this.devices = new ArrayList<>();
        for (int i = 0; i < numberOfDevices; i++) {
            this.devices.add(new Device(0));
        }
        this.nextElement=null;
        this.id = nextId;
        nextId++;
    }
    public double getNextDelay(int index) {
        if (delays == null || delays.isEmpty()) {
            throw new IllegalStateException("No delays configured for this element.");
        }
        Delay delay = delays.get(index);

        switch (delay.getDistribution().toLowerCase()) {
            case "exp":
                return FunRand.Exp(delay.getDelay());
            case "erl":
                return FunRand.Erl(delay.getDelay(), delay.getDelayDev());
            case "unif":
                return FunRand.Unif(delay.getDelay(), delay.getDelayDev());
            default:
                return delay.getDelay(); 
        }
    }
    public void setDelay(int index, double delayDev){
        Delay delay = delays.get(index);
        delay.setDelayDev(delayDev);
    }
    public int getQuantity() {
        return quantity;
    }
    public double getTcurr() {
        return tcurr;
    }
    public void setTcurr(double tcurr) {
        this.tcurr = tcurr;
    }

    public List<Device> getDevices(){
        return devices;
    }

    public Element getNextElement(int index) {
        return nextElement.get(index);
    }
    public void setNextElement(List<Process> nextElement) {
        this.nextElement = nextElement;
    }
    public void inAct(Patient patient){
    }
    public void outAct(){
        quantity++;
    }
    public double getTnext() {
        return tnext;
    }
    public void setTnext(double tnext) {
        this.tnext = tnext;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void printResult(){
        System.out.println(getName()+ " quantity = "+ quantity);
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void doStatistics(double delta){

    }
}
