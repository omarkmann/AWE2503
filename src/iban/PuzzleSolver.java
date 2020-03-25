package iban;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PuzzleSolver {
    private IBANValidator ibanValidator = new IBANValidator();
    public static final IBANJDBC jdbc = new IBANJDBC();

    public void checkIBANList() {
        ibanValidator.validate("BH47MCSC0oRZ2571740368");
        List<String> testNumbers = jdbc.getTestNumbers();
        List<String> validIbans = new ArrayList<>();
        for (String iban : testNumbers) {
            if (ibanValidator.validate(iban)) {
                validIbans.add(iban);
            }
        }
        validIbans.sort(new ReverseComparator());
        for (String validIban: validIbans) {
            System.out.print(validIban.charAt(1));
        }
    }
}
