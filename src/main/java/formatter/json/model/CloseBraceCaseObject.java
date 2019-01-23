package formatter.json.model;

import lombok.Getter;

@Getter
public class CloseBraceCaseObject {
    private int iterator;
    private int countTabs;
    private String result;

    public CloseBraceCaseObject(int iterator, int countTabs, String result) {
        this.iterator = iterator;
        this.countTabs = countTabs;
        this.result = result;
    }
}
