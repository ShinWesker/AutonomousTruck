package dhbw.mosbach.cor;

import dhbw.mosbach.cor.roles.Supervisor;
import dhbw.mosbach.visitor.IPart;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public abstract class ATeam {
    private ATeam successor;
    protected final Supervisor supervisor;

    protected ATeam(Supervisor supervisor) {
        this.supervisor = supervisor;
    }
    public boolean canHandlePart(IPart part, List<String> canHandleParts){
        return canHandleParts.contains(part.getClass().getSimpleName());
    }

    public void repairPart(Defect defect, IPart part){
        if (getSuccessor() != null) {
            getSuccessor().repairPart(defect, part);
        } else{
            System.out.println("unable to repair defect");
        }
    }
}
