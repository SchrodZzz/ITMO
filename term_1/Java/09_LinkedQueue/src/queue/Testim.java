package queue;

import java.util.ArrayList;
import java.util.function.Function;
import java.util.function.Predicate;

public class Testim {

    private static Object[] array;
    private final static int ARRAY_SIZE = 5;

    public static void main(String[] args) {
        array = new Object[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = i * (i + 1);
        }

        System.out.println("Predicate: answer = all elements that equal 12");
        for (Object currentAnswer : predicateExample(curElement -> curElement.equals(12))) {
            System.out.print(currentAnswer + " ");
        }
        System.out.print("\n\n");

        System.out.println("Function: answer = currentObject + _kek");
        for (Object currentAnswer : functionExample(curElement -> curElement + "_kek")) {
            System.out.print(currentAnswer + " ");
        }
        System.out.print("\n\n");

        System.out.println("Function: answer = (currentObject == 12)");
        for (Object currentAnswer : functionExample(curElement -> curElement.equals(12))) {
            System.out.print(currentAnswer + " ");
        }
        System.out.print("\n\n");
    }

    //                                                       <input data type>
    private static ArrayList <Object> predicateExample(Predicate <Object> pr) {
        ArrayList <Object> answer = new ArrayList <>();
        for (Object currentObject : array) {
            if (pr.test(currentObject)) {
                answer.add(currentObject);
            }
        }
        return answer;
    }

    //                              as example I recommend you to change result data type to String and Boolean
    //                                                <input data type> <result data type>
    private static ArrayList <Object> functionExample(Function <Object, Object> fu) {
        ArrayList <Object> answer = new ArrayList <>();
        for (Object currentObject : array) {
            answer.add(fu.apply(currentObject));
        }
        return answer;
    }
}
