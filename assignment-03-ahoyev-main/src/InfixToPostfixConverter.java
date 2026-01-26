public class InfixToPostfixConverter {

    public static String convert(String infix) {

        MyLinkedStack<Character> stack = new MyLinkedStack<>();
        StringBuilder out = new StringBuilder();

        for (char ch : infix.toCharArray()) {

            if (ch == ' ') continue;

            // Operand → add directly
            if (Character.isLetterOrDigit(ch)) {
                out.append(ch);
            }
            // (
            else if (ch == '(') {
                stack.push(ch);
            }
            // )
            else if (ch == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    out.append(stack.pop());
                }
                if (stack.isEmpty()) return "ERROR"; 
                stack.pop(); 
            }
            // Operator
            else {
                while (!stack.isEmpty() &&
                       stack.peek() != '(' &&
                       precedence(stack.peek()) >= precedence(ch) &&
                       ch != '^') {

                    out.append(stack.pop());
                }
                stack.push(ch);
            }
        }

        // pop leftover operators
        while (!stack.isEmpty()) {
            char top = stack.pop();
            if (top == '(') return "ERROR";
            out.append(top);
        }

        return out.toString();
    }

    private static int precedence(char op) {
        if (op == '^') return 3;
        if (op == '*' || op == '/') return 2;
        if (op == '+' || op == '-') return 1;
        return 0;
    }
}