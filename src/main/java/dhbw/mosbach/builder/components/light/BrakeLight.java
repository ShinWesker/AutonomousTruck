package dhbw.mosbach.builder.components.light;

import com.google.common.eventbus.Subscribe;
import dhbw.mosbach.builder.enums.HorizontalPosition;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.eventbus.events.EventBrake;
import dhbw.mosbach.eventbus.events.EventBrakeLightOff;
import dhbw.mosbach.eventbus.events.EventBrakeLightOn;

public class BrakeLight extends ALight {

    public BrakeLight(Position position) {
        super(position, HorizontalPosition.BACK);
    }

    @Subscribe
    public void receive(EventBrakeLightOn brakeLightOn){
        activate();
        System.out.println("Event triggered");
    }

    @Subscribe
    public void receive(EventBrakeLightOff brakeLightOff){
        deactivate();
        System.out.println("Event triggered");
    }
}
