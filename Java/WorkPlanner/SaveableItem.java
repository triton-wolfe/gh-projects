import java.util.ArrayList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public abstract class SaveableItem {
    public abstract String toJSON();
    public static ArrayList<String> getNestedJSON(String json) {
        ArrayList<Integer> startInds = new ArrayList<>();
        ArrayList<Integer> endInds = new ArrayList<>();
        ArrayList<String> toReturn = new ArrayList<>();
        int depth = 0;
        boolean record = false;
        for (int i = 0; i < json.length(); i++) {
            char letter = json.charAt(i);
            if (letter == '{') {
                depth++;
                if (depth == 2) {
                    startInds.add(i);
                    record = true;
                }
            } else if (letter == '}') {
                if (depth == 2 && record) {
                    endInds.add(i);
                    record = false;
                }
                depth--;
            }
        }
        for (int j = 0; j < startInds.size(); j++) {
            toReturn.add(json.substring(startInds.get(j).intValue(),
                endInds.get(j).intValue() + 1));
        }
        return toReturn;
    }

    public static ArrayList<String> getXMLTags(String xml, String tag) {
        ArrayList<String> toReturn = new ArrayList<>();
        Pattern pat = Pattern.compile("<" + tag + ">(.*?)</" + tag + ">", Pattern.DOTALL);

        Matcher mat = pat.matcher(xml);
        while (mat.find()) {
            toReturn.add(mat.group(1));
        }
        return toReturn;
    }

    public static String getXMLTag(String xml, String tag) {
        Pattern pat = Pattern.compile("<" + tag + ">(.*?)</" + tag + ">", Pattern.DOTALL);
        Matcher mat = pat.matcher(xml);
        if (mat.find()) {
            return mat.group(1);
        } else {
            return "";
        }
    }
}
