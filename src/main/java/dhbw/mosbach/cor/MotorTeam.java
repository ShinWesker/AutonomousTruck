package dhbw.mosbach.cor;
import dhbw.mosbach.cor.composite.Supervisor;
import dhbw.mosbach.visitor.IPart;

import java.util.ArrayList;
import java.util.List;

public class MotorTeam extends ATeam {
    private List<Defect> canHandle;
    protected MotorTeam(Supervisor supervisor) {
        super(supervisor);
        canHandle = new ArrayList<>();
        canHandle.add(Defect.E03);
    }

    public void reparDefect(Defect defect, IPart part) {
        if (canHandleDefect(defect,canHandle)) {
            System.out.println( this.getClass().getSimpleName()+ " Can handle this: " + defect);
            supervisor.delegate(part);
        } else{
            super.reparDefect(defect,part);
        }

    }
}
