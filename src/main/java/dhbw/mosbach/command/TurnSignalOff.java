package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.light.TurnSignal;
import dhbw.mosbach.builder.enums.Position;

import java.util.Arrays;

public class TurnSignalOff implements ICommand {
    private final TurnSignal[] turnSignals;
    private final Position position;

    public TurnSignalOff(TurnSignal[] turnSignals, Position position) {
        this.turnSignals = turnSignals;
        this.position = position;
    }

    @Override
    public void execute() {
        Arrays.stream(turnSignals)
                .filter(turnSignal -> turnSignal.getPosition() == position)
                .findFirst()
                .ifPresent(TurnSignal::deactivate);
    }
}
