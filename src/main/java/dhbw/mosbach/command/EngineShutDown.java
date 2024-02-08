package dhbw.mosbach.command;

import dhbw.mosbach.builder.components.Engine;

public class EngineShutDown  implements  ICommand{
    private final Engine engine;

    public EngineShutDown(Engine engine) {
        this.engine = engine;
    }

    @Override
    public void execute() {
        engine.deactivate();
    }
}
