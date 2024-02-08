package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.light.Camera;

public class CameraOn implements ICommand {
    private final Camera camera;

    public CameraOn(Camera camera) {
        this.camera = camera;
    }

    @Override
    public void execute() {
        camera.activate();
    }
}
