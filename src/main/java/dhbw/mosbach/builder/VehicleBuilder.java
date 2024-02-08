package dhbw.mosbach.builder;


public interface VehicleBuilder<V extends IVehicle> {

    void buildAxles();
    void buildChassis();
    void buildSensory();
    V getVehicle();
}
