package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.light.BrakeLight;

public class BrakeLightOn implements ICommand {
    private final BrakeLight[] brakeLights;

    public BrakeLightOn(BrakeLight[] brakeLights) {
        this.brakeLights = brakeLights;
    }

    @Override
    public void execute() {
        brakeLights[0].activate();
    }
}
