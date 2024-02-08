package dhbw.mosbach.builder.components;

import com.google.common.eventbus.Subscribe;
import dhbw.mosbach.eventbus.events.EventBrake;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Brake {
    private int percentage = 0;


    @Subscribe
    public void receive(EventBrake eventBrake){
        this.percentage = eventBrake.getPercentage();
        System.out.println("Event triggered");
    }
}
