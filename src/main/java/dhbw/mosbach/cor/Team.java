package dhbw.mosbach.cor;

import dhbw.mosbach.enums.Defect;
import dhbw.mosbach.cor.roles.Supervisor;
import dhbw.mosbach.visitor.IPart;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public abstract class Team {
    private Team successor;
    protected final Supervisor supervisor;

    protected Team(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
    public boolean canHandlePart(IPart part, List<String> permissions){
        return permissions.contains(part.getClass().getSimpleName());
    }

    public void repairPart(Defect defect, IPart part){
        if (getSuccessor() != null) {
            getSuccessor().repairPart(defect, part);
        } else{
            System.out.println("unable to repair defect");
        }
    }
}
