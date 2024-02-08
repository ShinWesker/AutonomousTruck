package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.Brake;
import dhbw.mosbach.builder.components.axle.Axle;
import lombok.Getter;

public class CBrake implements ICommand {
    private final Axle[] axles;

    @Getter
    private final int percentage;

    public CBrake(Axle[] axles, int percentage) {
        this.axles = axles;
        this.percentage = percentage;
    }

    @Override
    public void execute() {
        for (Axle a : axles){
            for (Brake b : a.getBrakes()){
                b.setPercentage(percentage);
            }
        }
    }
}
