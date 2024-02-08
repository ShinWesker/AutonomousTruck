package dhbw.mosbach.cor;

import dhbw.mosbach.cor.composite.Supervisor;
import dhbw.mosbach.visitor.IPart;

import java.util.List;

public abstract class ATeam {

    private ATeam succesor;
    protected final Supervisor supervisor;

    protected ATeam(Supervisor supervisor) {
        this.supervisor = supervisor;
    }

    public void setSuccesor(ATeam succesor) {
        this.succesor = succesor;
    }

    public ATeam getSuccesor() {
        return succesor;
    }

    public boolean canHandleDefect(Defect defect, List<Defect> canHandle){
        return canHandle.contains(defect);
    }

    public void reparDefect(Defect defect, IPart part){
        if (getSuccesor() != null) {
            getSuccesor().reparDefect(defect, part);
        } else{
            System.out.println("unable to repair defect");
        }
    }

}
