package dhbw.mosbach.builder.truck;

import dhbw.mosbach.builder.VehicleDirector;

public class TruckDirector extends VehicleDirector<AutonomousTruck, TruckBuilder> {
    @Override
    public AutonomousTruck build(TruckBuilder builder) {
        builder.buildAxles();
        builder.buildChassis();
        builder.buildCabin();
        builder.attachCabin();
        builder.buildSensory();
        builder.connectSensory();
        return builder.getVehicle();
    }
}
