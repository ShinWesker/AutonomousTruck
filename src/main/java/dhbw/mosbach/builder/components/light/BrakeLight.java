package dhbw.mosbach.builder.components.light;

import com.google.common.eventbus.Subscribe;
import dhbw.mosbach.enums.HorizontalPosition;
import dhbw.mosbach.enums.Position;
import dhbw.mosbach.eventbus.events.EventBrakeLightOff;
import dhbw.mosbach.eventbus.events.EventBrakeLightOn;
import dhbw.mosbach.mediator.ITruckMediator;

public class BrakeLight extends ElectronicComponent {
    public BrakeLight(ITruckMediator mediator, Position position) {
        super(mediator,position, HorizontalPosition.BACK);
    }

    @Subscribe
    public void receive(EventBrakeLightOn event){
        status = true;
    }

    @Subscribe
    public void receive(EventBrakeLightOff event){
        status = false;
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
