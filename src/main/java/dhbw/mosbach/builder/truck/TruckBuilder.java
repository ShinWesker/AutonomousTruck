package dhbw.mosbach.builder.truck;

import dhbw.mosbach.bridge.TruckBatteryControl;
import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.components.*;
import dhbw.mosbach.builder.components.chassis.TruckChassis;
import dhbw.mosbach.builder.components.axle.Axle;
import dhbw.mosbach.builder.components.axle.SteeringAxle;
import dhbw.mosbach.builder.components.axle.Wheel;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.HeadLight;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.enums.HorizontalPosition;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.composite.Battery;

public class TruckBuilder implements TruckVehicleBuilder {
    CentralUnit centralUnit = new CentralUnit();
    private Axle[] axles;
    private SteeringAxle steeringAxle;
    private Battery battery;
    private Cabine cabine;
    private Coupling coupling;

    TruckChassis.TruckChassisBuilder truckChassisBuilder = new TruckChassis.TruckChassisBuilder();

    @Override
    public void buildAxles() {
        axles = new Axle[2];

        for (int i = 0; i < axles.length + 1; i++) {
            Wheel[] wheels = {new Wheel(), new Wheel()};
            Brake[] brakes = {new Brake(), new Brake()};

            if (i < axles.length) {
                axles[i] = new Axle(wheels, brakes);
            } else {
                steeringAxle = new SteeringAxle(wheels, brakes);
            }
        }
        truckChassisBuilder.setSteeringAxle(steeringAxle);
        truckChassisBuilder.setAxles(axles);
    }

    @Override
    public void buildChassis() {
        battery = new Battery();
        TruckBatteryControl truckBatteryControl = new TruckBatteryControl(battery);

        coupling = new Coupling();

        truckChassisBuilder = new TruckChassis.TruckChassisBuilder()
                .setAxles(axles)
                .setSteeringAxle(steeringAxle)
                .setCoupling(coupling)
                .setEngine(new Engine(truckBatteryControl));

    }

    @Override
    public void buildCabin() {
        HeadLight[] headLights = {
                new HeadLight(Position.LEFT),
                new HeadLight(Position.RIGHT)};

        ExteriorMirror[] exteriorMirrors = {
                new ExteriorMirror(new Camera(), new Lidar(),Position.LEFT),
                new ExteriorMirror(new Camera(), new Lidar(),Position.RIGHT)
        };
        cabine = new Cabine(headLights, exteriorMirrors);
    }

    @Override
    public void attachCabin() {
        truckChassisBuilder.setCabine(cabine);
    }
    @Override
    public void buildSensory() {
        truckChassisBuilder
                .setBrakeLights(new BrakeLight[]{
                        new BrakeLight(Position.LEFT),
                        new BrakeLight(Position.RIGHT)})

                .setTurnSignals(new TurnSignal[]{
                        new TurnSignal(Position.LEFT, HorizontalPosition.FRONT),
                        new TurnSignal(Position.LEFT, HorizontalPosition.BACK),
                        new TurnSignal(Position.RIGHT, HorizontalPosition.FRONT),
                        new TurnSignal(Position.RIGHT, HorizontalPosition.BACK)
                });
    }
    @Override
    public void connectSensory() {
        coupling.getSensor().addListener(centralUnit);
    }

    @Override
    public AutonomousTruck getVehicle() {
        return new AutonomousTruck(truckChassisBuilder.createTruckChassis(), centralUnit);
    }
}
