public class FunRand {
    public static double Exp(double timeMean) {
        double a = 0;

        while (a == 0) {
            a = Math.random();
        }

        a = -timeMean * Math.log(a);

        return a;
    }

    public static double Unif(double timeMin, double timeMax) {
        double a = 0;

        while (a == 0) {
            a = Math.random();
        }

        a = timeMin + a * (timeMax - timeMin);

        return a;
    }

    public static double Erl(double timeMean, double k) {
        double a = 0;

        for (int i = 0; i < k; i++) {
            double u = 0;
            while (u == 0) {
                u = Math.random();
            }
            
            a += Math.log(u);;
        }

        a = -1 / timeMean * a;
        
        return a;
    }
}
