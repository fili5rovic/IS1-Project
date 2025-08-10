package util;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

public class JsonUtil {

    public static String readJson(String name) {
        String path = "jsonInput/" + name + ".json";
        try (InputStream is = JsonUtil.class.getClassLoader().getResourceAsStream(path)) {
            if (is == null) {
                throw new RuntimeException("File not found: " + path);
            }
            Scanner scanner = new Scanner(is, "UTF-8").useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            throw new RuntimeException("Error reading file: " + path, e);
        }
    }

    public static String format(String json) {
        StringBuilder prettyJson = new StringBuilder();
        int indent = 0;
        boolean inQuotes = false;
        for (char ch : json.toCharArray()) {
            switch (ch) {
                case '"':
                    prettyJson.append(ch);
                    if (ch == '"') {
                        inQuotes = !inQuotes;
                    }
                    break;
                case '{':
                case '[':
                    prettyJson.append(ch);
                    if (!inQuotes) {
                        prettyJson.append('\n');
                        indent++;
                        for (int i = 0; i < indent; i++) {
                            prettyJson.append("    ");
                        }
                    }
                    break;
                case '}':
                case ']':
                    if (!inQuotes) {
                        prettyJson.append('\n');
                        indent--;
                        for (int i = 0; i < indent; i++) {
                            prettyJson.append("    ");
                        }
                    }
                    prettyJson.append(ch);
                    break;
                case ',':
                    prettyJson.append(ch);
                    if (!inQuotes) {
                        prettyJson.append('\n');
                        for (int i = 0; i < indent; i++) {
                            prettyJson.append("    ");
                        }
                    }
                    break;
                case ':':
                    prettyJson.append(ch);
                    if (!inQuotes) {
                        prettyJson.append(" ");
                    }
                    break;
                default:
                    prettyJson.append(ch);
            }
        }
        return prettyJson.toString();
    }

    /** 
     * Very simple recursive flattening of JSON objects using dot notation.
     * Only works for well-formed JSON, objects and arrays, with primitive values.
     * This is NOT a full JSON parser! It works for most simple Java-generated JSON.
     */
    public static void printJsonTable(String json) {
        json = json.trim();
        if (json.startsWith("[")) {
            List<Map<String, String>> rows = parseJsonArray(json);
            if (rows.isEmpty()) {
                System.out.println("(empty array)");
                return;
            }
            Set<String> columns = new LinkedHashSet<>();
            for (Map<String, String> m : rows) columns.addAll(m.keySet());
            printTable(columns, rows);
        } else if (json.startsWith("{")) {
            Map<String, String> row = flattenJson(parseJsonObject(json), "");
            printTable(row.keySet(), Collections.singletonList(row));
        } else {
            System.out.println("Invalid JSON for table display.");
        }
    }

    // --- Minimal JSON parsing and flattening ---

    // Parses a JSON array of objects
    private static List<Map<String, String>> parseJsonArray(String json) {
        List<Map<String, String>> list = new ArrayList<>();
        int depth = 0, start = -1;
        boolean inQuotes = false;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"') inQuotes = !inQuotes;
            if (!inQuotes) {
                if (c == '{') {
                    if (depth == 0) start = i;
                    depth++;
                } else if (c == '}') {
                    depth--;
                    if (depth == 0 && start != -1) {
                        list.add(flattenJson(parseJsonObject(json.substring(start, i + 1)), ""));
                        start = -1;
                    }
                }
            }
        }
        return list;
    }

    // Parses a JSON object into a Map<String, Object> (recursive, for objects only)
    private static Map<String, Object> parseJsonObject(String json) {
        Map<String, Object> map = new LinkedHashMap<>();
        json = json.trim();
        if (json.startsWith("{")) json = json.substring(1);
        if (json.endsWith("}")) json = json.substring(0, json.length() - 1);

        boolean inQuotes = false;
        int brace = 0, bracket = 0, start = 0;
        String key = null;
        for (int i = 0; i < json.length(); i++) {
            char c = json.charAt(i);
            if (c == '"') inQuotes = !inQuotes;
            if (!inQuotes) {
                if (c == '{') brace++;
                if (c == '}') brace--;
                if (c == '[') bracket++;
                if (c == ']') bracket--;

                if (c == ':' && key == null && brace == 0 && bracket == 0) {
                    key = stripQuotes(json.substring(start, i).trim());
                    start = i + 1;
                } else if ((c == ',' && brace == 0 && bracket == 0) || i == json.length() - 1) {
                    int end = (i == json.length() - 1) ? (i + 1) : i;
                    String value = json.substring(start, end).trim();
                    if (key != null) {
                        if (value.startsWith("{")) {
                            map.put(key, parseJsonObject(value));
                        } else if (value.startsWith("[")) {
                            map.put(key, value); // Just store as string for arrays
                        } else {
                            map.put(key, stripQuotes(value));
                        }
                    }
                    key = null;
                    start = i + 1;
                }
            }
        }
        return map;
    }

    // Flattens a Map<String, Object> recursively into Map<String, String> using dot notation
    private static Map<String, String> flattenJson(Map<String, Object> map, String prefix) {
        Map<String, String> flatMap = new LinkedHashMap<>();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            String key = prefix.isEmpty() ? entry.getKey() : (prefix + "." + entry.getKey());
            Object value = entry.getValue();
            if (value instanceof Map) {
                flatMap.putAll(flattenJson((Map<String, Object>) value, key));
            } else {
                flatMap.put(key, value == null ? "" : value.toString());
            }
        }
        return flatMap;
    }

    private static String stripQuotes(String s) {
        s = s.trim();
        if (s.startsWith("\"") && s.endsWith("\"") && s.length() > 1) {
            return s.substring(1, s.length() - 1);
        }
        return s;
    }

    // --- Table printing ---

    private static void printTable(Set<String> columns, List<Map<String, String>> rows) {
        Map<String, Integer> widths = new LinkedHashMap<>();
        for (String col : columns) {
            widths.put(col, col.length());
        }
        for (Map<String, String> row : rows) {
            for (String col : columns) {
                int len = row.getOrDefault(col, "").length();
                if (len > widths.get(col)) widths.put(col, len);
            }
        }
        // Print header
        StringBuilder header = new StringBuilder("|");
        StringBuilder sep = new StringBuilder("+");
        for (String col : columns) {
            int w = widths.get(col);
            header.append(" ").append(pad(col, w)).append(" |");
            for (int i = 0; i < w + 2; i++) sep.append('-');
            sep.append("+");
        }
        System.out.println(sep);
        System.out.println(header);
        System.out.println(sep);
        // Print rows
        for (Map<String, String> row : rows) {
            StringBuilder line = new StringBuilder("|");
            for (String col : columns) {
                line.append(" ").append(pad(row.getOrDefault(col, ""), widths.get(col))).append(" |");
            }
            System.out.println(line);
        }
        System.out.println(sep);
    }

    private static String pad(String s, int width) {
        if (s == null) s = "";
        return String.format("%-" + width + "s", s);
    }
}