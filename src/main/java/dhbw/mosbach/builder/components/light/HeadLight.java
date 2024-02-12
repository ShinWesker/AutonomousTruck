package dhbw.mosbach.builder.components.light;

import dhbw.mosbach.enums.HorizontalPosition;
import dhbw.mosbach.enums.Position;
import dhbw.mosbach.mediator.ITruckMediator;

public class HeadLight extends ElectronicComponent {
    public HeadLight(ITruckMediator mediator, Position position) {
        super(mediator,position, HorizontalPosition.FRONT);
    }

    @Override
    public void activate() {
        mediator.activate(this);
    }

    @Override
    public void deactivate() {
        mediator.deactivate(this);
    }
}
