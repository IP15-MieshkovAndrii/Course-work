import java.util.List;

public class Create extends Element{

    public Create(String name, List<Delay> delays, int devices) {
        super(name, delays, devices);
        super.setTnext(0.0);
    }
        
    @Override
    public void outAct() {
        super.outAct();
        Time time = new Time(super.getTcurr());
        double hours = time.getHours();
        double p1,p2,p3;
        if(hours >= 7 && hours <= 10 ){
            p1 = 0.9;
            p2 = 0.1;
            p3 = 0;
        } else {
            p1 = 0.5;
            p2 = 0.1;
            p3 = 0.4;
        }

        Patient newPatient = generatePatient(p1, p2, p3);
        newPatient.setArrival(super.getTcurr());

        if(hours >= 7 && hours <= 17) {
            // System.out.println("New patient arrived: " + newPatient.getType() + " at " + time.toHHMMSS());
            super.setTnext(super.getTcurr() + super.getNextDelay(0));
            super.getNextElement(0).inAct(newPatient);
        } else {
            // System.out.println("New patient arrived: " + newPatient.getType() + " at " + time.toHHMMSS());
            super.setTnext(super.getTcurr() + time.getNightDelay());
            super.getNextElement(0).inAct(newPatient);
        }

    }

    public Patient generatePatient(double p1, double p2, double p3) {
        int type = 0;
        double rand = Math.random();

        if (rand < p1) {
            type = 1; 
        } else if (rand < p1 + p2) {
            type = 2; 
        } else {
            type = 3;
        }

        Patient newPatient = new Patient(type, super.getTcurr());

        return newPatient;
    }
}
