public class Duke {
    public static void main(String[] args) {
        String logo = " ____        _        \n"
                + "|  _ \\ _   _| | _____ \n"
                + "| | | | | | | |/ / _ \\\n"
                + "| |_| | |_| |   <  __/\n"
                + "|____/ \\__,_|_|\\_\\___|\n";
        String welcomeMsg = "Hello from\n" + logo;
        printOutput(welcomeMsg);

        // greet the users
        String greetings = "Hello! I'm Orion\n"
                + "What can I do for you?\n";
        printOutput(greetings);

        printOutput("Bye. Hope to see you again soon!\n");
    }

    private static void drawLine() {
        int lineLength = 60; // Adjust the length of the line as needed
        char horizontalLineChar = '\u2500'; // Unicode character for a horizontal line

        for (int i = 0; i < lineLength; i++) {
            System.out.print(horizontalLineChar);
        }
        System.out.println();
    }

    private static void printOutput(String output) {
        System.out.println(output);
        drawLine();
    }
}
