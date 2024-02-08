package dhbw.mosbach.mediator;

import dhbw.mosbach.builder.components.light.Camera;
import dhbw.mosbach.builder.components.light.Lidar;
import dhbw.mosbach.builder.components.light.ElectronicComponent;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.HeadLight;
import dhbw.mosbach.builder.components.light.TurnSignal;

public interface ITruckMediator {
    void addBrakeLight(BrakeLight brakeLight);
    void addTurnSignal(TurnSignal turnSignal);
    void addHeadLight(HeadLight headLight);
    void addCamera(Camera camera);
    void addLidar(Lidar lidar);
    void activate(ElectronicComponent electronicComponent);
    void deactivate(ElectronicComponent electronicComponent);

}
