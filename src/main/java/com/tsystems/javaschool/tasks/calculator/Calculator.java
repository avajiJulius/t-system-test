package com.tsystems.javaschool.tasks.calculator;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Calculator {

    static final String REGEX = "(-|/|\\+|\\*)+(?![^(]*\\))";

    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */

    public String evaluate(String statement) {
        if (statement == null || statement.isEmpty() || !validate(statement))
            return null;

        double resultDouble = calculate(statement);
        if(Double.isNaN(resultDouble)) return null;

        int resultInt = (int) Math.round(resultDouble);
        if(resultDouble % resultInt == 0)
            return String.valueOf(resultInt);
        else
            return String.valueOf(resultDouble);
    }

    private List<Double> getValues(String statement) {
        String[] split = statement.split(REGEX);
        List<String> result = new ArrayList<>();
        for(String val : split) {
            result.add(val);
            if (val.startsWith("(") && val.endsWith(")")) {
                String bt = val.substring(1, val.length() - 1);
                result.remove(val);
                double calc = calculate(bt);
                result.add(String.valueOf(calc));
            }
        }

        return result.stream().map(s -> Double.parseDouble(s)).collect(Collectors.toList());
    }

    private List<Character> getOperators(String statement) {
        List<Character> result = new ArrayList<>();
        for (int i = 0; i < statement.length(); i++) {
            char c = statement.charAt(i);
            if(c == '(') {
                while(statement.charAt(i) != ')') i++;
            }
            else if (c == '+' || c == '-' || c == '*' || c == '/') result.add(c);
        }
        return result;
    }

    private double calculate(String stmt) {
        List<Double> vals = getValues(stmt);
        List<Character> ops = getOperators(stmt);
        double result = 0;
        int index = 0;
        while (!ops.isEmpty() && vals.size() != 1) {
            double a = 0;
            double b = 0;
            for (int i = 0; i < ops.size(); i++) {
                char op = ops.get(i);
                if (op == '*' | op == '/') {
                    a = vals.get(i);
                    b = vals.remove(i+1);
                    op = ops.remove(i);
                    result = (op == '*' ? a*b : a != 0 && b != 0 ? a/b : Double.NaN);
                    vals.set(i, result);
                    if (result == Double.NaN) return Double.NaN;
                    i--;
                }
            }
            if(!ops.isEmpty()) {
                char op = ops.get(index);
                a = vals.get(index);
                b = vals.remove(index + 1);
                result = (op == '+' ? a + b : a - b);
                vals.set(index, result);
                ops.remove(index);
            }
        }

        return result;
    }



    private boolean validate(String statement) {
        char[] chars = statement.toCharArray();
        if (!parenthesesValidate(chars) | !operationsValidate(chars)) return false;
        return true;
    }

    private boolean operationsValidate(char[] chars) {
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] == ',' || chars[i+1] == ',') return false;
            if (chars[i] == '+' || chars[i] == '-' || chars[i] == '*' || chars[i] == '/'|| chars[i] == '.') {
                if (chars[i] == chars[i+1]) return false;
            }
        }
        return true;
    }

    private boolean parenthesesValidate(char[] chars) {
        int count = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '(') count++;
            else if (chars[i] == ')') count--;
            if(count < 0) return false;
        }
        if (count > 0) return false;
        return true;
    }




}
