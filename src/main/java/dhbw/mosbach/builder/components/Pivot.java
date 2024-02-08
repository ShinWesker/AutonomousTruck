package dhbw.mosbach.builder.components;

import dhbw.mosbach.builder.truck.AutonomousTruck;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pivot {
    AutonomousTruck truck;
    Boolean status;

    public Pivot(Boolean status){
        this.status = status;
    }

}
