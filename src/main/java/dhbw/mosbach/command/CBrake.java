package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.Brake;
import dhbw.mosbach.builder.components.axle.Axle;
import lombok.Getter;

public class CBrake implements ICommand {
    private final Axle[] axles;

    @Getter
    private final int intensity;

    public CBrake(Axle[] axles, int intensity) {
        this.axles = axles;
        this.intensity = intensity;
    }

    @Override
    public void execute() {
        for (Axle a : axles){
            for (Brake b : a.getBrakes()){
                b.brake(intensity);
            }
        }
    }
}
