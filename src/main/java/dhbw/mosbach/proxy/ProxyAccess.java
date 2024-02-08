package proxy;

import dhbw.mosbach.cor.composite.TechnicalEngineer;
import dhbw.mosbach.visitor.IPart;

import java.util.Objects;

public enum ProxyAccess implements IAccess {
    INSTANCE;
    @Override
    public void grant(TechnicalEngineer technicalEngineer) {
        if (technicalEngineer.getPassword().equals("PasswordOperation")) {
            new RealAccess().grant(technicalEngineer);
        } else{
            System.out.println("Access denied!");
        }
    }
}
