package iban;

public class IBANPattern {
    private String pattern;
    private int length;

    public IBANPattern(String pattern, int length) {
        this.pattern = pattern;
        this.length = length;
    }

    public String getPattern() {
        return pattern;
    }

    public int getLength() {
        return length;
    }
}
