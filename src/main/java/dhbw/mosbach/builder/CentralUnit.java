package dhbw.mosbach.builder;

import dhbw.mosbach.builder.components.HoldingArea;
import dhbw.mosbach.builder.trailer.Trailer;
import dhbw.mosbach.builder.truck.AutonomousTruck;
import dhbw.mosbach.command.ICommand;
import dhbw.mosbach.key.IEncryption;
import dhbw.mosbach.key.ReceiverModule;
import dhbw.mosbach.key.SHA256;
import dhbw.mosbach.observer.ISensoricListener;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CentralUnit implements ISensoricListener {
    @Setter
    private ICommand command;
    private final List<String> loadingPlan = new ArrayList<>();
    @Setter
    private AutonomousTruck truck;
    private final IEncryption encryption = new SHA256();

    public void execute(){
        command.execute();
    }
    public void receiveSignal(String signal) {
        if (Objects.equals(encryption.decrypt(signal), "Kodiak2024")) {
            truck.changeState();
        } else{
            System.out.println("wrong password!");
        }
    }

    @Override
    public void detect() {
        truck.setConnected(true);
        System.out.println("Trailer successfully connected!");
    }

    @Override
    public void detect(HoldingArea holdingArea, String operation) {
        System.out.println(holdingArea.getPallet() + operation);
    }
}
