package dhbw.mosbach.builder.components.light;

import com.google.common.eventbus.Subscribe;
import dhbw.mosbach.builder.enums.HorizontalPosition;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.eventbus.events.EventBrakeLightOn;
import dhbw.mosbach.eventbus.events.EventTurnSignalOff;
import dhbw.mosbach.eventbus.events.EventTurnSignalOn;

public class TurnSignal extends ALight{

    public TurnSignal(Position position, HorizontalPosition horizontalPosition) {
        super(position, horizontalPosition);
    }
    @Subscribe
    public void receive(EventTurnSignalOn eventTurnSignalOn){
        if (position == eventTurnSignalOn.getPosition()) {
            activate();
        }
        System.out.println("Event triggered");
    }
    @Subscribe
    public void receive(EventTurnSignalOff eventTurnSignalOff){
        if (position == eventTurnSignalOff.getPosition()) {
            deactivate();
        }
        System.out.println("Event triggered");
    }

}
