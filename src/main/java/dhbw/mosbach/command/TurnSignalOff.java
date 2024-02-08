package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.enums.Position;

public class TurnSignalOff implements ICommand {
    private final TurnSignal[] turnSignals;
    private final Position position;

    public TurnSignalOff(TurnSignal[] turnSignals, Position position) {
        this.turnSignals = turnSignals;
        this.position = position;
    }

    @Override
    public void execute() {
        for (TurnSignal t: turnSignals
        ) {
            if (t.getPosition() == position) t.deactivate();
        }
    }
}
