package dhbw.mosbach.command;

import dhbw.mosbach.builder.truck.AutonomousTruck;

public class MoveStraight implements ICommand {
    private final AutonomousTruck truck;
    private final int degree;

    public MoveStraight(AutonomousTruck truck, int degree) {
        this.truck = truck;
        this.degree = degree;
    }

    @Override
    public void execute() {
        truck.moveStraight(degree);
    }
}
