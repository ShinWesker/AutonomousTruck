package dhbw.mosbach.builder.truck;

import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.components.Brake;
import dhbw.mosbach.builder.components.axle.Axle;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.builder.components.chassis.TruckChassis;
import dhbw.mosbach.builder.IVehicle;
import dhbw.mosbach.command.*;
import dhbw.mosbach.eventbus.ThreePoleConnector;
import dhbw.mosbach.eventbus.events.EventBrake;
import dhbw.mosbach.mediator.TruckMediator;
import dhbw.mosbach.state.ITruckState;
import dhbw.mosbach.state.Inactive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AutonomousTruck implements IVehicle {
    private final TruckChassis truckChassis;
    private final CentralUnit centralUnit;
    private ThreePoleConnector threePoleConnector;
    private Boolean connected = false;
    private ITruckState state = new Inactive();

    protected AutonomousTruck(TruckChassis truckChassis, CentralUnit centralUnit, TruckMediator mediator) {
        this.truckChassis = truckChassis;
        this.centralUnit = centralUnit;
        this.centralUnit.setTruck(this);
        mediator.setTruck(this);
    }

    public void activate() {
        executeCommand(new CameraOn(truckChassis.getCabine().getExteriorMirrors()[0].getCamera()));
        executeCommand(new LidarOn(truckChassis.getCabine().getExteriorMirrors()[0].getLidar()));
        executeCommand(new EngineStart(truckChassis.getEngine()));
        moveStraight(0);
    }

    public void deactivate() {
        executeCommand(new CameraOff(truckChassis.getCabine().getExteriorMirrors()[0].getCamera()));
        executeCommand(new LidarOff(truckChassis.getCabine().getExteriorMirrors()[0].getLidar()));
        executeCommand(new EngineShutDown(truckChassis.getEngine()));
        moveStraight(0);
    }

    public void moveStraight(int percentage) {
        deactivateTurnSignals();
        executeCommand(new MoveStraight(truckChassis.getSteeringAxle(), percentage));
    }

    public void turnLeft(int degree, int percentage) {
        turn(Position.LEFT, degree, percentage);
    }

    public void turnRight(int degree, int percentage) {
        turn(Position.RIGHT, degree, percentage);
    }

    private void turn(Position position, int degree, int percentage) {
        System.out.println("Truck turning " + position.toString().toLowerCase());
        activateTurnSignal(position);
        applyBrakes(25);
        executeTurn(position, degree, percentage);
    }

    private void activateTurnSignal(Position position) {
        executeCommand(new TurnSignalOn(truckChassis.getTurnSignals(), position));
    }

    private void deactivateTurnSignals() {
        deactivateTurnSignal(Position.LEFT);
        deactivateTurnSignal(Position.RIGHT);
    }

    private void deactivateTurnSignal(Position position) {
        executeCommand(new TurnSignalOff(truckChassis.getTurnSignals(), position));
    }

    private void applyBrakes(int intensity) {
        executeCommand(new CBrake(truckChassis.getAxles(), intensity));
        if (Boolean.TRUE.equals(connected)) {
            threePoleConnector.getBrakeBus().post(new EventBrake(intensity));
        }
    }

    private void executeTurn(Position position, int degree, int percentage) {
        ICommand turnCommand = position == Position.LEFT ?
                new TurnLeft(truckChassis.getEngine(), truckChassis.getSteeringAxle(), percentage, degree) :
                new TurnRight(truckChassis.getEngine(), truckChassis.getSteeringAxle(), percentage, degree);
        executeCommand(turnCommand);
    }

    private void executeCommand(ICommand command) {
        centralUnit.setCommand(command);
        centralUnit.execute();
    }

    public void changeState() {
        state.toggle(this);
        System.out.println(state.getClass().getSimpleName());
    }

    public void connect(Trailer trailer) {
        truckChassis.getCoupling().connect(trailer);
        setupThreePoleConnector(trailer);
    }

    private void setupThreePoleConnector(Trailer trailer) {
        threePoleConnector = new ThreePoleConnector();
        trailer.connect(this);
        connectComponentsToThreePoleConnector(trailer);
    }

    private void connectComponentsToThreePoleConnector(Trailer trailer) {
        for (Axle axle : trailer.getTrailerChassis().getAxles()) {
            for (Brake brake : axle.getBrakes()) {
                threePoleConnector.addSubscriberBrakeBus(brake);
            }
        }
        for (BrakeLight brakeLight : trailer.getTrailerChassis().getBrakeLights()) {
            threePoleConnector.addSubscriberBrakeLightBus(brakeLight);
        }
        for (TurnSignal turnSignal : trailer.getTrailerChassis().getTurnSignals()) {
            threePoleConnector.addSubscriberTurnSignalBus(turnSignal);
        }
    }
}
