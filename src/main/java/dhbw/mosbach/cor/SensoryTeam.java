package dhbw.mosbach.cor;
import dhbw.mosbach.cor.composite.Supervisor;
import dhbw.mosbach.visitor.IPart;

import java.util.ArrayList;
import java.util.List;

public class SensoryTeam extends ATeam {

    private List<Defect> canHandle;

    protected SensoryTeam(Supervisor supervisor, ATeam successor) {
        super(supervisor);
        setSuccesor(successor);
        canHandle = new ArrayList<>();
        canHandle.add(Defect.E01);
        canHandle.add(Defect.E02);
    }

    @Override
    public void repairDefect(Defect defect, IPart part) {
        if (canHandleDefect(defect,canHandle)) {
            System.out.println( this.getClass().getSimpleName()+ " Can handle this: " + defect);
            supervisor.delegate(part);
        } else{
            super.repairDefect(defect, part);
        }

    }
}
