package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.Camera;

public class CameraOn implements ICommand {
    private final Camera[] cameras;

    public CameraOn(Camera[] cameras) {
        this.cameras = cameras;
    }

    @Override
    public void execute() {
        for (Camera c: cameras
        ) {
            c.activate();
        }
    }
}
