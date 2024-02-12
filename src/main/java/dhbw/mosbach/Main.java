package dhbw.mosbach;

import dhbw.mosbach.composite.bridge.TruckBatteryControl;
import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.VehicleDirector;
import dhbw.mosbach.builder.components.*;
import dhbw.mosbach.builder.components.light.Camera;
import dhbw.mosbach.enums.Position;
import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.builder.trailer.TrailerBuilder;
import dhbw.mosbach.builder.trailer.TrailerDirector;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import dhbw.mosbach.builder.truck.TruckBuilder;
import dhbw.mosbach.builder.truck.TruckDirector;
import dhbw.mosbach.composite.Battery;
import dhbw.mosbach.enums.Defect;
import dhbw.mosbach.cor.MotorTeam;
import dhbw.mosbach.cor.SensoryTeam;
import dhbw.mosbach.cor.ServiceCenter;
import dhbw.mosbach.cor.roles.EmergencyTeamManager;
import dhbw.mosbach.cor.roles.OperationTeamManager;
import dhbw.mosbach.cor.roles.Supervisor;
import dhbw.mosbach.cor.roles.TechnicalEngineer;
import dhbw.mosbach.state.ElectronicKey;
import dhbw.mosbach.state.ReceiverModule;
import dhbw.mosbach.mediator.ITruckMediator;
import dhbw.mosbach.mediator.TruckMediator;
import dhbw.mosbach.visitor.Examiner;

public class Main {
    public static void main(String[] args) {

        CentralUnit centralUnit = new CentralUnit();
        ITruckMediator mediator = new TruckMediator();

        // Builder
        TruckBuilder truckBuilder = new TruckBuilder(centralUnit, mediator);
        VehicleDirector<AutonomousTruck, TruckBuilder> truckDirector = new TruckDirector();
        AutonomousTruck autonomousTruck = truckDirector.build(truckBuilder);


        TrailerBuilder trailerBuilder = new TrailerBuilder();
        VehicleDirector<Trailer, TrailerBuilder> trailerDirector = new TrailerDirector();
        Trailer trailer = trailerDirector.build(trailerBuilder);

        System.out.println(autonomousTruck);
        System.out.println(trailer);

        autonomousTruck.turnLeft(30, 75);
        autonomousTruck.turnRight(30, 75);

        autonomousTruck.connect(trailer);

        autonomousTruck.turnLeft(30, 75);
        autonomousTruck.turnRight(30, 75);

        trailer.load(new Pallet("banana"), 1);

        // Key Test
        ReceiverModule receiverModule = new ReceiverModule(centralUnit);
        ElectronicKey key = new ElectronicKey("Kodiak2024", receiverModule);
        key.sendSignal();
        key.sendSignal();

        ElectronicKey key2 = new ElectronicKey("haha35", receiverModule);
        key2.sendSignal();


        //Observer
        Pallet pallet = new Pallet("Eggs");
        HoldingArea holdingArea = new HoldingArea(1,Position.RIGHT);
        holdingArea.getSensor().addListener(centralUnit);
        holdingArea.load(pallet);

        Coupling coupling = new Coupling();
        coupling.getSensor().addListener(centralUnit);
        coupling.connect(trailer);

        // Battery
        Battery battery = new Battery();
        TruckBatteryControl control = new TruckBatteryControl(battery);
        Engine engine = new Engine(control);
        engine.move(5);

        control.fillEnergy();


        // COR + Composite
        SensoryTeam sensoryTeam =  new SensoryTeam(createSupervisor());
        MotorTeam motorTeam =  new MotorTeam(createSupervisor());

        sensoryTeam.setSuccessor(motorTeam);

        ServiceCenter serviceCenter = new ServiceCenter(sensoryTeam);
        Camera camera = new Camera(mediator,Position.LEFT);

        serviceCenter.handleDefect(Defect.E01, camera);

        autonomousTruck.examineParts(new Examiner(serviceCenter));
    }

    public static Supervisor createSupervisor(){
        TechnicalEngineer[] technicalEmergency = new TechnicalEngineer[3];
        technicalEmergency[0] = new TechnicalEngineer();
        technicalEmergency[1] = new TechnicalEngineer();
        technicalEmergency[2] = new TechnicalEngineer();

        TechnicalEngineer[] technicalOperation = new TechnicalEngineer[3];
        technicalOperation[0] = new TechnicalEngineer();
        technicalOperation[1] = new TechnicalEngineer();
        technicalOperation[2] = new TechnicalEngineer();

        EmergencyTeamManager emergencyTeamManager = new EmergencyTeamManager(technicalEmergency);
        OperationTeamManager operationTeamManager = new OperationTeamManager(technicalOperation);

        Supervisor supervisor = new Supervisor("PasswordOperation");

        technicalEmergency[0].setParent(emergencyTeamManager);
        technicalEmergency[1].setParent(emergencyTeamManager);
        technicalEmergency[2].setParent(emergencyTeamManager);

        technicalOperation[0].setParent(operationTeamManager);
        technicalOperation[1].setParent(operationTeamManager);
        technicalOperation[2].setParent(operationTeamManager);

        supervisor.setSuccessor(operationTeamManager);
        operationTeamManager.setSuccessor(emergencyTeamManager);
        operationTeamManager.setParent(supervisor);
        emergencyTeamManager.setParent(supervisor);

        return supervisor;
    }
}