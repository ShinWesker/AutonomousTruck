package dhbw.mosbach.cor;

import dhbw.mosbach.cor.composite.Supervisor;
import dhbw.mosbach.visitor.IPart;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class ATeam {

    @Getter
    @Setter
    private ATeam succesor;
    protected final Supervisor supervisor;

    protected ATeam(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public boolean canHandleDefect(Defect defect, List<Defect> canHandle){
        return canHandle.contains(defect);
    }

    public void repairDefect(Defect defect, IPart part){
        if (getSuccesor() != null) {
            getSuccesor().repairDefect(defect, part);
        } else{
            System.out.println("unable to repair defect");
        }
    }

}
