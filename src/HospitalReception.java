import java.util.ArrayList;
import java.util.List;

public class HospitalReception {
    public static void main(String[] args){

        List<Delay> delays1 = new ArrayList<>();
        delays1.add(new Delay(15.0, "exp"));
        Create arrivalOfPatients = new Create("Arrival of patients", delays1, 1);

        List<Delay> delays2 = new ArrayList<>();
        delays2.add(new Delay(15.0, ""));
        delays2.add(new Delay(40.0, ""));
        delays2.add(new Delay(20.0, ""));
        Process reception = new Process("Reception", delays2, 2);

        List<Delay> delays3 = new ArrayList<>(); 
        delays3.add(new Delay(3.0, "unif"));
        delays3.add(new Delay(3.0, ""));
        Process transitionWard = new Process("Transition to the ward", delays3, 3);
        transitionWard.setDelay(0, 8.0);

        List<Delay> delays4 = new ArrayList<>();
        delays4.add(new Delay(2.0, "unif"));
        Process referralLaboratory = new Process("Referral to the laboratory", delays4, 1);
        referralLaboratory.setDelay(0, 5.0);

        List<Delay> delays5 = new ArrayList<>();
        delays5.add(new Delay(4.5, "erl"));
        Process registrationLaboratory = new Process("Registration in the laboratory", delays5, 1);
        registrationLaboratory.setDelay(0, 3.0);

        List<Delay> delays6 = new ArrayList<>();
        delays6.add(new Delay(4.0, "erl"));
        Process passingLaboratory = new Process("Passing tests in the laboratory", delays6, 2);
        passingLaboratory.setDelay(0, 2.0);

        List<Delay> delays7 = new ArrayList<>();
        delays7.add(new Delay(2.0, "unif"));
        Process returningRecaption= new Process("Returning to reception", delays7, 1);
        passingLaboratory.setDelay(0, 5.0);

        List<Process> elements1 = new ArrayList<>();
        elements1.add(reception);
        arrivalOfPatients.setNextElement(elements1);

        List<Process> elements2 = new ArrayList<>();
        elements2.add(transitionWard);
        elements2.add(referralLaboratory);
        reception.setNextElement(elements2);

        List<Process> elements3 = new ArrayList<>();
        elements3.add(registrationLaboratory);
        referralLaboratory.setNextElement(elements3);

        List<Process> elements4 = new ArrayList<>();
        elements4.add(passingLaboratory);
        registrationLaboratory.setNextElement(elements4);

        List<Process> elements5 = new ArrayList<>();
        elements5.add(returningRecaption);
        passingLaboratory.setNextElement(elements5);

        List<Process> elements6 = new ArrayList<>();
        elements6.add(reception);
        returningRecaption.setNextElement(elements6);

        ArrayList<Element> list = new ArrayList<>();
        list.add(arrivalOfPatients);
        list.add(reception);
        list.add(transitionWard);
        list.add(referralLaboratory);
        list.add(registrationLaboratory);
        list.add(passingLaboratory);
        list.add(returningRecaption);


        Model model = new Model(list);
        model.simulate(1440.0);

    }
    
}
