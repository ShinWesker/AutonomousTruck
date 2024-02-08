package dhbw.mosbach.builder.components.light;

import com.google.common.eventbus.Subscribe;
import dhbw.mosbach.builder.enums.HorizontalPosition;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.eventbus.events.EventTurnSignalOff;
import dhbw.mosbach.eventbus.events.EventTurnSignalOn;
import dhbw.mosbach.mediator.ITruckMediator;

public class TurnSignal extends ElectronicComponent {

    public TurnSignal(ITruckMediator mediator, Position position, HorizontalPosition horizontalPosition) {
        super(mediator ,position, horizontalPosition);
    }

    @Override
    public void activate() {
        mediator.activate(this);
    }

    @Override
    public void deactivate() {
        mediator.deactivate(this);
    }

    @Subscribe
    public void receive(EventTurnSignalOn eventTurnSignalOn){
        if (position == eventTurnSignalOn.getPosition()) {
            status = true;
        }
        System.out.println("Event triggered");
    }
    @Subscribe
    public void receive(EventTurnSignalOff eventTurnSignalOff){
        if (position == eventTurnSignalOff.getPosition()) {
            status = false;
        }
        System.out.println("Event triggered");
    }

}
