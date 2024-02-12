package dhbw.mosbach.cor;
import dhbw.mosbach.enums.Defect;
import dhbw.mosbach.visitor.IPart;
import lombok.Getter;

@Getter
public class ServiceCenter {
    private final SensoryTeam sensoryTeam;
    public ServiceCenter(SensoryTeam sensoryTeam){
        this.sensoryTeam = sensoryTeam;
    }
    public void handleDefect(Defect defect, IPart part){
        if (sensoryTeam != null) {
            sensoryTeam.repairPart(defect, part);
        }
    }
}
