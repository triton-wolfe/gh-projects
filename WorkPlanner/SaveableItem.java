import java.util.ArrayList;

public abstract class SaveableItem {
    public abstract String toJSON();
    public static ArrayList<String> getNested(String json) {
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
}
