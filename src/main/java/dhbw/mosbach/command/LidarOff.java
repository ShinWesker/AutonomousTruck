package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.Lidar;

public class LidarOff implements  ICommand {
    private final Lidar[] lidars;

    public LidarOff(Lidar[] lidars) {
        this.lidars = lidars;
    }

    @Override
    public void execute() {
        for (Lidar l: lidars
             ) {
            l.deactivate();
        }
    }
}
