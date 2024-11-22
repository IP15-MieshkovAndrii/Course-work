public class Time {
    private double minutesOfDay;
    private double hours, minutes, seconds;

    public Time(double minutes) {
        minutesOfDay = minutes;
    }

    public String toHHMMSS() {
        hours = Math.floor((minutesOfDay / 60) % 24);
        minutes = Math.floor(minutesOfDay % 60);
        seconds = ((minutesOfDay % 60) - minutes) * 60 / 100;
        return String.format("%02.0f:%02.0f:%02.0f", hours, minutes, seconds);
    }

    public void setMinutes(double minutes) {
        minutesOfDay = minutes;
    }

    public double getHours() {
        return (minutesOfDay / 60) % 24;
    }
    public double getNightDelay() {
        double minutesUntilSeven = 0;

        if (hours < 7) {
            minutesUntilSeven = (7 * 60) - (hours * 60 + minutes);
        } else {
            minutesUntilSeven = (24 * 60) - (hours * 60 + minutes) + (7 * 60);
        }

        return minutesUntilSeven; 
    }
    
}
