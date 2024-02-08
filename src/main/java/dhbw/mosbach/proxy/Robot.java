package proxy;

import dhbw.mosbach.builder.components.Camera;
import dhbw.mosbach.builder.components.Engine;
import dhbw.mosbach.builder.components.Lidar;
import dhbw.mosbach.visitor.IPartVisitor;

public class Robot implements IPartVisitor {

    @Override
    public void repair(Engine engine) {
        engine.repair();
        System.out.println("Repairing in Robot: " + engine.getClass().getSimpleName());

    }

    @Override
    public void repair(Camera camera) {
        camera.repair();
        System.out.println("Repairing in Robot: " + camera.getClass().getSimpleName());

    }

    @Override
    public void repair(Lidar lidar) {
        lidar.repair();
        System.out.println("Repairing in Robot: " + lidar.getClass().getSimpleName());

    }
}
