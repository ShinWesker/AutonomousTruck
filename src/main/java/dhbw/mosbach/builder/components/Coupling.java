package dhbw.mosbach.builder.components;

import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.observer.CouplingSensor;
import lombok.Getter;

@Getter
public class Coupling {
    private final CouplingSensor sensor = new CouplingSensor();
    private Trailer trailer;

    public void connect(Trailer trailer) {
        this.trailer = trailer;
        sensor.detect();
    }
}
