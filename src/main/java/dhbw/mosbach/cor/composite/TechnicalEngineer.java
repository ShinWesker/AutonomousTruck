package dhbw.mosbach.cor.composite;

import dhbw.mosbach.proxy.ProxyAccess;
import dhbw.mosbach.visitor.IPart;

public class TechnicalEngineer extends TeamManager {

    private IPart part;

    public String getPassword() {
        return parent.getPassword();
    }

    public IPart getPart() {
        return part;
    }

    public void delegate(IPart part) {
        this.part = part;
        connectToRobot();
    }

    private void connectToRobot() {
        System.out.println("TechnicalEnginner : " + this.hashCode());
        System.out.println("In Team: "+ parent.getClass().getSimpleName());
        System.out.println("Connecting to Proxy to start robot");
        ProxyAccess.INSTANCE.grant(this);
        System.out.println();
    }
}
