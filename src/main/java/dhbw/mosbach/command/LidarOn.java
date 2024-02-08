package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.Lidar;

public class LidarOn implements ICommand{
    private final Lidar[] lidars;

    public LidarOn(Lidar[] lidars) {
        this.lidars = lidars;
    }

    @Override
    public void execute() {
        for (Lidar l: lidars
        ) {
            l.activate();
        }
    }
}
