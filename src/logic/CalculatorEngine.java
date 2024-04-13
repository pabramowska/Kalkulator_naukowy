package logic;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Iterator;

public class CalculatorEngine {
    /**
     * Dodaje dwie liczby.
     *
     * @param num1 Pierwsza liczba do dodania.
     * @param num2 Druga liczba do dodania.
     * @return Suma num1 i num2.
     */
    public static double add(double num1, double num2) {
        return  num1 + num2;
    }
    /**
     * Odejmuje dwie liczby.
     *
     * @param num1 Pierwsza liczba do odejmowania.
     * @param num2 Druga liczba do odejmowania.
     * @return różnica num1 i num2.
     */
    public static double subtract(double num1, double num2) {
        return  num1 - num2;
    }
    /**
     * Mnoży dwie liczby.
     *
     * @param num1 Pierwsza liczba do mnożenia.
     * @param num2 Druga liczba do mnożenia.
     * @return iloczyn num1 i num2.
     */
    public static double multiply(double num1, double num2) {
        return  num1 * num2;
    }
    /**
     * Dzieli dwie liczby.
     *
     * @param num1 dzielna.
     * @param num2 dzielnik.
     * @return iloraz num1 i num2.
     */
    public static double divide(double num1, double num2) {
        return  num1 / num2;
    }

    public static double sqrt(double num) {
        return Math.sqrt(num);
    }

    public static double log(double num) {
        return Math.log(num);
    }

    public static double sin(double num) {
        return Math.sin(Math.toRadians(num));
    }

    public static double cos(double num) {
        return Math.cos(Math.toRadians(num));
    }

    public static double tan(double num) {
        return Math.tan(Math.toRadians(num));
    }

    /**
     * metoda wykorzystywana do sortowania wyrażeń matematycznych
     @param expression wyrażenie wpisane do kalkulatora
     @return lista posortowana zgodnie z algorytmem Shunting Yard Algorithm
     */
    public static Deque<String> sortExpression(String expression) {
        Deque<String> queue = new ArrayDeque<>();
        Deque<String> operators = new ArrayDeque<>();
        StringBuilder tokenBuilder = new StringBuilder();
        StringBuilder numberBuilder = new StringBuilder();

        /**
         * Budowa kolejki i stosu do obsługi kolejności zadań
         */
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            if(Character.isSpaceChar(c)){
                continue;
            }
            /** Sprawdzamy, czy bieżący znak jest cyfrą lub kropką */
            if (Character.isDigit(c) || c == '.') {
                numberBuilder.append(c); /** Budujemy liczbę */
            } else {
                /** Jeśli mamy jakąś liczbę zbudowaną, dodajemy ją do kolejki */
                if (numberBuilder.length() > 0) {
                    queue.add(numberBuilder.toString());
                    numberBuilder.setLength(0); /**  Resetujemy StringBuilder*/
                }
                /** Budujemy stos operatorów */
                if(operators.size() == 0 || c == '(') {
                    operators.add(String.valueOf(c)); // Dodajemy operator do listy

                }else if(priority(String.valueOf(c)) >= priority(operators.peekLast())){
                    operators.add(String.valueOf(c));

                }else if(c == ')') { /**Jeżeli operator jest nawiasem zamykającym dodajemy do kolejki działania ze stosu,
                 * które pojawiły się przed nim iterując się od końca stosu */
                    Iterator<String> iter = operators.descendingIterator();

                    while (iter.hasNext()) {
                        String operator = iter.next();

                        if (operator.equals("(")) {
                            iter.remove(); /** po napotkaniu nawiasu zamykającego usuń go ze stosu i przerwij iterację */
                            break;
                        } else {
                            queue.add(String.valueOf(operator));
                            iter.remove(); /** dodaj do kolejki wszystkie operatory między nawiasami, iterując się od końca */
                        }
                    }
                }else {
                    continue;
                }
            }
        }

        /** Dodaj pozostałą liczbę, jeśli istnieje */
        if (numberBuilder.length() > 0) {
            queue.add(numberBuilder.toString());
        }
        /** Dodaj parametry do kolejki, jeżeli stos nie jest pusty */
        if (operators.size() > 0){
            Iterator<String> iter = operators.descendingIterator();
            while (iter.hasNext()) {
                String operator = iter.next();
                    queue.add(operator);
                    iter.remove();
        }}

        // Tutaj możesz wydrukować lub zwrócić listy, w zależności od potrzeb
        System.out.println("Numbers: " + queue);
        return queue;
    }


    /**
     * Ustal priorytet operatora
     * @param operator
     * @return priorytet
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
            default:
                return -1;
        }

        }


    public static void main(String[] args) {
        sortExpression("12 + (3.45 - 6) / 78");
    }
    }


