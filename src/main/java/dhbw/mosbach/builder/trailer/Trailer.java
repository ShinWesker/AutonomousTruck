package dhbw.mosbach.builder.trailer;

import dhbw.mosbach.builder.IVehicle;
import dhbw.mosbach.builder.components.Pallet;
import dhbw.mosbach.builder.components.chassis.TrailerChassis;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import lombok.Getter;

@Getter
public class Trailer implements IVehicle {

    private final TrailerChassis trailerChassis;

    protected Trailer(TrailerChassis trailerChassis){
        this.trailerChassis = trailerChassis;

    }
    public void load(Pallet pallet, int index){
        trailerChassis.load(pallet,index);
    }

    public void connect(AutonomousTruck autonomousTruck) {
        trailerChassis.connect(autonomousTruck);
    }
}
