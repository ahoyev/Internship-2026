[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/6es5mHb0)
# Assignment-03: Infix to Postfix Conversion Using Stack

**Objective**

In this assignment, you will implement an algorithm that converts an infix expression (e.g., A + B * C) into a postfix expression (e.g., A B C * +) using a stack data structure. You will apply your knowledge of stack operations, operator precedence, and expression parsing.

**Background**

When evaluating arithmetic expressions, it’s often easier for a computer to work with postfix notation (also known as Reverse Polish Notation) rather than infix.
In postfix form, operators follow their operands, and parentheses are unnecessary.

You will implement a conversion algorithm that uses a stack to temporarily hold operators and parentheses while constructing the output expression.

**Algorithm Rules**

| Rule | Description | 
|:------------|:-------------|
| Operand| Append each operand to the end of the output expression.| 
| Operator ^ | Push ^ onto the stack (it has the highest precedence and is right-associative).| 
| Operators +, -, *, / | Pop operators from the stack, appending them to the output expression, until either the stack is empty or its top entry has a lower precedence than the newly encountered operator. Then push the new operator onto the stack.| 
| Open parenthesis (| Push ( onto the stack.| 
| Close parenthesis )| Pop operators from the stack and append them to the output expression until an open parenthesis ( is popped. Discard both parentheses.| 
| End of expression| Pop any remaining operators from the stack and append them to the output expression.| 


**Example**

Input (Infix):

A + B * (C - D)


Output (Postfix):

A B C D - * +

**Requirements**

Implement the algorithm in a Java class named InfixToPostfixConverter, using the MyLinkedStack class for stack operations.

The program should:

Prompt the user to enter the input and output filenames.

Reads one or more infix expressions from an input file (one expression per line).

Converts each infix expression to postfix using a stack.

Writes each postfix expression to an output file (line-aligned with input) and displays it on the console. 

Your program should handle invalid expressions gracefully (e.g., mismatched parentheses).

**Sample Run**

Enter infix expression: A+B*(C^D-E)^(F+G*H)-I

Postfix expression: ABCD^E-FGH*+^*+I-
