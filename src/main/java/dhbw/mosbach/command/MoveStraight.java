package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.Engine;
import dhbw.mosbach.builder.components.axle.SteeringAxle;
import dhbw.mosbach.enums.Position;

public class MoveStraight implements ICommand {
    private final SteeringAxle steeringAxle;
    private final int speed;
    private final Engine engine;

    public MoveStraight(SteeringAxle steeringAxle, int speed, Engine engine) {
        this.steeringAxle = steeringAxle;
        this.speed = speed;
        this.engine = engine;
    }
    @Override
    public void execute() {
        steeringAxle.setDegree(0);
        steeringAxle.setPosition(Position.STRAIGHT);
        engine.move(speed);
    }
}
