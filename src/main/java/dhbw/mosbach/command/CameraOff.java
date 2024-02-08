package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.light.Camera;

public class CameraOff implements ICommand {
    private final Camera camera;

    public CameraOff(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void execute() {
        camera.deactivate();
    }
}
