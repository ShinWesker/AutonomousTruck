package dhbw.mosbach.proxy;

import dhbw.mosbach.builder.components.light.Camera;
import dhbw.mosbach.builder.components.Engine;
import dhbw.mosbach.builder.components.light.Lidar;
import dhbw.mosbach.visitor.IPartVisitor;

public class Robot implements IPartVisitor {

    @Override
    public void repair(Engine engine) {
        engine.repair();
    }

    @Override
    public void repair(Camera camera) {
        camera.repair();
    }

    @Override
    public void repair(Lidar lidar) {
        lidar.repair();
    }
}
