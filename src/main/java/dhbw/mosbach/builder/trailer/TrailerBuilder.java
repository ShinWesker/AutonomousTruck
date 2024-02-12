package dhbw.mosbach.builder.trailer;

import dhbw.mosbach.builder.IVehicleBuilder;
import dhbw.mosbach.builder.components.Brake;
import dhbw.mosbach.enums.HorizontalPosition;
import dhbw.mosbach.enums.Position;
import dhbw.mosbach.builder.components.axle.Axle;
import dhbw.mosbach.builder.components.axle.Wheel;
import dhbw.mosbach.builder.components.chassis.TrailerChassis;
import dhbw.mosbach.builder.components.light.BrakeLight;
import dhbw.mosbach.builder.components.light.TurnSignal;

public class TrailerBuilder implements IVehicleBuilder<Trailer> {
    TrailerChassis.TrailerChassisBuilder trailerChassisBuilder;
    @Override
    public void buildChassis() {
        trailerChassisBuilder = new TrailerChassis.TrailerChassisBuilder();
    }
    @Override
    public void buildAxles() {
        Axle[] axles = new Axle[2];
        for (int i = 0; i < axles.length; i++) {
            Wheel[] wheels = {new Wheel(), new Wheel()};
            Brake[] brakes = {new Brake(), new Brake()};
            axles[i] = new Axle(wheels,brakes);
        }
        trailerChassisBuilder.setAxles(axles);
    }

    @Override
    public void buildSensory() {
        trailerChassisBuilder
                .setTurnSignals(new TurnSignal[]{
                        new TurnSignal(null,Position.LEFT, HorizontalPosition.BACK),
                        new TurnSignal(null,Position.RIGHT, HorizontalPosition.BACK)
                })
                .setBrakeLights(new BrakeLight[]{
                        new BrakeLight(null,Position.LEFT),
                        new BrakeLight(null,Position.RIGHT)});
    }

    @Override
    public Trailer getVehicle() {
        return new Trailer(trailerChassisBuilder.createTrailerChassis());
    }
}
