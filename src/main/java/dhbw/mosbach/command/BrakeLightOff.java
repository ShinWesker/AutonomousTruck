package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.light.BrakeLight;

public class BrakeLightOff implements ICommand {
    private final BrakeLight[] brakeLights;

    public BrakeLightOff(BrakeLight[] brakeLights) {
        this.brakeLights = brakeLights;
    }

    @Override
    public void execute() {
        brakeLights[0].deactivate();
    }
}
