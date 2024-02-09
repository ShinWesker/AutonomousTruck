package dhbw.mosbach.visitor;

import dhbw.mosbach.builder.components.light.Camera;
import dhbw.mosbach.builder.components.Engine;
import dhbw.mosbach.builder.components.light.Lidar;

public interface IControlVisitor {
    void detect(Engine engine);
    void detect(Camera camera);
    void detect(Lidar lidar);
}
