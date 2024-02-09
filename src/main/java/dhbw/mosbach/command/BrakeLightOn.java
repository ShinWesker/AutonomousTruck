package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.light.BrakeLight;

public class BrakeLightOn implements ICommand {
    private final BrakeLight brakeLight;

    public BrakeLightOn(BrakeLight brakeLight) {
        this.brakeLight = brakeLight;
    }

    @Override
    public void execute() {
        brakeLight.activate();
    }
}
