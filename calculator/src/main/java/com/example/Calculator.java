package com.example;
import java.util.Stack;
import java.util.List;
import java.util.ArrayList;

public class Calculator {
    public static boolean is_digit(String a) {
        try {  
            Integer.parseInt(a);  
            return true;
        } catch(NumberFormatException e){  
            return false;  
        }
    }

    public static List<String> divide_to_parts(String a) throws Exception {
        // allowed symbols are */-+() and numbers

        String[] a_new = a.replace(" ", "").split("");
        String[] allowed_symbols = {"*", "/", "-", "+", "(", ")"};
        List<String> res = new ArrayList<String>();

        
        for (int i = 0; i < a_new.length; i++) {
            if (a_new[i].equals("-")) {
                if (i == a_new.length - 1) {
                    throw new Exception("Something wrong with formula1");
                }
                else {
                    if (i > 0) {
                        if (is_digit(a_new[i + 1]) & !is_digit(a_new[i - 1])) {
                            String tmp = "";
                            while (i < a_new.length - 1) {
                                if (is_digit(a_new[i + 1]) || a_new[i + 1].equals(".")) {
                                    tmp += a_new[i + 1];
                                    i = i + 1;
                                }
                                else {
                                    break;
                                }
                            }
                            res.add("-" + tmp);
                        }
                        else {
                            res.add("-");
                        }
                    }
                    else {
                        String tmp = "";
                        while (i < a_new.length - 1) {
                            if (is_digit(a_new[i + 1]) || a_new[i + 1].equals(".")) {
                                tmp += a_new[i + 1];
                                i = i + 1;
                            }
                            else {
                                break;
                            }
                        }
                        res.add("-" + tmp);
                    }
                }
            }
            else if (is_digit(a_new[i])) {
                String tmp = a_new[i];
                while (i < a_new.length - 1) {
                    if (is_digit(a_new[i + 1]) || a_new[i + 1].equals(".")) {
                        tmp += a_new[i + 1];
                        i = i + 1;
                    }
                    else {
                        break;
                    }
                }
                res.add(tmp);
            }
            else {
                boolean pushed = false;
                for (int j = 0; j < allowed_symbols.length; j++){
                    if (a_new[i].equals(allowed_symbols[j])) {
                        res.add(a_new[i]);
                        pushed = true;
                    }
                }
                
                if (!pushed) {
                    throw new Exception("Something wrong with formula2");
                }
            }
                
        }

        return res;
    }

    public static List<String> sorting_station(List<String> a) throws Exception {
        List<String> res = new ArrayList<String>();
        Stack<String> stack = new Stack<String>();

        for (int i = 0; i < a.size(); i++){
            if (is_digit(a.get(i).replace(".", ""))) {
                res.add(a.get(i));
            }    
            else if (a.get(i).equals("+")) {
                while (!stack.empty() && (stack.peek().equals("*") 
                        || stack.peek().equals("/") 
                        || stack.peek().equals("-"))) {
                    res.add(stack.pop());
                }
                stack.push(a.get(i));
            }
            else if (a.get(i).equals("-")) {
                while (!stack.empty() && (stack.peek().equals("*") 
                        || stack.peek().equals("/") 
                        || stack.peek().equals("+"))) {
                    res.add(stack.pop());
                }
                stack.push(a.get(i));
            }
            else if (a.get(i).equals("/")) {
                while (!stack.empty() && stack.peek().equals("*")) {
                    res.add(stack.pop());
                }

                stack.push(a.get(i));
            }
            else if (a.get(i).equals("*")) {
                while (!stack.empty() && stack.peek().equals("/")) {
                    res.add(stack.pop());
                }

                stack.push(a.get(i));
            }
            else if (a.get(i).equals("(")) {
                stack.push(a.get(i));
            }
            else if (a.get(i).equals(")")) {
                while (!stack.empty() && !stack.peek().equals("(")) {
                    res.add(stack.pop());
                }
                stack.pop();
            }
        }
        while (!stack.empty() && !stack.peek().equals("(")) {
            res.add(stack.pop());
        }

        if (!stack.empty()) {
            throw new Exception("Something wrong with formula3");
        }

        return res;
        
    }

    public static double calc(List<String> b) throws Exception {
        Stack<String> stack = new Stack<String>();
        double res = 0;
        String operation, tmp;
        for (int i = b.size() - 1; i >= 0; i--) {
            if (is_digit(b.get(i).replace(".", ""))) {
                if (!stack.empty() && is_digit(stack.peek().replace(".", ""))) {
                    tmp = stack.pop();
                    operation = stack.pop();

                    if (operation.equals("*")) {
                        res = Double.parseDouble(b.get(i)) * Double.parseDouble(tmp);
                    }
                    else if (operation.equals("/")) {
                        res = Double.parseDouble(b.get(i)) / Double.parseDouble(tmp);
                    }
                    else if (operation.equals("+")) {
                        res = Double.parseDouble(b.get(i)) + Double.parseDouble(tmp);
                    }
                    else if (operation.equals("-")) {
                        res = Double.parseDouble(b.get(i)) - Double.parseDouble(tmp);
                    }
                    
                    while (!stack.empty() && is_digit(stack.peek().replace(".", ""))) {
                        tmp = stack.pop();
                        operation = stack.pop();
                        if (operation.equals("*")) {
                            res = res * Double.parseDouble(tmp);
                        }
                        else if (operation.equals("/")) {
                            res = res / Double.parseDouble(tmp);
                        }
                        else if (operation.equals("+")) {
                            res = Double.parseDouble(tmp) + res;
                        }
                        else if (operation.equals("-")) {
                            res = res - Double.parseDouble(tmp);
                        }
                    }
                    stack.add(String.valueOf(res));
                }
                else {
                    stack.add(b.get(i));
                }
            }
            else {
                stack.add(b.get(i));
            }
        }

        if (stack.size() > 1) {
            throw new Exception("Something wrong with formula4");
        }

        return Double.parseDouble(stack.pop());
    }

    public static double calculate_expr(String expr) throws Exception{
        return calc(sorting_station(divide_to_parts(expr)));
    }
}