package util;

public class MenuUtil {

    private static final String MENU_TEXT;

    static {
        String[][] options = {
                {"1. Create city", "2. Create user", "3. Change user email address"},
                {"4. Change user city", "5. Create category", "6. Create audio recording"},
                {"7. Change audio recording name", "8. Add category to audio recording", "9. Create package"},
                {"10. Change monthly price for package", "11. Create user subscription to package", "12. Create user listening"},
                {"13. Add audio to user's favorites", "14. Create user rating for audio", "15. Change user rating for audio"},
                {"16. Delete user rating for audio", "17. Delete audio by creator", "18. Get all cities"},
                {"19. Get all users", "20. Get all categories", "21. Get all audio recordings"},
                {"22. Get categories for audio", "23. Get all packages", "24. Get subscriptions for a user"},
                {"25. Get listenings for audio", "26. Get ratings for audio", "27. Get user's favorite audios"}
        };

        int colWidth = 45;
        String rowSeparator = "+" + repeat("-", colWidth) + "+" + repeat("-", colWidth) + "+" + repeat("-", colWidth) + "+\n";

        StringBuilder sb = new StringBuilder();
        sb.append(rowSeparator);

        for (String[] row : options) {
            sb.append("|");
            for (String cell : row) {
                sb.append(String.format(" %-"+(colWidth-1)+"s|", cell));
            }
            sb.append("\n").append(rowSeparator);
        }

        MENU_TEXT = sb.toString();
    }

    private static String repeat(String s, int times) {
        StringBuilder sb = new StringBuilder(s.length() * times);
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    public static void printMenu() {
        System.out.print(MENU_TEXT);
    }
}
