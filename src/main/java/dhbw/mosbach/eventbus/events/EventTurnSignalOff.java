package dhbw.mosbach.eventbus.events;

import dhbw.mosbach.builder.enums.Position;
import lombok.Getter;

@Getter
public class EventTurnSignalOff {
    public final Position position;
    public EventTurnSignalOff(Position position){
        this.position = position;
    }
}
