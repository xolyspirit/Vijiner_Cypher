package sample;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Lgram {
    private Integer gcd;
    private List<Integer> distances;
    private StringProperty lGram;

    Lgram(String lGram, List<Integer> collisions) {
        distances = new ArrayList<Integer>();
        for (int i = 1; i < collisions.size(); i++) {
            distances.add(collisions.get(i) - collisions.get(i - 1));
        }

        this.lGram = new SimpleStringProperty(lGram);
        this.gcd = distances.size() > 1 ? getGCD(distances) : distances.get(0);
    }

    public Integer getGcd() {
        return gcd;
    }

    public String getDistancess() {
        return distances.toString();
    }

    public StringProperty distancesProperty() {
        return new SimpleStringProperty(distances.toString());
    }

    public String getlGram() {
        return lGram.get();
    }

    public StringProperty lGramProperty() {
        return lGram;
    }

    private Integer getGCD(List<Integer> digits) {
        Integer gcd = getGCD(digits.get(0), digits.get(1));
        for (int i = 2; i < digits.size(); i++) {
            gcd = getGCD(gcd, digits.get(i));
        }
        return gcd;
    }

    private Integer getGCD(Integer a, Integer b) {
        return BigInteger.valueOf(b).gcd(BigInteger.valueOf(a)).intValue();
    }
}

