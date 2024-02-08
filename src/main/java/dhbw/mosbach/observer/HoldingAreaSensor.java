package dhbw.mosbach.observer;

import dhbw.mosbach.builder.components.HoldingArea;

public class HoldingAreaSensor extends ASensor {
    public void detect(HoldingArea holdingArea, String s){
        for (ISensoricListener l: listeners
             ) {
            l.detect(holdingArea,s);
        }
    }
}
