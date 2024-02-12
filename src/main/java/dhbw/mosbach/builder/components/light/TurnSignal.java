package dhbw.mosbach.builder.components.light;

import com.google.common.eventbus.Subscribe;
import dhbw.mosbach.enums.HorizontalPosition;
import dhbw.mosbach.enums.Position;
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
    public void receive(EventTurnSignalOn event){
        if (position == event.getPosition()) {
            status = true;
        }
    }
    @Subscribe
    public void receive(EventTurnSignalOff event){
        if (position == event.getPosition()) {
            status = false;
        }
    }

}
