package search;

class BinarySearchMethods {

    // pre:For ∀i: a[i] >= a[i + 1] ∧ a[-1] > x > a[a.length]
    // -1 <= l <= r <= a[].length
    // l - index of the last number that exactly greater than key
    // r - index of the first number that equal or smaller than answer
    // key - wanted for search value
    // useRecursiveAlgorithm - boolean to choose recursive (true) or iterative (false)

    static int find(int[] a, int l, int r, int key, boolean useRecursiveAlgorithm) {
        // Algorithm realisation chooser
        if (useRecursiveAlgorithm) {
            // Pre defined before *.find
            return iterativeBinarySearch(a, l, r, key);
            // Post defined after *.find
        } else {
            // Pre defined before *.find
            return recursiveBinarySearch(a, l, r, key);
            // Post defined after *.find
        }
    }

    // Post: (a[i]' == a[i]) && (key' == key)
    // useRecursiveAlgorithm is true -> *.recursiveBinarySearch is called
    // OR useRecursiveAlgorithm is false -> *.iterativeBinarySearch is called
    // (a[].length == 0 || a[0] <= key) -> (ℝ = 0)
    // OR (a[].last > key) -> (ℝ = a.length)
    // OR (0 <= ℝ <= a[].length) -> (For ∀i 0 <= i < ℝ: a[i] > key) && (For ∀i ℝ <= i < a.length: a[i] <= key)

    // Pre defined before *.find
    private static int iterativeBinarySearch(int[] a, int l, int r, int key) {
        // While the search boundary contains more than one number we are trying to cut our border in half
        while (r - l > 1) {
            // So, we find middle element
            int m = (l + r) / 2;
            // l <= m <= r
            // Compare it with find key
            if (a[m] <= key) {
                // This means that all numbers to the right of the middle will be less or equal
                // For ∀ i >= m: a[i] <= key
                // (m - l) < (r - l)
                r = m;
                // r also first number that may be our answer, because it's minimal, that we find yet
            } else {
                // This means that all numbers to the left of the middle will be greater
                // For ∀ i <= m: a[i] > a[i]
                // (r - m) < (r - l)
                l = m;
                // l also last number that exactly greater than key, because it's maximum, that we find yet
            }
            // Search boundary was divided by two
        }

        // (r - l == 1) -> we have one element in our boundary, let's return this
        return r;
    }
    // Post defined after *.find

    // Pre defined before *.find
    private static int recursiveBinarySearch(int[] a, int l, int r, int key) {
        // If search boundary contains more than one number we are call recursive with smaller boundary
        // Answer will be correct and will return to calling method
        if (r - l > 1) {
            // Find middle element
            int m = (l + r) / 2;
            // l <= m <= r
            // Compare it with find key
            if (a[m] <= key) {
                // This means that all numbers to the right of the middle will be less or equal
                // For ∀ i >= m: a[i] <= key
                // Let's start our function by substituting middle instead of right
                // (m - l) < (r - l)
                return recursiveBinarySearch(a, l, m, key);
                // We returned founded value
            } else {
                // This means that all numbers to the left of the middle will be greater
                // For ∀ i <= m: a[i] > a[i]
                // Let's start our function by substituting middle instead of left
                // (r - m) < (r - l)
                return recursiveBinarySearch(a, m, r, key);
                // We returned founded value
            }
        }

        // (r - l == 1) -> we have one element in our boundary, let's return this
        return r;
    }
    // Post defined after *.find

}
