package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.Camera;

public class CameraOff implements ICommand {
    private final Camera[] cameras;

    public CameraOff(Camera[] cameras) {
        this.cameras = cameras;
    }

    @Override
    public void execute() {
        for (Camera c: cameras
             ) {
            c.deactivate();
        }
    }
}
