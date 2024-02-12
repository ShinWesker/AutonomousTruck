import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.VehicleDirector;
import dhbw.mosbach.builder.components.Pallet;
import dhbw.mosbach.builder.components.chassis.TruckChassis;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.builder.trailer.TrailerBuilder;
import dhbw.mosbach.builder.trailer.TrailerDirector;
import dhbw.mosbach.builder.trailer.TrailerVehicleBuilder;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import dhbw.mosbach.builder.truck.TruckBuilder;
import dhbw.mosbach.builder.truck.TruckDirector;
import dhbw.mosbach.command.ICommand;
import dhbw.mosbach.command.TurnSignalOff;
import dhbw.mosbach.command.TurnSignalOn;
import dhbw.mosbach.cor.roles.EmergencyTeamManager;
import dhbw.mosbach.cor.roles.OperationTeamManager;
import dhbw.mosbach.cor.roles.Supervisor;
import dhbw.mosbach.cor.roles.TechnicalEngineer;
import dhbw.mosbach.mediator.TruckMediator;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestUtil {

    public AutonomousTruck createTruck(){
        CentralUnit centralUnit = new CentralUnit();
        TruckMediator mediator = new TruckMediator();
        TruckBuilder builder = new TruckBuilder(centralUnit, mediator);
        TruckDirector truckDirector = new TruckDirector();
        return truckDirector.build(builder);
    }

    public Trailer createTrailer(){
        TrailerVehicleBuilder trailerBuilder = new TrailerBuilder();
        VehicleDirector<Trailer, TrailerVehicleBuilder> trailerDirector = new TrailerDirector();
        return trailerDirector.build(trailerBuilder);
    }

    public void loadAllItemsIntoTrailer(Trailer trailer) {
        String[] fruits = {"Apple", "Banana", "Cherry", "Date", "Elderberry", "Fig", "Grape", "Honeydew", "Kiwi", "Lemon", "Mango", "Nectarine", "Orange", "Papaya", "Quince", "Raspberry"};
        for (int i = 0; i < fruits.length; i++) {
            trailer.load(new Pallet(fruits[i]), i);
        }
    }

    public Supervisor createSupervisor(){
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

        supervisor.setSuccessor(operationTeamManager);
        operationTeamManager.setSuccessor(emergencyTeamManager);
        operationTeamManager.setParent(supervisor);
        emergencyTeamManager.setParent(supervisor);

        return supervisor;
    }

    public Supervisor createTeamWithMockedEngineers(TechnicalEngineer mockOperation, TechnicalEngineer mockEmergency){
        TechnicalEngineer[] technicalEmergency = new TechnicalEngineer[3];
        technicalEmergency[0] = mockEmergency;
        technicalEmergency[1] = mockEmergency;
        technicalEmergency[2] = mockEmergency;

        TechnicalEngineer[] technicalOperation = new TechnicalEngineer[3];
        technicalOperation[0] = mockOperation;
        technicalOperation[1] = mockOperation;
        technicalOperation[2] = mockOperation;

        EmergencyTeamManager emergencyTeamManager = new EmergencyTeamManager(technicalEmergency);
        OperationTeamManager operationTeamManager = new OperationTeamManager(technicalOperation);

        Supervisor supervisor = new Supervisor("PasswordOperation");

        supervisor.setSuccessor(operationTeamManager);
        operationTeamManager.setSuccessor(emergencyTeamManager);
        operationTeamManager.setParent(supervisor);
        emergencyTeamManager.setParent(supervisor);

        return supervisor;
    }
}
