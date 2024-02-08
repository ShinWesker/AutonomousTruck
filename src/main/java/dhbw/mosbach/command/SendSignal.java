package dhbw.mosbach.command;

import dhbw.mosbach.key.ReceiverModule;

public class SendSignal implements ICommand {

    private final ReceiverModule receiverModule;
    private final String signal;

    public SendSignal(String signal, ReceiverModule receiverModule) {
        this.signal = signal;
        this.receiverModule = receiverModule;

    }

    @Override
    public void execute() {
        receiverModule.receiveSignal(signal);
    }
}
