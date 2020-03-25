package iban;

import java.math.BigInteger;

public class IBANValidator {
    public boolean validate(String iban) {
        String countryCode = iban.substring(0, 2);
        String formattedIBAN = iban.substring(4);
        IBANPattern pattern = PuzzleSolver.jdbc.getPatternByCountryCode(countryCode);
        if (iban.length() != pattern.getLength()) {
            return false;
        }
        String[] patternElements = pattern.getPattern().split(",");
        for (String patternElement : patternElements) {
            char type = patternElement.charAt(patternElement.length() - 1);
            int amount = Integer.parseInt(patternElement.substring(0, patternElement.length() - 1));
            boolean patternValid = checkIbanChars(formattedIBAN, type, amount);
            if (!patternValid) {
                return false;
            }
            formattedIBAN = formattedIBAN.substring(amount);
        }
       return checkChecksum(iban);
    }

    private boolean checkIbanChars(String formattedIBAN, char type, int amount) {
        for (int i = 0; i < amount; i++) {
            char c = formattedIBAN.charAt(i);
            switch (type) {
                case 'a':
                    if (!Character.isUpperCase(c)) {
                        return false;
                    }
                    break;
                case 'n':
                    if (!Character.isDigit(c)) {
                        return false;
                    }
                    break;
            }

        }
        return true;
    }

    private boolean checkChecksum(String iban) {
        int checksum = Integer.parseInt(iban.substring(2, 4));
        if (checksum < 2 || checksum > 98) {
            return false;
        }
        BigInteger formattedIban = transformIban(iban);
        BigInteger result = formattedIban.mod(new BigInteger("97"));
        return result.intValue() == 1;
    }


    private BigInteger transformIban(String iban) {
        iban = transformCountyCodeAndChecksum(iban);
        String formattedIban = iban;
        for (int i = 0; i < iban.length(); i++) {
            char c = iban.charAt(i);
            if (Character.isAlphabetic((c))) {
                formattedIban = formattedIban.replace("" + c, "" + transformAlphabeticChar(c));
            }
        }
        return new BigInteger(formattedIban);
    }

    private String transformCountyCodeAndChecksum(String iban) {
        String countryCode = iban.substring(0, 2);
        String transformedCountyCode = "" + transformAlphabeticChar(countryCode.charAt(0))
                + transformAlphabeticChar(countryCode.charAt(1));
        return iban.substring(4) + transformedCountyCode + iban.substring(2, 4);
    }

    private int transformAlphabeticChar(char c) {
        return (int) Character.toUpperCase(c) - 55;
    }


}
