package dhbw.mosbach;

import dhbw.mosbach.bridge.TruckBatteryControl;
import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.VehicleDirector;
import dhbw.mosbach.builder.components.*;
import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.enums.Position;
import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.builder.trailer.TrailerBuilder;
import dhbw.mosbach.builder.trailer.TrailerDirector;
import dhbw.mosbach.builder.trailer.TrailerVehicleBuilder;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import dhbw.mosbach.builder.truck.TruckBuilder;
import dhbw.mosbach.builder.truck.TruckDirector;
import dhbw.mosbach.builder.truck.TruckVehicleBuilder;
import dhbw.mosbach.command.TurnSignalOn;
import dhbw.mosbach.composite.Battery;
import dhbw.mosbach.cor.Defect;
import dhbw.mosbach.cor.ServiceCenter;

public class Main {
    public static void main(String[] args) {

        // Builder
        TruckVehicleBuilder truckBuilder = new TruckBuilder();
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



        /*
        //Command Pattern test
        TurnSignal[] turnSignals = new TurnSignal[3];
        TurnSignal turnSignal = new TurnSignal(Position.RIGHT);
        TurnSignal turnSignal1 = new TurnSignal(Position.RIGHT);
        TurnSignal turnSignal2 = new TurnSignal(Position.RIGHT);
        turnSignals[0] = turnSignal;
        turnSignals[1] = turnSignal1;
        turnSignals[2] = turnSignal2;

        CentralUnit centralUnit = new CentralUnit();
        centralUnit.setCommand(new TurnSignalOn(turnSignals));
        centralUnit.execute();

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

        trailer = new Trailer();
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
        Defect defect = Defect.E03;
        Defect defect1 = Defect.E02;
        ServiceCenter serviceCenter = new ServiceCenter();

        Camera camera = new Camera();

        serviceCenter.handleDefect(defect, engine);
        serviceCenter.handleDefect(defect1, camera);

        */


    }
}