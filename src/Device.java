public class Device {
    private double tnext;
    private int state;

    public Device(int state) {
        this.state = state;
    }

    public void inAct(double tnext) {
        state = 1;
        this.tnext = tnext;
    }

    public void outAct() {
        state = 0;
    }

    public double getTnext(){
        return tnext;
    }

    public double getState(){
        return state;
    }
    
}
