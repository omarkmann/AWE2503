package iban;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class IBANJDBC {
    private Connection c;
    private Statement statement;

    public IBANJDBC() {
        open();
    }

    private void open() {
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:IBAN.sqlite");
            System.out.println("Opened database successfully");
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
    }

    public List<String> getTestNumbers() {
        List<String> testNumbers = new ArrayList<>();
        try {
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM testnumbers;");

            while (rs.next()) {
                testNumbers.add(rs.getString("num"));
            }
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return testNumbers;
    }

    public IBANPattern getPatternByCountryCode(String countryCode) {
        String pattern = "";
        int length = 0;
        try {
            statement = c.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM IBANSpec WHERE CountryCode = '"
                    + countryCode + "';");
            while (rs.next()) {
                pattern = rs.getString("Pattern");
                length = rs.getInt("Length");
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
        return new IBANPattern(pattern, length);
    }
}