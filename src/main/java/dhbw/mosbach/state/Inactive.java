package dhbw.mosbach.state;

import dhbw.mosbach.builder.truck.AutonomousTruck;

public class Inactive implements ITruckState {
    @Override
    public void toggle(AutonomousTruck truck) {
        truck.setState(new Active());
        truck.activate();
    }
}
