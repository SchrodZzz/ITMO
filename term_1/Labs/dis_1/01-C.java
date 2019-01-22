/*
 * Written by Andrei "SchrodZzz" Ivshin
 * Date: 11.2018
 */

import java.util.ArrayList;
import java.util.Scanner;

public class coding_a {

    public static void main(String args[]) {

        Scanner reader = new Scanner(System.in);
        int n = Integer.parseInt(reader.nextLine());

        boolean notZero = false;
        boolean notOne = false;
        boolean notSD = false;
        boolean notMonotonic = false;
        boolean notLinear = false;

        String currentValues;

        for (int i = 0; i < n; i++) {

            currentValues = reader.nextLine();
            String[] arguments = new String[(int) Math.pow(2, Integer.parseInt(currentValues.substring(0, 1)))];
            currentValues = currentValues.substring(2);

            for (int j = 0; j < arguments.length; j++) {
                arguments[j] = Integer.toBinaryString(j);
            }

            if (!notZero) {
                notZero = !zeroCheck(currentValues);
            }


            if (!notOne) {
                notOne = !oneCheck(currentValues);
            }


            if (!notSD) {
                notSD = !sdCheck(currentValues);
            }


            if (!notMonotonic) {
                notMonotonic = !monotonicCheck(currentValues, arguments);
            }


            if (!notLinear) {
                notLinear = !linearCheck(currentValues, arguments);
            }

            //System.out.println(isZero +" "+ isOne  +" "+ isSD +" "+ isMonotonic  +" "+ isLinear);
        }

        /*System.out.println(isZero +" "+ isOne  +" "+ isSD +" "+ isMonotonic  +" "+ isLinear
                +" "+ notZero +" "+ notOne  +" "+ notSD +" "+ notMonotonic  +" "+ notLinear);*/

        if (notLinear && notMonotonic && notOne && notSD && notZero) System.out.println("YES");
        else System.out.println("NO");


    }

    private static boolean zeroCheck(String value) {
        return value.charAt(0) == '0';
    }

    private static boolean oneCheck(String value) {
        return value.charAt(value.length() - 1) == '1';
    }

    private static boolean sdCheck(String value) {
        boolean ok = true;
        if (value.length() == 1) {
            return false;
        }
        for (int i = 0; i < value.length() / 2; i++) {
            if (value.charAt(i) == value.charAt(value.length() - 1 - i)) {
                ok = false;
                break;
            }
        }
        return ok;
    }

    private static boolean monotonicCheck(String value, String[] arguments) {

        if (arguments.length <= 1) {
            return true;
        }
        for (int i = arguments.length; i > 1; i /= 2) {
            for (int j = 0; j < arguments.length; j += i) {
                if (value.substring(j, j + i / 2).replaceAll("0", "").length()
                        > value.substring(j + i / 2, j + i).replaceAll("0", "").length()) {
                    return false;
                }
            }
        }

        return true;
    }

    private static boolean linearCheck(String value, String[] arguments) {

        boolean ok = true;

        ArrayList <ArrayList <Byte>> calculations = new ArrayList <>();
        ArrayList <Byte> tmp = new ArrayList <>();

        for (int i = 0; i < value.length(); i++) tmp.add(Byte.valueOf(String.valueOf(value.charAt(i))));
        calculations.add(tmp);

        for (int i = 0; i < value.length(); i++) {
            ArrayList <Byte> helper = new ArrayList <>();
            for (int j = 1; j < value.length() - i; j++) {
                helper.add((byte) ((calculations.get(i).get(j - 1) + calculations.get(i).get(j)) % 2));
            }
            calculations.add(helper);
        }

        for (int i = 0; i < value.length(); i++) {
            if (calculations.get(i).get(0) == 1 && arguments[i].replaceAll("0", "").length() > 1) {
                ok = false;
                break;
            }
        }
        return ok;
    }
}
