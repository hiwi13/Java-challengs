// Chapter1_Challenge_1_2.java
// Lottery Number Analyzer
// Demonstrates arrays, string manipulation, and loops

public class Chapter1_Challenge_1_2 {
    public static void main(String[] args) {
        String[] winningNumbers = {
            "12-34-56-78-90",
            "33-44-11-66-22",
            "01-02-03-04-05"
        };

        double highestAverage = 0;
        String bestTicket = "";

        for (String ticket : winningNumbers) {
            System.out.println("Analyzing: " + ticket);

            // Remove dashes
            String clean = ticket.replace("-", "");

            // Convert to integer array
            char[] chars = clean.toCharArray();
            int[] digits = new int[chars.length];
            int sum = 0;

            for (int i = 0; i < chars.length; i++) {
                digits[i] = Character.getNumericValue(chars[i]);
                sum += digits[i];
            }

            double average = (double) sum / digits.length;

            System.out.println("Digit Sum: " + sum + ", Digit Average: " + average);

            if (average > highestAverage) {
                highestAverage = average;
                bestTicket = ticket;
            }
        }

        System.out.println("The winning number with the highest average is: "
                + bestTicket + " with an average of " + highestAverage);
    }
}