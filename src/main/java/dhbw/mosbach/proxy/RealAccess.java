package dhbw.mosbach.proxy;

import dhbw.mosbach.cor.composite.TechnicalEngineer;

public class RealAccess implements proxy.IAccess {
    proxy.Robot robot = new proxy.Robot();
    @Override
    public void grant(TechnicalEngineer technicalEngineer) {
        technicalEngineer.getPart().accept(robot);
    }
}
