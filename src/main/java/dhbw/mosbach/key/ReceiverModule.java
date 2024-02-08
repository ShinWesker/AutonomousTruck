package dhbw.mosbach.key;

import dhbw.mosbach.builder.CentralUnit;

public class ReceiverModule {

    private final CentralUnit centralUnit;

    public ReceiverModule(CentralUnit centralUnit) {
        this.centralUnit = centralUnit;
    }

    public void receiveSignal(String signal) {
        centralUnit.receiveSignal(signal);
    }
}
