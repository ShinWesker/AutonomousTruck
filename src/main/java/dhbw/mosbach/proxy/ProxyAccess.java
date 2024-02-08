package dhbw.mosbach.proxy;
import dhbw.mosbach.cor.composite.TechnicalEngineer;

public enum ProxyAccess implements proxy.IAccess {
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
