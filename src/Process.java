import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Process extends Element {
    private int maxqueue, failure;
    private double meanQueue;
    private Queue<Patient> queue;
    private Map<Integer, List<Double>> patientDurations;

    public Process(String name, List<Delay> delays, int devices) {
        super(name, delays, devices);
        queue = new LinkedList<Patient>();
        maxqueue = Integer.MAX_VALUE;
        meanQueue = 0.0;
        patientDurations = new HashMap<>();
    }

    @Override
    public void inAct(Patient patient) {
        double duration = 0;
        for (Device device : super.getDevices()) {
            if (device.getState() == 0) {
                switch (super.getName()) {
                    case "Reception":
                        Time time = new Time(super.getTcurr());
                        double hours = time.getHours();
                        int type = patient.getType();
                        int flag = patient.getFlag();

                        if(hours >= 7 && hours <= 17) {
                            if(type==2 && flag==1){
                                device.inAct(super.getTcurr() + super.getNextDelay(type-1));
                                super.setTnext(super.getTcurr() + super.getNextDelay(type-1));
                                super.getNextElement(1).inAct(patient);
                            } else {
                                if(hours >= 7 && hours <= 16) {
                                    device.inAct(super.getTcurr() + super.getNextDelay(type-1));
                                    super.setTnext(super.getTcurr() + super.getNextDelay(type-1));

                                    if(type==1){
                                        super.getNextElement(0).inAct(patient);
                                    } else {
                                        super.getNextElement(1).inAct(patient);
                                    }
                                }
                            }
                        }
                        return;
                    case "Transition to the ward": 
                        device.inAct(super.getTcurr() + super.getNextDelay(0) + super.getNextDelay(1));
                        super.setTnext(super.getTcurr() + super.getNextDelay(0) + super.getNextDelay(1));

                        patient.setExit(super.getTcurr() + super.getNextDelay(0));
                        duration = patient.getDuration();
                        patientDurations.computeIfAbsent(patient.getType(), ArrayList::new).add(duration);

                        // System.out.println(patient.getPatient());

                        return;
                    case "Referral to the laboratory": 
                        device.inAct(super.getTcurr());
                        super.setTnext(super.getTcurr() + super.getNextDelay(0));
                        super.getNextElement(0).inAct(patient);
                        return;
                    case "Registration in the laboratory": 
                        System.out.println(patient.getPatient());
                        device.inAct(super.getTcurr() + super.getNextDelay(0));
                        super.setTnext(super.getTcurr() + super.getNextDelay(0));
                        super.getNextElement(0).inAct(patient);
                        return;
                    case "Passing tests in the laboratory": 
                        device.inAct(super.getTcurr() + super.getNextDelay(0));
                        super.setTnext(super.getTcurr() + super.getNextDelay(0));
                        if(patient.getType()==3){
                            patient.setExit(super.getTcurr() + super.getNextDelay(0));
                            duration = patient.getDuration();
                            patientDurations.computeIfAbsent(patient.getType(), ArrayList::new).add(duration);

                            System.out.println(patient.getPatient());
                        
                        } else {
                            super.getNextElement(0).inAct(patient);
                        }
                        return;
                    case "Returning to reception": 
                        device.inAct(super.getTcurr());
                        super.setTnext(super.getTcurr() + super.getNextDelay(0));
                        super.getNextElement(0).inAct(patient);
                        return;
                    default:
                        device.inAct(super.getTcurr() + super.getNextDelay(0));
                        super.setTnext(super.getTcurr() + super.getNextDelay(0));
                        return;
                }
                 
            }
        }

        if (getQueue().size() < getMaxqueue()) {
            setQueueElement(patient);
        } else {
            failure++; 
        }
    }

    @Override
    public void outAct() {
        super.outAct();
        for (Device device : super.getDevices()) {
            if (device.getTnext() <= super.getTcurr() && device.getState() == 1) {

                device.outAct();

                if (getQueue().size() > 0) {
                    
                    Patient patient = getQueueElement();
                    double duration = 0;

                    switch (super.getName()) {
                        case "Reception":
                            Time time = new Time(super.getTcurr());
                            double hours = time.getHours();
                            int type = patient.getType();

                            if(hours >= 7 && hours <= 17) {
                                if(type==2){
                                    device.inAct(super.getTcurr() + super.getNextDelay(type-1));
                                    super.setTnext(super.getTcurr() + super.getNextDelay(type-1));
                                    super.getNextElement(1).inAct(patient);
                                } else {
                                    if(hours >= 7 && hours <= 16) {
                                        double delay = super.getNextDelay(patient.getType()-1);
                                        device.inAct(super.getTcurr() + delay);
                                        super.setTnext(super.getTcurr() + delay);
                                        if(type==1){
                                            super.getNextElement(0).inAct(patient);
                                        } else {
                                            super.getNextElement(1).inAct(patient);
                                        }
                                    }

                                }
                            }
                            return;
                        case "Transition to the ward": 
                            patient.setExit(super.getTcurr() + super.getNextDelay(0));
                            duration = patient.getDuration();
                            patientDurations.computeIfAbsent(patient.getType(), ArrayList::new).add(duration);
                            device.inAct(super.getTcurr() + super.getNextDelay(0) + super.getNextDelay(1));
                            super.setTnext(super.getTcurr() + super.getNextDelay(0) + super.getNextDelay(1));
                            return;
                        case "Registration in the laboratory": 
                            device.inAct(super.getTcurr() + super.getNextDelay(0));
                            super.setTnext(super.getTcurr() + super.getNextDelay(0));
                            super.getNextElement(0).inAct(patient);
                            return;
                        case "Passing tests in the laboratory": 
                            device.inAct(super.getTcurr() + super.getNextDelay(0));
                            super.setTnext(super.getTcurr() + super.getNextDelay(0));
                            if(patient.getType()==3){
                                patient.setExit(super.getTcurr() + super.getNextDelay(0));
                                duration = patient.getDuration();
                                patientDurations.computeIfAbsent(patient.getType(), ArrayList::new).add(duration);
                            } else {
                                super.getNextElement(0).inAct(patient);
                            }
                            return;
                        default:
                            patient.setExit(super.getTcurr());
                            duration = patient.getDuration();
                            patientDurations.computeIfAbsent(patient.getType(), ArrayList::new).add(duration);
                            device.inAct(super.getTcurr() + super.getNextDelay(0));
                            super.setTnext(super.getTcurr() + super.getNextDelay(0));
                            return;
                    }
                }
                return; 
            }

        }
        super.setTnext(Double.MAX_VALUE);
    }

    public int getFailure() {
        return failure;
    }
    public Queue<Patient> getQueue() {
        return queue;
    }
    public void setQueueElement(Patient element) {
        this.queue.add(element);
    }
    public int getMaxqueue() {
        return maxqueue;
    }
    public Patient getQueueElement() {
        return queue.remove();
    }
    public void setMaxqueue(int maxqueue) {
        this.maxqueue = maxqueue;
    }

    @Override
    public void doStatistics(double delta) {
        meanQueue = getMeanQueue() + queue.size() * delta;
    }

    public double getMeanQueue() {
        return meanQueue;
    }

    public void durations() {
        for (Map.Entry<Integer, List<Double>> entry : patientDurations.entrySet()) {
            int type = entry.getKey();
            List<Double> durations = entry.getValue();
        
            double mean = durations.stream()
                                   .mapToDouble(Double::doubleValue)
                                   .average()
                                   .orElse(0.0);
        
            String s = "Type " + type + " = Mean Value: " + mean;
            System.out.println(s);
        }
    }
}
