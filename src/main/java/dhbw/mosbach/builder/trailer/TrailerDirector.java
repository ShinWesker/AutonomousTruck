package dhbw.mosbach.builder.trailer;

import dhbw.mosbach.builder.VehicleDirector;

public class TrailerDirector extends VehicleDirector<Trailer, TrailerVehicleBuilder> {
    @Override
    public Trailer build(TrailerVehicleBuilder builder) {

        builder.buildAxles();
        builder.buildChassis();
        builder.buildSensory();

        return builder.getVehicle();
    }
}
