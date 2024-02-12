package dhbw.mosbach.observer;


public class CouplingSensor extends Sensor {
    public void detect(){
        for (ISensoricListener l: listeners
             ) {
            l.detect();
        }
    }
}
