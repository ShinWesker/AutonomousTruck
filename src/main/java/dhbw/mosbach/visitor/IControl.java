package dhbw.mosbach.visitor;

import dhbw.mosbach.builder.components.Camera;
import dhbw.mosbach.builder.components.Engine;
import dhbw.mosbach.builder.components.Lidar;

public interface IControl {
    void control(Engine engine);
    void control(Camera camera);
    void control(Lidar lidar);
}
