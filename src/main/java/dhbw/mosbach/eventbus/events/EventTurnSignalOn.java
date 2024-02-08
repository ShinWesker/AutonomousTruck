package dhbw.mosbach.eventbus.events;

import dhbw.mosbach.builder.enums.Position;
import lombok.Getter;

@Getter
public class EventTurnSignalOn {
    private  final Position position;
    public EventTurnSignalOn(Position position) {
                this.position = position;
    }
}
