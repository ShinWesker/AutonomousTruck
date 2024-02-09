import dhbw.mosbach.builder.CentralUnit;
import dhbw.mosbach.builder.VehicleDirector;
import dhbw.mosbach.builder.components.Pallet;
import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.builder.trailer.TrailerBuilder;
import dhbw.mosbach.builder.trailer.TrailerDirector;
import dhbw.mosbach.builder.trailer.TrailerVehicleBuilder;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import dhbw.mosbach.builder.truck.TruckBuilder;
import dhbw.mosbach.builder.truck.TruckDirector;
import dhbw.mosbach.mediator.TruckMediator;

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
}
