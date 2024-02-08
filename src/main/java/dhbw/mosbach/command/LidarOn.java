package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.light.Lidar;

public class LidarOn implements ICommand{
    private final Lidar[] lidars;

    public LidarOn(Lidar[] lidars) {
        this.lidars = lidars;
    }

    @Override
    public void execute() {
        lidars[0].activate();
    }
}
