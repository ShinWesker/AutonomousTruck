package dhbw.mosbach.observer;

import dhbw.mosbach.builder.components.HoldingArea;

public class HoldingAreaSensor extends Sensor {
    public void detect(HoldingArea area){
        for (ISensoricListener l: listeners
             ) {
            l.detect(area);
        }
    }
}
