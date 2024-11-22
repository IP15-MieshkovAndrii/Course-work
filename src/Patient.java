public class Patient {
    private int type;
    private double arrivalTime;
    private double exit;
    private double duration;
    private int isPassed;

    public Patient(int type, double time) {
        this.type = type;
        arrivalTime = time;
        duration = 0;
        exit = 0;
        if(this.type == 1) {
            isPassed = 1;
        } else {
            isPassed = 0;
        }
    }

    public int getType() {
        return type;
    }
    public void setType(int newType) {
        this.type = newType;
    }
    public double getDuration() {
        return duration;
    }
    public void setExit(double exit) {
        this.exit = exit;
        this.duration+= this.exit - this.arrivalTime;
    }
    public void setArrival(double arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getPatient() {
        String patiet = "Type: " + type + ", arrived at:" + arrivalTime + ", exit at:" + exit + ", duration: " + duration;
        return patiet;
    }

    public void setFlag(int isPassed) {
        this.isPassed = isPassed;
    }
    public int getFlag() {
        return isPassed;
    }
}
