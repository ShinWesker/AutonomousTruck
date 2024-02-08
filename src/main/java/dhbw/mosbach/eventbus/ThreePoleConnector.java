package dhbw.mosbach.eventbus;

import com.google.common.eventbus.EventBus;
import dhbw.mosbach.builder.components.Brake;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.TurnSignal;
import lombok.Getter;

@Getter
public class ThreePoleConnector {
    EventBus brakeBus = new EventBus();
    EventBus turnSignalBus = new EventBus();
    EventBus brakeLightBus = new EventBus();

    public void addSubscriberBrakeBus(Brake brake){
        brakeBus.register(brake);
    }
    public void addSubscriberTurnSignalBus(TurnSignal turnSignal){
        turnSignalBus.register(turnSignal);
    }

    public void addSubscriberBrakeLightBus(BrakeLight brakeLight){
        brakeLightBus.register(brakeLight);
    }

}
