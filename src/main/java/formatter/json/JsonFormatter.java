package formatter.json;

public interface JsonFormatter {
    String LINE_SEPARATOR = System.lineSeparator();
    String TAB = "\t";
    String SPACE = " ";

    String formatJson(String jPathQuery);
}
