package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.Engine;
import dhbw.mosbach.builder.components.axle.SteeringAxle;
public class TurnRight implements ICommand {
    private final Engine engine;
    private final SteeringAxle steeringAxle;
    private final int percentage;
    private final int degree;

    public TurnRight(Engine engine, SteeringAxle steeringAxle, int percentage, int degree) {
        this.percentage = percentage;
        this.degree = degree;
        this.steeringAxle = steeringAxle;
        this.engine = engine;
    }

    @Override
    public void execute() {
        engine.move(percentage);
        steeringAxle.setDegree(degree);
    }
}
