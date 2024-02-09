package dhbw.mosbach.proxy;

import dhbw.mosbach.cor.roles.TechnicalEngineer;

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
