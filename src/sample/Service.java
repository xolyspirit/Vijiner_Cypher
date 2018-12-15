package sample;

import java.util.*;

public class Service {
    private final char[] alfavit = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя ".toCharArray();
    private int smesh = alfavit.length;
    private char[][] tabulaRecta = new char[smesh][smesh];
    private int lgramMinLenght = 3;

    Service() {
        initTabulaRecta();
    }

    private void initTabulaRecta() {
        int shift = 0;
        for (int i = 0; i < smesh; i++) {
            for (int j = 0; j < smesh; j++) {
                shift = i + j;
                if (shift >= smesh) {
                    shift %= smesh;
                }
                tabulaRecta[i][j] = alfavit[shift];
            }
        }
    }

    public String encode(String text, String key) {
        int x = 0, y = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char keyChar = key.charAt(i % key.length());
            char textChar = text.charAt(i);
            for (int j = 0; j < smesh; j++) {
                if (tabulaRecta[j][0] == keyChar) {
                    x = j;
                    break;
                }
            }
            for (int j = 0; j < smesh; j++) {
                if (tabulaRecta[0][j] == textChar) {
                    y = j;
                    break;
                }
            }
            sb.append(tabulaRecta[x][y]);
        }
        return sb.toString();
    }

    public String decode(String text, String key) {
        int x = 0, y = 0;
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char keyChar = key.charAt(i % key.length());
            char textChar = text.charAt(i);
            for (int j = 0; j < smesh; j++) {
                if (tabulaRecta[j][0] == keyChar) {
                    x = j;
                    break;
                }
            }
            for (int j = 0; j < smesh; j++) {
                if (tabulaRecta[x][j] == textChar) {
                    y = j;
                    break;
                }
            }
            ans.append(tabulaRecta[0][y]);
        }
        analyzeTextByKasiski(text);
        return ans.toString();
    }

    public List<Lgram> analyzeTextByKasiski(String text) {
        List<String> tokens = findSubStrings(text);
        return findCollisions(tokens, text);
    }

    private List<String> findSubStrings(String text) {
        List<String> tokens = new LinkedList<String>();
        for (int i = 0; i < text.length() - lgramMinLenght; i++) {
            tokens.addAll(Arrays.asList(text.substring(i).split("(?<=\\G.{" + lgramMinLenght + "})")));
        }
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).length() < lgramMinLenght) {
                tokens.remove(i);
                i--;
            }
        }
        Set<String> temp = new HashSet<String>(tokens);
        tokens.clear();
        tokens.addAll(temp);
        return tokens;
    }

    private List<Lgram> findCollisions(List<String> tokens, String text) {
        List<Lgram> lgrams = new ArrayList<Lgram>();
        int indexAfterToken;
        Map<String, List<Integer>> collisions = new HashMap<String, List<Integer>>();
        for (String temp : tokens) {
            indexAfterToken = text.indexOf(temp) + lgramMinLenght;
            collisions.put(temp, new ArrayList<Integer>());
            collisions.get(temp).add(text.indexOf(temp));
            while (text.substring(indexAfterToken).contains(temp)) {
                indexAfterToken += text.substring(indexAfterToken).indexOf(temp);
                collisions.get(temp).add(indexAfterToken);
                indexAfterToken += lgramMinLenght;
                if (indexAfterToken + lgramMinLenght >= text.length()) {
                    break;
                }
            }
        }
        Set<String> temp = new HashSet<String>(collisions.keySet());
        for (String tempS : temp) {
            if (collisions.get(tempS).size() == 1) {
                collisions.remove(tempS);
            }
        }
        for (Object key : collisions.keySet()) {
            List<Integer> tempList = collisions.get(key.toString());
            lgrams.add(new Lgram(key.toString(), tempList));
        }
        return lgrams;
    }

    public Integer getMinValueOfGcd(List<Lgram> lgrams) {
        int min = lgrams.get(0).getGcd();
        for (Lgram l : lgrams) {
            if (l.getGcd() < min) {
                min = l.getGcd();
            }
        }
        return  min;
    }
}

