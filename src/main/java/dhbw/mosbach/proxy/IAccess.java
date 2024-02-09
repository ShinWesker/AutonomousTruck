package dhbw.mosbach.proxy;

import dhbw.mosbach.cor.roles.TechnicalEngineer;

public interface IAccess {
    void grant(TechnicalEngineer technicalEngineer);
}
