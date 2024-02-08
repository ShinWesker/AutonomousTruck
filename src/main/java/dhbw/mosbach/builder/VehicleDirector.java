package dhbw.mosbach.builder;


public abstract class VehicleDirector<V extends IVehicle, B extends VehicleBuilder<V>> {
    public abstract V build(B builder);
}
