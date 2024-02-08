package dhbw.mosbach.eventbus.events;

import lombok.Getter;

@Getter
public class EventBrake {
    private final int percentage;
    public EventBrake(int percentage){
        this.percentage = percentage;
    }
}
