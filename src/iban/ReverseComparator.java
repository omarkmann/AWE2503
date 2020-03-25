package iban;

import java.util.Comparator;

public class ReverseComparator implements Comparator<String> {
    @Override
    public int compare(String s1, String s2) {
        StringBuilder sb1 = new StringBuilder(s1);
        String s1Reversed = sb1.reverse().toString();
        StringBuilder sb2 = new StringBuilder(s2);
        String s2Reversed = sb2.reverse().toString();
        return s1Reversed.compareTo(s2Reversed);
    }
}
