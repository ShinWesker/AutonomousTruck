package dhbw.mosbach.builder.components.axle;

import dhbw.mosbach.builder.components.Brake;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Axle {
    protected Wheel[] wheels;
    protected Brake[] brakes;

    public Axle(Wheel[] wheels, Brake[] brakes) {
        this.wheels = wheels;
        this.brakes = brakes;
    }
}
