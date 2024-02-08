package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.axle.SteeringAxle;

public class MoveStraight implements ICommand {
    private final SteeringAxle steeringAxle;
    private final int degree;

    public MoveStraight(SteeringAxle steeringAxle, int degree) {
        this.steeringAxle = steeringAxle;
        this.degree = degree;
    }

    @Override
    public void execute() {
        steeringAxle.setDegree(degree);
    }
}
