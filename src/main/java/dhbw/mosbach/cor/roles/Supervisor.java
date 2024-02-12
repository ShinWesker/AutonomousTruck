package dhbw.mosbach.cor.roles;

import dhbw.mosbach.enums.Defect;
import dhbw.mosbach.visitor.IPart;
import lombok.Getter;

@Getter
public class Supervisor extends TeamMember {
    private final String password;
    public Supervisor(String password) {
        this.password = password;
    }

    @Override
    public void repairDefect(Defect defect, IPart part) {
        successor.repairDefect(defect,part);
    }
}
