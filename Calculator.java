import java.util.Stack;
import java.io.*;

//Reverse Polish calculator
//currently supports addition, subtraction, multiplication, division, and exponents

public class Calculator
{
    // converts infix notation to postfix
    public static String[] toPostfix(String[] infix)
    {
        Stack<String> stackOfOperators = new Stack <String>();
        Stack<String> postfix = new Stack <String>();
        stackOfOperators.push("(");
        boolean valid2 = true;
        String prevOperator = "";

        for(int i = 0; i < infix.length; i++)
        {
            if(infix[i].equals("("))//left parenthesis is encountered
            {
                stackOfOperators.push("(");
                prevOperator = "(";
            }
            else if(infix[i].equals("+"))// + is encountered
            {
                if(prevOperator.equals("*") || (prevOperator.equals("/")) || (prevOperator.equals("^")))
                {
                    postfix.push(stackOfOperators.pop());
                }
                stackOfOperators.push("+");
                prevOperator = "+";
            }
            else if(infix[i].equals("-"))// - is encountered
            {
                if(prevOperator.equals("*") || (prevOperator.equals("/")) || (prevOperator.equals("^")))
                {
                    postfix.push(stackOfOperators.pop());
                }
                stackOfOperators.push("-");
                prevOperator = "-";
            }
            else if(infix[i].equals("*"))// * is encountered
            {
                if(prevOperator.equals("^"))
                {
                    postfix.push(stackOfOperators.pop());
                }
                stackOfOperators.push("*");
                prevOperator = "*";
            }
            else if(infix[i].equals("/"))// / is encountered
            {
                if(prevOperator.equals("^"))
                {
                    postfix.push(stackOfOperators.pop());
                }
                stackOfOperators.push("/");
                prevOperator = "/";
            }
            else if(infix[i].equals("^"))// ^ is encountered
            {
                stackOfOperators.push("^");
                prevOperator = "^";
            }
            else if(infix[i].equals(")"))//right parenthesis is encountered
            {
                int j = i - 1;
                while(!(stackOfOperators.peek().equals("(")))
                {
                    postfix.push(stackOfOperators.pop());
                }
                stackOfOperators.pop();
                prevOperator = ")";
            }
            else//operand is encountered
            {
                try
                {
                    Double doesItWork = Double.parseDouble(infix[i]);
                    postfix.push(infix[i]);
                }
                catch(NumberFormatException e)
                {
                    String[] invalid = new String[1];
                    invalid[0] = "bad";
                    return invalid;
                }
            }
        }

        
        Object[] src = postfix.toArray();
        //casts object array into string array
        String[] dest = new String[src.length];
        System.arraycopy(src, 0, dest, 0, src.length);
        
        return dest;
    }

    //adds a space between operands and operators
    public static String addSpaces(String s)
    {
        String s2 = "";

        for(int i = 0; i < s.length() - 1; i++)
        {
            if(0 <= Character.getNumericValue(s.charAt(i)) && Character.getNumericValue(s.charAt(i)) <= 9)
            {
                if(s.charAt(i + 1) == '(')
                {
                    s2 = s2 + s.charAt(i) + " * ";
                }
                else if(s.charAt(i + 1) == '.')
                {
                    s2 = s2 + s.charAt(i);
                }
                else if(0 <= Character.getNumericValue(s.charAt(i + 1)) && Character.getNumericValue(s.charAt(i + 1)) <= 9)
                {
                    s2 = s2 + s.charAt(i);
                }
                else
                {
                    s2 = s2 + s.charAt(i) + " ";
                }
            }
            else
            {
                s2 = s2 + s.charAt(i) + " ";
            }
        }
        s2 = s2 + s.charAt(s.length() - 1);
        return s2;
    }

    public static void main(String args[])
    {
        Stack <Double> numbers = new Stack <Double>();
        double operand1, operand2, result;
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int end = 1;
        boolean valid = true;
        //evalutes expressions from left to right
        while(end == 1)
        {
            String s = "";
            try
            {
                s = in.readLine();
            }
            catch(Exception e)
            {

            }

            s = s.replaceAll("\\s+", "");
            s = addSpaces(s);
            s = s + " )";
            String[] equation = s.split(" ", 0);

            String[] postfixEquation = toPostfix(equation);


            for(int i = 0; i < postfixEquation.length; i++)
            {
                if(postfixEquation[i].equals("+"))
                {
                    operand2 = numbers.pop();
                    operand1 = numbers.pop();
                    result = operand1 + operand2;
                    numbers.push(result);
                }
                else if(postfixEquation[i].equals("-"))
                {
                    operand2 = numbers.pop();
                    operand1 = numbers.pop();
                    result = operand1 - operand2;
                    numbers.push(result);
                }
                else if(postfixEquation[i].equals("*"))
                {
                    operand2 = numbers.pop();
                    operand1 = numbers.pop();
                    result = operand1 * operand2;
                    numbers.push(result);
                }
                else if(postfixEquation[i].equals("/"))
                {
                    operand2 = numbers.pop();
                    operand1 = numbers.pop();
                    result = operand1 / operand2;
                    numbers.push(result);
                }
                else if(postfixEquation[i].equals("^"))
                {
                    operand2 = numbers.pop();
                    operand1 = numbers.pop();
                    result = Math.pow(operand1, operand2);
                    numbers.push(result);
                }
                else if(postfixEquation[i].equals("q"))
                {
                    end = 0;
                    System.out.println("exiting program");
                }
                else
                {
                    try
                    {
                        numbers.push(Double.parseDouble(postfixEquation[i]));
                    }
                    catch(NumberFormatException e)
                    {
                        System.out.println("invalid argument in equation");
                        valid = false;
                        break;
                    }
                }
            }
            if(valid == false)
            {
                numbers.clear();
                valid = true;
            }
            else if(end == 1)
            {
                double memeNumber = numbers.pop();
                System.out.println(memeNumber);
                if(memeNumber == 69)
                {
                    System.out.println("nice");
                }
                else if(memeNumber == 420)
                {
                    System.out.println("weed");
                }
            }
        }
        
        try
        {
            in.close();
        }
        catch(Exception e)
        {
            
        }
    }
}