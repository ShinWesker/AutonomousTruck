package dhbw.mosbach;

import dhbw.mosbach.bridge.TruckBatteryControl;
import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.VehicleDirector;
import dhbw.mosbach.builder.components.*;
import dhbw.mosbach.builder.components.light.Camera;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.builder.trailer.TrailerBuilder;
import dhbw.mosbach.builder.trailer.TrailerDirector;
import dhbw.mosbach.builder.trailer.TrailerVehicleBuilder;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import dhbw.mosbach.builder.truck.TruckBuilder;
import dhbw.mosbach.builder.truck.TruckDirector;
import dhbw.mosbach.builder.truck.TruckVehicleBuilder;
import dhbw.mosbach.composite.Battery;
import dhbw.mosbach.cor.Defect;
import dhbw.mosbach.cor.MotorTeam;
import dhbw.mosbach.cor.SensoryTeam;
import dhbw.mosbach.cor.ServiceCenter;
import dhbw.mosbach.cor.roles.EmergencyTeamManager;
import dhbw.mosbach.cor.roles.OperationTeamManager;
import dhbw.mosbach.cor.roles.Supervisor;
import dhbw.mosbach.cor.roles.TechnicalEngineer;
import dhbw.mosbach.key.ElectronicKey;
import dhbw.mosbach.key.ReceiverModule;
import dhbw.mosbach.mediator.ITruckMediator;
import dhbw.mosbach.mediator.TruckMediator;
import dhbw.mosbach.visitor.Examiner;

public class Main {
    public static void main(String[] args) {

        CentralUnit centralUnit = new CentralUnit();
        ITruckMediator mediator = new TruckMediator();

        // Builder
        TruckVehicleBuilder truckBuilder = new TruckBuilder(centralUnit, mediator);
        VehicleDirector<AutonomousTruck, TruckVehicleBuilder> truckDirector = new TruckDirector();
        AutonomousTruck autonomousTruck = truckDirector.build(truckBuilder);


        TrailerVehicleBuilder trailerBuilder = new TrailerBuilder();
        VehicleDirector<Trailer, TrailerVehicleBuilder> trailerDirector = new TrailerDirector();
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
        TechnicalEngineer[] technicalEmergency = new TechnicalEngineer[1];
        technicalEmergency[0] = new TechnicalEngineer();

        TechnicalEngineer[] technicalOperation = new TechnicalEngineer[1];
        technicalOperation[0] = new TechnicalEngineer();

        EmergencyTeamManager emergencyTeamManager = new EmergencyTeamManager(technicalEmergency);
        OperationTeamManager operationTeamManager = new OperationTeamManager(technicalOperation);

        Supervisor supervisor = new Supervisor("PasswordOperation");

        supervisor.setSuccessor(operationTeamManager);
        operationTeamManager.setSuccessor(emergencyTeamManager);
        operationTeamManager.setParent(supervisor);
        emergencyTeamManager.setParent(supervisor);

        MotorTeam motorTeam =  new MotorTeam(supervisor);

        TechnicalEngineer[] technicalEngineers2 = new TechnicalEngineer[3];
        technicalEngineers2[0] = new TechnicalEngineer();
        technicalEngineers2[1] = new TechnicalEngineer();
        technicalEngineers2[2] = new TechnicalEngineer();

        TechnicalEngineer[] technicalEngineers3 = new TechnicalEngineer[3];
        technicalEngineers3[0] = new TechnicalEngineer();
        technicalEngineers3[1] = new TechnicalEngineer();
        technicalEngineers3[2] = new TechnicalEngineer();

        EmergencyTeamManager emergencyTeamManager1 = new EmergencyTeamManager(technicalEngineers2);
        OperationTeamManager operationTeamManager1 = new OperationTeamManager(technicalEngineers3);

        Supervisor supervisor1 = new Supervisor("PasswordOperation");

        supervisor1.setSuccessor(operationTeamManager1);
        operationTeamManager1.setSuccessor(emergencyTeamManager1);
        SensoryTeam sensoryTeam = new SensoryTeam(supervisor1);

        operationTeamManager1.setParent(supervisor);
        emergencyTeamManager1.setParent(supervisor);

        sensoryTeam.setSuccessor(motorTeam);

        ServiceCenter serviceCenter = new ServiceCenter(sensoryTeam);
        Camera camera = new Camera(mediator,Position.LEFT);

        serviceCenter.handleDefect(Defect.E01, camera);

        autonomousTruck.examineParts(new Examiner(serviceCenter));
    }
}