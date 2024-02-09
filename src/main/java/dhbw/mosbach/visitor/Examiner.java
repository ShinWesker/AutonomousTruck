package dhbw.mosbach.visitor;

import dhbw.mosbach.builder.components.Engine;
import dhbw.mosbach.builder.components.light.Camera;
import dhbw.mosbach.builder.components.light.Lidar;
import dhbw.mosbach.cor.ServiceCenter;

public class Examiner implements IControlVisitor {
    private final ServiceCenter serviceCenter;
    public Examiner(ServiceCenter serviceCenter){
        this.serviceCenter = serviceCenter;
    }
    @Override
    public void detect(Engine engine) {
        if (engine.getDefect() != null) {
            contactServiceTeam(engine);        }
    }

    @Override
    public void detect(Camera camera) {
        if (camera.getDefect() != null) {
            contactServiceTeam(camera);        }
    }

    @Override
    public void detect(Lidar lidar) {
        if (lidar.getDefect() != null) {
            contactServiceTeam(lidar);
        }
    }

    public void contactServiceTeam(IPart part){
        serviceCenter.handleDefect(part.getDefect(), part);
    }
}
