package dhbw.mosbach.proxy;

import dhbw.mosbach.cor.roles.TechnicalEngineer;

public class RealAccess implements IAccess {
    Robot robot = new Robot();
    @Override
    public void grant(TechnicalEngineer technicalEngineer) {
        technicalEngineer.getPart().acceptPartVisitor(robot);
    }
}
