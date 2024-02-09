package dhbw.mosbach.cor.roles;

import dhbw.mosbach.cor.Defect;
import dhbw.mosbach.visitor.IPart;
import lombok.Setter;

@Setter
public abstract class TeamMember {
    protected TeamMember successor;
    protected TeamMember parent;
    public abstract void repairDefect(Defect defect, IPart part);
    public abstract String getPassword();
}
