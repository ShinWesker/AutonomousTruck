package proxy;

import dhbw.mosbach.cor.composite.TechnicalEngineer;

public class RealAccess implements IAccess {
    Robot robot = new Robot();
    @Override
    public void grant(TechnicalEngineer technicalEngineer) {
        technicalEngineer.getPart().accept(robot);
    }
}
