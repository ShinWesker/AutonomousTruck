package dhbw.mosbach.visitor;

import dhbw.mosbach.builder.components.light.Camera;
import dhbw.mosbach.builder.components.Engine;
import dhbw.mosbach.builder.components.light.Lidar;

public interface IPartVisitor {
    void repair(Engine engine);
    void repair(Camera camera);
    void repair(Lidar lidar);
}
