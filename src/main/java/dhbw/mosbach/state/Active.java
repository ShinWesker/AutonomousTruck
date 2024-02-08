package dhbw.mosbach.state;

import dhbw.mosbach.builder.truck.AutonomousTruck;

public class Active implements ITruckState {
    @Override
    public void toggle(AutonomousTruck truck) {
        truck.setState(new Inactive());
    }
}
