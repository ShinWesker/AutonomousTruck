package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.light.BrakeLight;

public class BrakeLightOff implements ICommand {
    private final BrakeLight brakeLight;

    public BrakeLightOff(BrakeLight brakeLight) {
        this.brakeLight = brakeLight;
    }

    @Override
    public void execute() {
        brakeLight.deactivate();
    }
}
