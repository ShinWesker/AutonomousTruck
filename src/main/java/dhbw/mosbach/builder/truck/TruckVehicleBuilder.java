package dhbw.mosbach.builder.truck;


import dhbw.mosbach.builder.VehicleBuilder;

public interface TruckVehicleBuilder extends VehicleBuilder<AutonomousTruck> {
    void buildCabin();
    void attachCabin();
    void connectSensory();
}
