public class Delay {
    private double delay;
    private double delayDev;
    private String distribution;

    public Delay(double delay, String distribution){
        this.delay = delay;
        this.distribution = distribution;
    }

    public double getDelay() {
        return delay;
    }

    public double getDelayDev() {
        return delayDev;
    }
    public void setDelayDev(double delayDev) {
        this.delayDev = delayDev;
    }
    public String getDistribution() {
        return distribution;
    }
    public void setDistribution(String distribution) {
        this.distribution = distribution;
    }
}
