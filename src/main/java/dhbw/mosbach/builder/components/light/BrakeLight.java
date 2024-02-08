package dhbw.mosbach.builder.components.light;

import com.google.common.eventbus.Subscribe;
import dhbw.mosbach.builder.enums.HorizontalPosition;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.eventbus.events.EventBrakeLightOff;
import dhbw.mosbach.eventbus.events.EventBrakeLightOn;
import dhbw.mosbach.mediator.ITruckMediator;

public class BrakeLight extends ElectronicComponent {

    public BrakeLight(ITruckMediator mediator, Position position) {
        super(mediator,position, HorizontalPosition.BACK);
    }

    @Subscribe
    public void receive(EventBrakeLightOn brakeLightOn){
        isOn = true;
        System.out.println("Event triggered");
    }

    @Subscribe
    public void receive(EventBrakeLightOff brakeLightOff){
        isOn = false;
        System.out.println("Event triggered");
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
