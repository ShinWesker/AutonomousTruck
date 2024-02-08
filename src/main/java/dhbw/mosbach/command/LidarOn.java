package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.light.Lidar;

public class LidarOn implements ICommand{
    private final Lidar lidar;

    public LidarOn(Lidar lidar) {
        this.lidar = lidar;
    }

    @Override
    public void execute() {
        lidar.activate();
    }
}
