public abstract class SaveableItem {
    public abstract String toJSON();
    public static abstract SaveableItem fromJSON(String json);
}
