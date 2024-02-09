package dhbw.mosbach.builder.components;

import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.observer.HoldingAreaSensor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class HoldingArea {
    private Pallet pallet;
    private int index;
    private final Position position;
    private final HoldingAreaSensor sensor;

    public HoldingArea(int index, Position position){
        this.position = position;
        this.index = index;
        sensor = new HoldingAreaSensor();
    }

    public void load(Pallet pallet) {
        this.pallet = pallet;
        sensor.detect(this);
    }
    public void unload() {
        pallet = null;
        System.out.println("Pallet unloaded");
        sensor.detect(this);
    }
}
