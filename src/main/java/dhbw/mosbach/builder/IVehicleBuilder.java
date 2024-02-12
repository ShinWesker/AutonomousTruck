package dhbw.mosbach.builder;


public interface IVehicleBuilder<V extends IVehicle> {
    void buildAxles();
    void buildChassis();
    void buildSensory();
    V getVehicle();
}
