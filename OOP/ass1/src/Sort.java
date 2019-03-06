/**
 * The main class for assignment #1, Task 3.
 * The class sorts an array of numbers by a given order.
 *
 * @author Amit Twito
 * @version 1.0
 * @since 22.2.19 */
public class Sort {

    /**
     * Converts the strings from the args String array to int numbers,
     * and prints the sorted numbers array by a given order.
     *
     * @param args String type array from the command line :the first cell is the order of the sorting,
     * the other cells are the "list" of numbers .*/
    public static void main(String[] args) {

        //Convert the strings in the args array to int numbers.
        int[] numbersArray = stringsToInts(args);

        //Get the value of the first cell in the args array as the order String.
        String order = args[0];

        //Sort the array by order and print the sorted array.
        sortByOrderAndPrintArray(order, numbersArray);
    }

    /**
     * Sorts the numbers from the given int array by a given order,
     * and prints the sorted numbers array.
     *
     * @param order a String, the sorting order.
     * Can be : "asc" or "desc".
     *
     * @param numbers Int type array : "list" of numbers to be sorted.*/
    public static void sortByOrderAndPrintArray(String order, int[] numbers) {
        int n = numbers.length;
        int temp = 0;
        // Boolean indicator for the while loop.
        boolean swapped = true;

        //First case - ascending order.
        if (order.equals("asc")) {
            //Bubble sorting - taken from Bubble Sort wiki page.
            while (swapped) {
                swapped = false;
                for (int i = 1; i < n; i++) {
                    //Check if number[i] and number[i - 1] are not in the right order.
                    if (numbers[i - 1] > numbers[i]) {
                        //Swap them.
                        temp = numbers[i];
                        numbers[i] = numbers[i - 1];
                        numbers[i - 1] = temp;
                        //Remember that has been a swapping.
                        swapped = true;
                    }
                }
            }
        }

        //Second case - descending order.
        if (order.equals("desc")) {
            //Bubble sorting - taken from Bubble Sort wiki page.
            while (swapped) {
                swapped = false;
                for (int i = 1; i < n; i++) {
                    //Check if number[i] and number[i - 1] are not in the right order.
                    if (numbers[i - 1] < numbers[i]) {
                        //Swap them.
                        temp = numbers[i];
                        numbers[i] = numbers[i - 1];
                        numbers[i - 1] = temp;
                        //Remember that there has been a swapping.
                        swapped = true;
                    }
                }
            }
        }

        //Print the sorted array, all numbers in the same line, separated ny spaces.
        for (int num: numbers) {
            System.out.print(num + " ");
        }
        //Print a new line for the integrity of the output.
        System.out.println("");
    }


    /**
     * Converts the strings from a given String array to int numbers.
     *
     * @param numbers String type array, "list" of numbers .
     * @return Int array, built from the converted numbers.*/
    public static int[] stringsToInts(String[] numbers) {
        /*Create a new int array with the same length as the given array less one -
        ignore the first cell which is the sorting order string.*/
        int[] numbersArray = new int[numbers.length - 1];

        /*For each of the string in the given array, starting from the second cell (index 1),
        convert it to int number and put it in the numbersArray.*/
        for (int i = 1; i < numbers.length; i++) {
            numbersArray[i - 1] = Integer.parseInt(numbers[i]);
        }
        return numbersArray;
    }
}
