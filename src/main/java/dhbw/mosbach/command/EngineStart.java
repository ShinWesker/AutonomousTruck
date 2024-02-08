package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.Engine;

public class EngineStart  implements ICommand{
    private final Engine engine;

    public EngineStart(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void execute() {
        engine.deactivate();
    }
}
