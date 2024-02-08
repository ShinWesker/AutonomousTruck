package dhbw.mosbach.key;

import dhbw.mosbach.command.ICommand;
import dhbw.mosbach.command.SendSignal;

public class ElectronicKey {
    private final ICommand command;

    public ElectronicKey(String password, ReceiverModule receiverModule) {
        IEncryption encryption = new SHA256();
        command = new SendSignal(encryption.encrypt(password),receiverModule);
    }

    public void sendSignal(){
        command.execute();
    }
}
