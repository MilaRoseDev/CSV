import java.util.ArrayList;
import java.util.List;

public class StringCalculator {

    public int add(String numbers) {
        if (numbers.isEmpty()) {
            return 0;
        }

        String[] delimiters = { ",", "\n" };

        if (numbers.startsWith("//")) {
            String customDelimiter = numbers.substring(2, numbers.indexOf("\n"));
            delimiters = parseDelimiters(customDelimiter);
            numbers = numbers.substring(numbers.indexOf("\n") + 1);
        }

        String[] numberArray = numbers.split(String.join("|", delimiters));

        return sumNumbers(numberArray);
    }

    private String[] parseDelimiters(String customDelimiter) {
        List<String> delimiters = new ArrayList<>();

        if (customDelimiter.startsWith("[") && customDelimiter.endsWith("]")) {
            customDelimiter = customDelimiter.substring(1, customDelimiter.length() - 1);
            delimiters.addAll(List.of(customDelimiter.split("]\\[")));
        } else {
            delimiters.add(customDelimiter);
        }

        return delimiters.toArray(new String[0]);
    }

    private int sumNumbers(String[] numbers) {
        int sum = 0;
        List<Integer> negativeNumbers = new ArrayList<>();

        for (String number : numbers) {
            int num = Integer.parseInt(number);

            if (num < 0) {
                negativeNumbers.add(num);
            } else if (num <= 1000) {
                sum += num;
            }
        }

        if (!negativeNumbers.isEmpty()) {
            throw new IllegalArgumentException("Negatives not allowed: " + negativeNumbers);
        }

        return sum;
    }

    public static void main(String[] args) {
        StringCalculator calculator = new StringCalculator();

        // Example usage:
        System.out.println(calculator.add(""));                // Step 1
        System.out.println(calculator.add("1"));               // Step 1
        System.out.println(calculator.add("1,2"));             // Step 1
        System.out.println(calculator.add("1\n2,3"));          // Step 3
        System.out.println(calculator.add("//;\n1;2"));         // Step 4
        System.out.println(calculator.add("-1,2"));            // Step 5
        System.out.println(calculator.add("1001,2"));          // Step 6
        System.out.println(calculator.add("//[|||]\n1|||2|||3"));// Step 7
        System.out.println(calculator.add("//[|][%]\n1|2%3"));  // Step 8
        System.out.println(calculator.add("//[###][***]\n1###2***3")); // Step 9
    }
}
