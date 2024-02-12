package dhbw.mosbach.visitor;

import dhbw.mosbach.enums.Defect;

public interface IPart {
    void acceptPartVisitor(IPartVisitor visitor);
    void repair();
    void acceptControl(IControlVisitor visitor);

    // Methods only needed for DefectUtils
    void setDefect(Defect defect);
    Defect getDefect();
}
