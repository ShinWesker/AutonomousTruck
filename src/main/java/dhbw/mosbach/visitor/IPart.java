package dhbw.mosbach.visitor;

import dhbw.mosbach.cor.Defect;

public interface IPart {
    void accept(IPartVisitor iPartVisitor);
    void repair();
    Defect control(IControl control);
}
