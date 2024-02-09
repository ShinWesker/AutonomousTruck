package dhbw.mosbach.cor.roles;

import dhbw.mosbach.proxy.ProxyAccess;
import dhbw.mosbach.visitor.IPart;
import lombok.Getter;

@Getter
public class TechnicalEngineer {
    private IPart part;
    private String password;

    public void repair(IPart part, String password){
        this.part = part;
        this.password = password;
        connectToRobot();
    }
    private void connectToRobot() {
        ProxyAccess.INSTANCE.grant(this);
        System.out.println();
    }
}
