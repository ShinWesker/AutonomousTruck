package dhbw.mosbach.eventbus;

import com.google.common.eventbus.EventBus;
import dhbw.mosbach.builder.components.Brake;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.TurnSignal;
import lombok.Getter;

@Getter
public class ThreePoleConnector {
    private EventBus brakeBus = new EventBus();
    private EventBus turnSignalBus = new EventBus();
    private EventBus brakeLightBus = new EventBus();

    public void subscribeBrakeBus(Brake brake){
        brakeBus.register(brake);
    }
    public void subscribeTurnSignalBus(TurnSignal turnSignal){
        turnSignalBus.register(turnSignal);
    }

    public void subscribeBrakeLightBus(BrakeLight brakeLight){
        brakeLightBus.register(brakeLight);
    }

}
