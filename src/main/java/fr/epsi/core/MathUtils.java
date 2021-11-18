package fr.epsi.core;

public final class MathUtils {
    private MathUtils() {
    }


    /**
     * Calculates the factorial of the number n recursively.
     *
     * @param n the number n.
     * @return the factorial.
     */
    public static int factorial(int n) {
        if (n == 0 || n == 1) {
            return 1;
        }

        return n * factorial(n - 1);
    }

    /**
     * Checks if the specified number is a prime number.
     *
     * @param n the number.
     * @return true if its a prime number, false otherwise.
     */
    public static boolean isPrimeNumber(int n) {
        if (n < 2) {
            return false;
        }
        if (n == 2) {
            return true;
        }

        boolean isPrime = true;
        int divider = 2;
        while (isPrime && divider <= n / 2) {
            isPrime = n % divider != 0;
            divider++;
        }
        return isPrime;
    }
}
