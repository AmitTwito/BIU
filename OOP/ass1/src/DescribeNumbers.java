/**
 * The main class for assignment #1, Task 2.
 * The class calculates and prints the minimum, maximum and average of a given list of numbers.
 * @author Amit Twito
 * @version 1.0
 * @since 22.2.19*/
public class DescribeNumbers {
     /**
     * Converts the strings from the args String array to int numbers,
     * and prints the minimum number, maximum number and the average number.
     *
     * @param args String type array, "list" of numbers from the command line.*/
    public static void main(String[] args) {
        //Convert the array to int numbers array.
        int[] numbersArray = stringsToInts(args);

        //Print the min, max and avg.
        System.out.println("min: " + min(numbersArray));
        System.out.println("max: " + max(numbersArray));
        System.out.println("avg: " + avg(numbersArray));
    }

    /**
     * Converts the strings from a given String array to int numbers.
     *
     * @param numbers String type array, "list" of numbers.
     * @return Int array, built from the converted numbers.*/
    public static int[] stringsToInts(String[] numbers) {
        int[] numbersArray = new int[numbers.length];
        //For each of the strings in numbers , convert it to int and add it to the numbersArray.
        for (int i = 0; i < numbers.length; i++) {
            numbersArray[i] = Integer.parseInt(numbers[i]);
        }

        return  numbersArray;
    }

    /**
     * Returns the minimum number of a given ints array.
     *
     * @param numbers Int type array, array of numbers.
     * @return the minimum number of the given numbers array.*/
    public static int min(int[] numbers) {
        //Set the smallest number as the first one in the array.
        int smallest = numbers[0];
        //Run on every cell of the array.
        for (int i = 1;  i < numbers.length; i++) {
            //if the next number is smaller, set him as the current smallest number.
            if (numbers[i] < smallest) {
                smallest = numbers[i];
            }
        }
        return smallest;
    }

    /**
     * Returns the maximum number of a given ints array.
     *
     * @param numbers Int type array, array of numbers.
     * @return the maximum number of the given numbers array.*/
    public static int max(int[] numbers) {
        //set the smallest number as the first one in the array.
        int biggest = numbers[0];
        //run on every cell of the array.
        for (int i = 1;  i < numbers.length; i++) {
            //if the next number is bigger, set him as the current biggest number.
            if (numbers[i] > biggest) {
                biggest = numbers[i];
            }
        }
        return biggest;
    }

    /**
     * Returns the average of a given ints array.
     *
     * @param numbers Int type array, array of numbers.
     * @return the average of the given numbers array.*/
    public static float avg(int[] numbers) {
        float avg;
        //Set an initial value to the sum variable(sum of all numbers in the array).
        int sum = 0;

        //For each of the numbers in the array, add it to the current sum.
        for (int n : numbers) {
            sum = sum + n;
        }

        //Divide the sum with the length of the array (convert both to float).
        avg = (float) sum / (float) (numbers.length);

        return avg;
    }
}
