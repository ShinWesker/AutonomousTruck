package dhbw.mosbach.builder.components.light;

public sealed interface IElectronicComponent permits ElectronicComponent {
    void activate();
    void deactivate();
}
