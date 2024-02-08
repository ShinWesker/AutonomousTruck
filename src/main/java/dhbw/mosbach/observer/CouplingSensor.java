package dhbw.mosbach.observer;

import dhbw.mosbach.builder.trailer.Trailer;

public class CouplingSensor extends ASensor {
    public void detect(){
        for (ISensoricListener l: listeners
             ) {
            l.detect();
        }
    }
}
