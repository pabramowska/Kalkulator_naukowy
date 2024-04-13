package logic;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Klasa dostarczająca funkcje do podstawowych operacji arytmetycznych oraz funkcji matematycznych.
 * Obsługuje również przetwarzanie wyrażeń w notacji polskiej odwrotnej (RPN) za pomocą algorytmu Shunting Yard.
 */
public class CalculatorEngine {

    /**
     * Dodaje dwie liczby.
     *
     * @param num1 Pierwsza liczba do dodania.
     * @param num2 Druga liczba do dodania.
     * @return Suma num1 i num2.
     */
    public static double add(double num1, double num2) {
        return num1 + num2;
    }

    /**
     * Odejmuje drugą liczbę od pierwszej.
     *
     * @param num1 Liczba, od której odejmujemy.
     * @param num2 Liczba, którą odejmujemy.
     * @return Wynik odejmowania num2 od num1.
     */
    public static double subtract(double num1, double num2) {
        return num1 - num2;
    }

    /**
     * Mnoży dwie liczby.
     *
     * @param num1 Pierwszy czynnik mnożenia.
     * @param num2 Drugi czynnik mnożenia.
     * @return Iloczyn num1 i num2.
     */
    public static double multiply(double num1, double num2) {
        return num1 * num2;
    }

    /**
     * Dzieli pierwszą liczbę przez drugą.
     *
     * @param num1 Dzielna.
     * @param num2 Dzielnik.
     * @return Iloraz num1 przez num2.
     */
    public static double divide(double num1, double num2) {
        return num1 / num2;
    }

    /**
     * Oblicza pierwiastek kwadratowy z liczby.
     *
     * @param num Liczba do pierwiastkowania.
     * @return Pierwiastek kwadratowy liczby num.
     */
    public static double sqrt(double num) {
        return Math.sqrt(num);
    }

    /**
     * Oblicza logarytm naturalny liczby.
     *
     * @param num Liczba, dla której obliczany jest logarytm.
     * @return Logarytm naturalny liczby num.
     */
    public static double log(double num) {
        return Math.log(num);
    }

    /**
     * Oblicza sinus kąta podanego w stopniach.
     *
     * @param num Kąt w stopniach.
     * @return Sinus kąta.
     */
    public static double sin(double num) {
        return Math.sin(Math.toRadians(num));
    }

    /**
     * Oblicza cosinus kąta podanego w stopniach.
     *
     * @param num Kąt w stopniach.
     * @return Cosinus kąta.
     */
    public static double cos(double num) {
        return Math.cos(Math.toRadians(num));
    }

    /**
     * Oblicza tangens kąta podanego w stopniach.
     *
     * @param num Kąt w stopniach.
     * @return Tangens kąta.
     */
    public static double tan(double num) {
        return Math.tan(Math.toRadians(num));
    }

    /**
     * Sortuje wyrażenie matematyczne do notacji odwrotnej polskiej (RPN) stosując algorytm Shunting Yard.
     *
     * @param expression Wyrażenie matematyczne w formie ciągu znaków.
     * @return Deque reprezentujący posortowane wyrażenie w RPN.
     */
    public static Deque<String> sortExpression(String expression) {
        Deque<String> queue = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();
        StringBuilder tokenBuilder = new StringBuilder();
        StringBuilder numberBuilder = new StringBuilder();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if(Character.isSpaceChar(c)){
                continue;
            }
            if (Character.isDigit(c) || c == '.') {
                numberBuilder.append(c);
            } else if (Character.isLetter(c)) {
                tokenBuilder.append(c);
                while (i + 1 < expression.length() && Character.isLetter(expression.charAt(i + 1))) {
                    i++;
                    tokenBuilder.append(expression.charAt(i));
                }
                operators.add(tokenBuilder.toString());
                tokenBuilder.setLength(0);
            } else {
                if (numberBuilder.length() > 0) {
                    queue.add(numberBuilder.toString());
                    numberBuilder.setLength(0);
                }
                if(tokenBuilder.length() > 0) {
                    queue.add(tokenBuilder.toString());
                    tokenBuilder.setLength(0);
                }
                if(operators.isEmpty() || c == '(') {
                    operators.add(String.valueOf(c));
                } else if(priority(String.valueOf(c)) >= priority(operators.peekLast())){
                    operators.add(String.valueOf(c));
                } else if(c == ')') {
                    Iterator<String> iter = operators.descendingIterator();
                    while (iter.hasNext()) {
                        String operator = iter.next();
                        if (operator.equals("(")) {
                            iter.remove();
                            break;
                        } else {
                            queue.add(operator);
                            iter.remove();
                        }
                    }
                } else {
                    continue;
                }
            }
        }

        if (numberBuilder.length() > 0) {
            queue.add(numberBuilder.toString());
        }
        if (!operators.isEmpty()) {
            Iterator<String> iter = operators.descendingIterator();
            while (iter.hasNext()) {
                queue.add(iter.next());
                iter.remove();
            }
        }

        return queue;
    }

    /**
     * Określa priorytet operatorów i funkcji matematycznych.
     *
     * @param operator Operator lub funkcja do oceny.
     * @return Priorytet przypisany do operatora lub funkcji.
     */
    public static int priority(String operator){
        switch (operator) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 2;
            case "^":
                return 3;
            case "sin":
            case "cos":
            case "tan":
            case "sqrt":
            case "log":
                return 4;
            default:
                return -1;
        }
    }

    /**
     * Wykonuje obliczenia na wyrażeniu matematycznym zapisanym w RPN.
     *
     * @param equasion Wyrażenie matematyczne do obliczenia.
     * @return Wynik obliczeń.
     */
    public static double calculate(String equasion) {
        Deque<String> sortedEquasion = sortExpression(equasion);
        Deque<Double> stack = new ArrayDeque<>();
        while (!sortedEquasion.isEmpty()) {
            String token = sortedEquasion.pop();
            if (isNumeric(token)) {
                stack.push(Double.parseDouble(token));
            } else {
                try {
                    switch (token) {
                        case "+":
                            stack.push(add(stack.pop(), stack.pop()));
                            break;
                        case "-":
                            double subtrahend = stack.pop();
                            stack.push(subtract(stack.pop(), subtrahend));
                            break;
                        case "*":
                            stack.push(multiply(stack.pop(), stack.pop()));
                            break;
                        case "/":
                            double divisor = stack.pop();
                            stack.push(divide(stack.pop(), divisor));
                            break;
                        case "sqrt":
                            stack.push(sqrt(stack.pop()));
                            break;
                        case "sin":
                            stack.push(sin(stack.pop()));
                            break;
                        case "cos":
                            stack.push(cos(stack.pop()));
                            break;
                        case "tan":
                            stack.push(tan(stack.pop()));
                            break;
                        case "log":
                            stack.push(log(stack.pop()));
                            break;
                        default:
                            throw new IllegalArgumentException("Unexpected token: " + token);
                    }
                } catch (NoSuchElementException e) {
                    throw new IllegalArgumentException("Invalid RPN expression", e);
                }
            }
        }

        if (stack.size() != 1) {
            throw new IllegalArgumentException("Invalid RPN expression");
        }
        return stack.pop();
    }

    /**
     * Sprawdza, czy podany ciąg znaków jest wartością numeryczną.
     *
     * @param str Ciąg do sprawdzenia.
     * @return true jeśli ciąg jest numeryczny, false w przeciwnym przypadku.
     */
    private static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
