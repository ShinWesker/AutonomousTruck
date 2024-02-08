package dhbw.mosbach.observer;


public class CouplingSensor extends ASensor {
    public void detect(){
        for (ISensoricListener l: listeners
             ) {
            l.detect();
        }
    }
}
