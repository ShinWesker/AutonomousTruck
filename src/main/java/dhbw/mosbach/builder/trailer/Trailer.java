package dhbw.mosbach.builder.trailer;

import dhbw.mosbach.builder.IVehicle;
import dhbw.mosbach.builder.components.CargoSpace;
import dhbw.mosbach.builder.components.HoldingArea;
import dhbw.mosbach.builder.components.Pallet;
import dhbw.mosbach.builder.components.Pivot;
import dhbw.mosbach.builder.components.chassis.TrailerChassis;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import lombok.Getter;

public class Trailer implements IVehicle {

    @Getter
    private final TrailerChassis trailerChassis;
    private final Pivot pivot;
    private final CargoSpace cargoSpace;
    protected Trailer(TrailerChassis trailerChassis){
        this.trailerChassis = trailerChassis;
        this.pivot = new Pivot(false);
        this.cargoSpace = new CargoSpace();
    }
    public Pivot getPivot(){
        return new Pivot(pivot.getStatus());
    }

    public CargoSpace getCargoSpace(){
        return new CargoSpace();
    }

    public void load(Pallet pallet, int index){
        cargoSpace.load(pallet,index);
    }

    public void connect(AutonomousTruck autonomousTruck) {
        this.pivot.setTruck(autonomousTruck);
        for (HoldingArea holdingArea : cargoSpace.getArea())
            holdingArea.getSensor().addListener(autonomousTruck.getCentralUnit());
    }
}
