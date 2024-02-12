package dhbw.mosbach.visitor;

import dhbw.mosbach.enums.Defect;

import java.util.Random;

public class DefectUtils {

    static boolean activateRandom = false;

    private DefectUtils(){
    }

    private static final Random random = new Random();
    public static boolean checkDefect(IPart part) {
        if (activateRandom) {
            if (part.getDefect() != null) {
                return true;
            }

            if (random.nextInt(100) < 5) {
                Defect newDefect = Defect.values()[random.nextInt(Defect.values().length)];
                part.setDefect(newDefect);
                System.out.println("New defect introduced: " + newDefect);
                return true;
            }
        }

        return false;
    }
    public static void printDefect(IPart part){
        System.out.printf("""
                    Defect occurred!
                    Part: %s
                    DefectType: %s
                    HashCode: %d
                    """,
                part.getClass().getSimpleName(), part.getDefect().toString(), part.hashCode());
    }
}


