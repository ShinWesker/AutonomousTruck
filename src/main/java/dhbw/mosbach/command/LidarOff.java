package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.light.Lidar;

public class LidarOff implements  ICommand {
    private final Lidar lidar;

    public LidarOff(Lidar lidar) {
        this.lidar = lidar;
    }

    @Override
    public void execute() {
        lidar.deactivate();
    }
}
