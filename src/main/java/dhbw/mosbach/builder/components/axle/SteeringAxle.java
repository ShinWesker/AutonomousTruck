package dhbw.mosbach.builder.components.axle;

import dhbw.mosbach.builder.components.Brake;
import dhbw.mosbach.builder.enums.Position;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class SteeringAxle extends Axle {
    private int degree = 0;
    private Position position = Position.STRAIGHT;

    public SteeringAxle(Wheel[] wheels, Brake[] brakes) {
        super(wheels, brakes);
    }
}
