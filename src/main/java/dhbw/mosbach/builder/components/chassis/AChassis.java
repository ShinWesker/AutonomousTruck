package dhbw.mosbach.builder.components.chassis;

import dhbw.mosbach.builder.components.axle.Axle;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.TurnSignal;
import lombok.Getter;

@Getter
public abstract class AChassis {
    protected final BrakeLight[] brakeLights;
    protected final Axle[] axles;
    protected final TurnSignal[] turnSignals;

    protected AChassis(BrakeLight[] brakeLights, Axle[] axles, TurnSignal[] turnSignals) {
        this.brakeLights = brakeLights;
        this.axles = axles;
        this.turnSignals = turnSignals;
    }

}
