package dhbw.mosbach.proxy;

import dhbw.mosbach.cor.roles.TechnicalEngineer;

public class RealAccess implements IAccess {
    proxy.Robot robot = new proxy.Robot();
    @Override
    public void grant(TechnicalEngineer technicalEngineer) {
        technicalEngineer.getPart().acceptPartVisitor(robot);
    }
}
