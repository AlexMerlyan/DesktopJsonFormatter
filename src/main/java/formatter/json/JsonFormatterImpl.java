package formatter.json;

import formatter.json.model.CloseBraceCaseObject;
import org.apache.commons.lang3.StringUtils;

public class JsonFormatterImpl implements JsonFormatter {
    private static final byte OPEN_SQUARE_BRACKET_ASCII_TABLE_CODE = 91;
    private static final byte CLOSE_SQUARE_BRACKET_ASCII_TABLE_CODE = 93;
    private static final byte OPEN_BRACE_ASCII_TABLE_CODE = 123;
    private static final byte CLOSE_BRACE_ASCII_TABLE_CODE = 125;
    private static final byte COMMA_ASCII_TABLE_CODE = 44;


    @Override
    public String formatJson(final String json) {
        String clearJson = removeLineSeparatorsTabsAndSpaces(json);
        byte[] jPathQueryBytes = clearJson.getBytes();
        byte symbolCode;
        StringBuilder sb = new StringBuilder();
        int countTabs = 0;
        for (int i = 0; i < jPathQueryBytes.length; i++) {
            symbolCode = jPathQueryBytes[i];
            if (symbolCode == COMMA_ASCII_TABLE_CODE) {
                sb.append((char) symbolCode);
                sb.append(getSeparatorAndTabs(countTabs));
            } else if (isOpenBracesFormatNeeded(symbolCode)) {
                countTabs++;
                sb.append((char) symbolCode);
                sb.append(getSeparatorAndTabs(countTabs));
            } else if (isCloseBraceCase(symbolCode)) {
                CloseBraceCaseObject braceCaseObject = formatCloseBraceCase(jPathQueryBytes, i, countTabs, (char) symbolCode);
                i = braceCaseObject.getIterator();
                countTabs = braceCaseObject.getCountTabs();
                sb.append(braceCaseObject.getResult());
            } else {
                sb.append((char) symbolCode);
            }
        }

        return sb.toString();
    }

    private boolean isCloseBraceCase(byte symbolCode) {
        return symbolCode == CLOSE_BRACE_ASCII_TABLE_CODE || symbolCode == CLOSE_SQUARE_BRACKET_ASCII_TABLE_CODE;
    }

    private CloseBraceCaseObject formatCloseBraceCase(byte[] jPathQueryBytes, int i, int countTabs, char symbol) {
        String result;
        if (jPathQueryBytes.length > i + 1 && jPathQueryBytes[i + 1] == COMMA_ASCII_TABLE_CODE) {
            countTabs--;
            result = getSeparatorAndTabs(countTabs) + String.valueOf(symbol);
            i++;
            result += (char) jPathQueryBytes[i] + getSeparatorAndTabs(countTabs);
        } else {
            countTabs--;
            result = getSeparatorAndTabs(countTabs) + symbol;
        }
        return new CloseBraceCaseObject(i, countTabs, result);
    }

    private String getSeparatorAndTabs(int countTabs) {
        StringBuilder sb = new StringBuilder();
        sb.append(LINE_SEPARATOR);
        for (int i = 0; i < countTabs; i++) {
            sb.append(TAB);
        }
        return sb.toString();
    }

    private boolean isOpenBracesFormatNeeded(byte symbol) {
        return isOpenBracketFormatNeeded(symbol) || isOpenSquareBracketFormatNeeded(symbol);
    }

    private boolean isOpenBracketFormatNeeded(byte currentSymbol) {
        return currentSymbol == OPEN_BRACE_ASCII_TABLE_CODE;
    }

    private boolean isOpenSquareBracketFormatNeeded(byte symbol) {
        return symbol == OPEN_SQUARE_BRACKET_ASCII_TABLE_CODE;
    }

    private String removeLineSeparatorsTabsAndSpaces(String jPathQuery) {
        return jPathQuery.replaceAll(SPACE, StringUtils.EMPTY)
                .replaceAll(LINE_SEPARATOR, StringUtils.EMPTY)
                .replaceAll(TAB, StringUtils.EMPTY);
    }
}
