package dhbw.mosbach.builder.components;

import com.google.common.eventbus.Subscribe;
import dhbw.mosbach.eventbus.events.EventBrake;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Brake {
    private int percentage = 0;

    public void brake(int intensity){
        percentage = intensity;
    }

    @Subscribe
    public void receive(EventBrake event){
        this.percentage = event.getPercentage();
    }
}
