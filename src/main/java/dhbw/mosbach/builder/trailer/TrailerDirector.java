package dhbw.mosbach.builder.trailer;

import dhbw.mosbach.builder.VehicleDirector;

public class TrailerDirector extends VehicleDirector<Trailer, TrailerBuilder> {
    @Override
    public Trailer build(TrailerBuilder builder) {

        builder.buildChassis();
        builder.buildAxles();
        builder.buildSensory();

        return builder.getVehicle();
    }
}
