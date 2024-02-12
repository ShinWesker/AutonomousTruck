package dhbw.mosbach.visitor;

import dhbw.mosbach.enums.Defect;

public interface IPart {
    void acceptPartVisitor(IPartVisitor iPartVisitor);
    void repair();
    void acceptControl(IControlVisitor control);
    Defect getDefect();
    void setDefect(Defect defect);
}
