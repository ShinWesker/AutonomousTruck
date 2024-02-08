package dhbw.mosbach.builder.truck;

import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.components.Brake;
import dhbw.mosbach.builder.components.axle.Axle;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.builder.components.chassis.TruckChassis;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.IVehicle;
import dhbw.mosbach.command.CBrake;
import dhbw.mosbach.command.TurnLeft;
import dhbw.mosbach.command.TurnRight;
import dhbw.mosbach.command.TurnSignalOn;
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
        centralUnit.setTruck(this);
        mediator.setTruck(this);
    }
    public void moveStraight(int percentage) {
        System.out.println("Truck moved straight");
    }
    public void turnLeft(int degree, int percentage) {
        System.out.println("Truck turning left");
        centralUnit.setCommand(new TurnSignalOn(truckChassis.getTurnSignals(), Position.LEFT));
        centralUnit.execute();

        centralUnit.setCommand(new CBrake(truckChassis.getAxles(),25));
        centralUnit.execute();
        if (Boolean.TRUE.equals(connected)) threePoleConnector.getBrakeBus().post(new EventBrake(25));

        centralUnit.setCommand(new TurnLeft(truckChassis.getEngine(),truckChassis.getSteeringAxle(),percentage,degree));
        centralUnit.execute();
    }
    public void turnRight(int degree, int percentage) {
        System.out.println("Truck turning right");
        centralUnit.setCommand(new TurnSignalOn(truckChassis.getTurnSignals(), Position.RIGHT));
        centralUnit.execute();

        centralUnit.setCommand(new CBrake(truckChassis.getAxles(),25));
        centralUnit.execute();
        if (Boolean.TRUE.equals(connected)) threePoleConnector.getBrakeBus().post(new EventBrake(25));

        centralUnit.setCommand(new TurnRight(truckChassis.getEngine(),truckChassis.getSteeringAxle(),percentage,degree));
        centralUnit.execute();
    }
    public void changeState(){
        state.toggle(this);
        System.out.println(state.getClass().getSimpleName());
    }

    public void connect(Trailer trailer) {
        this.truckChassis.getCoupling().connect(trailer);
        threePoleConnector = new ThreePoleConnector();
        trailer.connect(this);

        for (Axle a : trailer.getTrailerChassis().getAxles()){
            for (Brake b : a.getBrakes()){
                threePoleConnector.addSubscriberBrakeBus(b);
            }
        }
        for (BrakeLight b : trailer.getTrailerChassis().getBrakeLights()){
            threePoleConnector.addSubscriberBrakeLightBus(b);
        }

        for (TurnSignal t : trailer.getTrailerChassis().getTurnSignals()){
            threePoleConnector.addSubscriberTurnSignalBus(t);
        }
    }
}
