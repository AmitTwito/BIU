/**
 * The main class for assignment #1, Task 1,
 * The class calculates and prints the factorial of a given number.
 * @author Amit Twito
 * @version 1.0
 * @since 22.2.19*/
public class Factorial {
    /**
     * Converts the first value in the args String array to a long number,
     * and prints the factorial of the given number:
     * first time through recursive method,
     * second time through iterative method.
     *
     * @param args String type array, the first argument from the
     * command line is the number for the factorial calculation.*/
    public static void main(String[] args) {

        //Convert the strings to long numbers.
        long inputNumber = Long.parseLong(args[0]);

        //Print the the method results.
        System.out.println("recursive: " + factorialRecursive(inputNumber));
        System.out.println("iterative: " + factorialItr(inputNumber));
    }

    /**
     * Gets a long type number and calculates the factorial of the number in iterative way.
     *
     * @param n The number for the factorial calculation.
     * @return the factorial calculation.*/
    public static long factorialItr(long n) {
        //If 0 is given, 0! equals to 1.
        if (n == 0) {
            return 1;
        } else {
            long factorialResult = 1;

            //Multiply every number by factorialResult from 1 to n.
            for (int i = 1; i <= n; i++) {
                factorialResult = factorialResult * i;
            }
            return factorialResult;
        }
    }

    /**
     * Gets a long type number and calculates the factorial of the number in recursive way.
     *
     * @param n The number for the factorial calculation.
     * @return the factorial calculation.*/
    public static long factorialRecursive(long n) {
        /*Stopping condition : if n reaches 1 it means its the end of the multification,
        * or if 0 is given, 0! equals to 1.*/
        if (n == 0) {
            return 1;
        }
        //Return the multification of n with the result of this method with parameter n-1;
        return n * (factorialRecursive(n - 1));
    }
}

