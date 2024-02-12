package dhbw.mosbach.state;

import dhbw.mosbach.command.ICommand;
import dhbw.mosbach.command.SendSignal;
import lombok.Getter;

public class ElectronicKey {
    private final ICommand command;
    @Getter
    private final String password;

    public ElectronicKey(String password, ReceiverModule receiverModule) {
        IEncryption encryption = new SHA256();
        this.password = encryption.encrypt(password);
        command = new SendSignal(this.password,receiverModule);
    }

    public void sendSignal(){
        command.execute();
    }
}
