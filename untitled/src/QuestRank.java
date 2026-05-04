public enum QuestRank {
    ONE_STAR("1★"),
    TWO_STAR("2★"),
    THREE_STAR("3★");

    private final String label;

    // 建構子，當列舉被建立時會把 "1★" 傳進去
    QuestRank(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}