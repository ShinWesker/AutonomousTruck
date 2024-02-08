package dhbw.mosbach.mediator;

import dhbw.mosbach.builder.components.light.Camera;
import dhbw.mosbach.builder.components.light.Lidar;
import dhbw.mosbach.builder.components.light.ElectronicComponent;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.HeadLight;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import dhbw.mosbach.eventbus.events.EventBrakeLightOff;
import dhbw.mosbach.eventbus.events.EventBrakeLightOn;
import dhbw.mosbach.eventbus.events.EventTurnSignalOff;
import dhbw.mosbach.eventbus.events.EventTurnSignalOn;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TruckMediator implements ITruckMediator {
    @Setter
    private AutonomousTruck truck;
    List<BrakeLight> brakeLights;
    List<TurnSignal> turnSignals;
    List<HeadLight> headLights;
    List<Camera> cameras;
    List<Lidar> lidars;

    public TruckMediator(){
        brakeLights = new ArrayList<>();
        turnSignals = new ArrayList<>();
        headLights = new ArrayList<>();
        cameras = new ArrayList<>();
        lidars = new ArrayList<>();
    }

    @Override
    public void addBrakeLight(BrakeLight brakeLight) {
        brakeLights.add(brakeLight);
    }

    @Override
    public void addTurnSignal(TurnSignal turnSignal) {
        turnSignals.add(turnSignal);
    }

    @Override
    public void addHeadLight(HeadLight headLight) {
        headLights.add(headLight);
    }

    @Override
    public void addCamera(Camera camera) {
        cameras.add(camera);
    }

    @Override
    public void addLidar(Lidar lidar) {
        lidars.add(lidar);
    }

    @Override
    public void activate(ElectronicComponent electronicComponent) {
        switch (electronicComponent) {
            case Camera camera:
                turnOnComponents(cameras);
                break;
            case Lidar lidar:
                turnOnComponents(lidars);
                break;
            case TurnSignal turnSignal:
                for (TurnSignal t : turnSignals){
                    if (t.getPosition() == turnSignal.getPosition()) {
                        t.setStatus(true);
                    }
                }
                if (truck.getConnected()) {
                    truck.getThreePoleConnector().getTurnSignalBus().post(new EventTurnSignalOn(turnSignal.getPosition()));
                }
                break;
            case HeadLight headLight:
                turnOnComponents(headLights);
                break;
            case BrakeLight brakeLight:
                turnOnComponents(brakeLights);
                if (truck.getConnected()) {
                    truck.getThreePoleConnector().getBrakeLightBus().post(new EventBrakeLightOn());
                }
            default:
                throw new IllegalStateException("Unexpected value: " + electronicComponent);
        }
    }
    @Override
    public void deactivate(ElectronicComponent electronicComponent) {
        switch (electronicComponent) {
            case Camera camera:
                turnOffComponents(cameras);
                break;
            case Lidar lidar:
                turnOffComponents(lidars);
                break;
            case TurnSignal turnSignal:
                for (TurnSignal t : turnSignals){
                    if (t.getPosition() == turnSignal.getPosition()) {
                        t.setStatus(false);
                    }
                }
                if (truck.getConnected()) {
                    truck.getThreePoleConnector().getTurnSignalBus().post(new EventTurnSignalOff(turnSignal.getPosition()));
                }
                break;
            case HeadLight headLight:
                turnOffComponents(headLights);
                break;
            case BrakeLight brakeLight:
                turnOffComponents(brakeLights);
                if (truck.getConnected()) {
                    truck.getThreePoleConnector().getBrakeLightBus().post(new EventBrakeLightOff());
                }
            default:
                throw new IllegalStateException("Unexpected value: " + electronicComponent);
        }
    }
    private <T extends ElectronicComponent> void turnOnComponents(Collection<T> components) {
        components.forEach(component -> component.setStatus(true));
    }

    private <T extends ElectronicComponent> void turnOffComponents(Collection<T> components) {
        components.forEach(component -> component.setStatus(false));
    }


}



