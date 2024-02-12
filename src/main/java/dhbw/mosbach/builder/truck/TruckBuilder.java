package dhbw.mosbach.builder.truck;

import dhbw.mosbach.bridge.TruckBatteryControl;
import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.components.*;
import dhbw.mosbach.builder.components.chassis.TruckChassis;
import dhbw.mosbach.builder.components.axle.Axle;
import dhbw.mosbach.builder.components.axle.SteeringAxle;
import dhbw.mosbach.builder.components.axle.Wheel;
import dhbw.mosbach.builder.components.light.*;
import dhbw.mosbach.builder.enums.HorizontalPosition;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.composite.Battery;
import dhbw.mosbach.mediator.ITruckMediator;
import dhbw.mosbach.mediator.TruckMediator;

public class TruckBuilder implements TruckVehicleBuilder {
    CentralUnit centralUnit;

    private SteeringAxle steeringAxle;

    private Cabine cabine;
    private Coupling coupling;
    private final ITruckMediator mediator;

    TruckChassis.TruckChassisBuilder truckChassisBuilder = new TruckChassis.TruckChassisBuilder();

    public TruckBuilder(CentralUnit centralUnit, ITruckMediator mediator){
        this.centralUnit = centralUnit;
        this.mediator = mediator;
    }

    @Override
    public void buildAxles() {
        Axle[] axles;
        axles = new Axle[2];

        for (int i = 0; i < axles.length + 1; i++) {
            Wheel[] wheels = {new Wheel(), new Wheel()};
            Brake[] brakes = {new Brake(), new Brake()};

            if (i < axles.length) {
                axles[i] = new Axle(wheels, brakes);
            } else {
                steeringAxle = new SteeringAxle(wheels, brakes);
            }
            truckChassisBuilder.setSteeringAxle(steeringAxle);
            truckChassisBuilder.setAxles(axles);
        }
    }

    @Override
    public void buildChassis() {
        Battery battery;
        battery = new Battery();
        TruckBatteryControl truckBatteryControl = new TruckBatteryControl(battery);
        coupling = new Coupling();

        truckChassisBuilder
                .setCoupling(coupling)
                .setEngine(new Engine(truckBatteryControl));
    }

    @Override
    public void buildCabin() {
        HeadLight[] headLights = {
                new HeadLight(mediator,Position.LEFT),
                new HeadLight(mediator,Position.RIGHT)};

        for (HeadLight headLight : headLights) {
            mediator.addHeadLight(headLight);
        }

        Camera cameraLeft = new Camera(mediator, Position.LEFT);
        Camera cameraRight = new Camera(mediator, Position.RIGHT);

        mediator.addCamera(cameraLeft);
        mediator.addCamera(cameraRight);

        Lidar lidarLeft = new Lidar(mediator, Position.LEFT);
        Lidar lidarRight = new Lidar(mediator, Position.RIGHT);

        mediator.addLidar(lidarLeft);
        mediator.addLidar(lidarRight);

        ExteriorMirror[] exteriorMirrors = {
                new ExteriorMirror(cameraLeft, lidarLeft, Position.LEFT),
                new ExteriorMirror(cameraRight, lidarRight, Position.RIGHT)
        };
        cabine = new Cabine(headLights, exteriorMirrors);
    }

    @Override
    public void attachCabin() {
        truckChassisBuilder.setCabine(cabine);
    }

    @Override
    public void buildSensory() {
        BrakeLight[] brakeLights = {
                new BrakeLight(mediator, Position.LEFT),
                new BrakeLight(mediator, Position.RIGHT)};

        for (BrakeLight brakeLight : brakeLights) {
            mediator.addBrakeLight(brakeLight);
        }

        TurnSignal[] turnSignals = {
                new TurnSignal(mediator, Position.LEFT, HorizontalPosition.FRONT),
                new TurnSignal(mediator, Position.LEFT, HorizontalPosition.BACK),
                new TurnSignal(mediator, Position.RIGHT, HorizontalPosition.FRONT),
                new TurnSignal(mediator, Position.RIGHT, HorizontalPosition.BACK)};

        for (TurnSignal turnSignal : turnSignals) {
            mediator.addTurnSignal(turnSignal);
        }

        truckChassisBuilder
                .setBrakeLights(brakeLights)
                .setTurnSignals(turnSignals);
    }

    @Override
    public void connectSensory() {
        coupling.getSensor().addListener(centralUnit);
    }

    @Override
    public AutonomousTruck getVehicle() {
        return new AutonomousTruck(truckChassisBuilder.createTruckChassis(), centralUnit, (TruckMediator) mediator);
    }
}
