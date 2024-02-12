package dhbw.mosbach.cor.roles;

import dhbw.mosbach.enums.Defect;
import dhbw.mosbach.proxy.ProxyAccess;
import dhbw.mosbach.visitor.IPart;
import lombok.Getter;

@Getter
public class TechnicalEngineer extends TeamMember {
    private IPart part;
    private String password;
    private Defect defect;

    private void connectToRobot() {
        ProxyAccess.INSTANCE.grant(this);
        System.out.println();
    }

    @Override
    public void repairDefect(Defect defect, IPart part) {
        this.defect =defect;
        this.part = part;
        this.password = parent.getPassword();
        connectToRobot();
    }
}
