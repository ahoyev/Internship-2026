[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/BOctiNM_)
# Assignment-01
**Task 1: Time a section of code in Java.**

Before starting this assignment, you should learn how to time a section of code in Java. One approach is to use the class java.util.Date. A Date object contains the time at which it was constructed. This time is stored as a long integer equal to the number of milliseconds that have passed since 00:00:00.000 GMT on January 1, 1970. By subtracting the starting time in milliseconds from the ending time in milliseconds, you get the run time—in milliseconds—of a section of code.

For example, suppose that thisMethod() is the name of a method you would like to time. The following statements will determine the time that thisMethod() requires to execute (milliseconds):
```java
Date current = new Date();                 // Get current time
long startTime = current.getTime();
thisMethod();                              // Code to be timed
current = new Date();                      // Get current time
long stopTime = current.getTime();
long elapsedTime = stopTime − startTime;   // Milliseconds
```
An alternative approach with higher precision is to use System.currentTimeMillis() or System.nanoTime(). Suppose that thisMethod() is the name of a method you wish to time. The following statements will determine the time that thisMethod() requires to execute for even more precise timing (nanoseconds)
```java
long startTime = System.nanoTime();
thisMethod();
long endTime = System.nanoTime();
long elapsedTime = endTime - startTime;
```

**Task 2: Write a Java program that does the following:**

**2.1.** Uses the following arrays as fields:

   **toSum** An **array** of six integers. The array should be initialized with the following numbers: 10, 100, 1000, 10000, 100000, and 1000000

   **timeOfA** A **chain** of **long** integers to hold the time it takes algorithm A to finish the calculation with a value of **toSum**

   **timeOfB** A **chain** of **long** integers to hold the time it takes algorithm B to finish the calculation with a value of **toSum**

   **timeOfC** A **chain** of **long** integers to hold the time it takes algorithm C to finish the calculation with a value of **toSum**

**2.2.** Implements the three algorithms for computing the sum  1 + 2 + 3 + ...  + n for an integer n > 0

_Hint: implement each algorithm on its own method similar to In_Class_exercise_2. Something similar to this:_

```java
public static void loopB(int n) {
  int sum = 0;
  for (int i = 1; i <= n; i++)
    for (int j = 1; j <= n; j++)
      sum = sum + j;
}
```

```java
//Algorithm A
sum = 0
for i = 1 to n
  sum = sum + i

//Algorithm B
sum = 0
for i = 1 to n
{
  for j = 1 to i
    sum = sum + 1
}

//Algorithm C
sum = n * (n + 1) / 2
```
**2.3.** Determine and store the run time of the three algorithms implemented in **2.2** for each value n in the integer array **toSum** defined in **2.1**. 

Your program should relate the data in each chain. For example, the number in the first node of the **timeOfA** chain should be the run time of algorithm A when running on the number stored in element 0 of the **toSum** array. The runtime of algorithm B and C of that same value should also be stored in the first node of the chains **timeOfB** and **timeOfC**.

**2.4** Display results.

Your program should have a method that displays a table of the run times of each algorithm for each value of n in an array **toSum**. Below is an example how the table should look like.

| N        | Time A (ns)  | Time B (ns)    | Time C (ns) |
|----------|--------------|----------------|--------------|
| 10       | 750          | 1,208          | 417          |
| 100      | 667          | 28,833         | 125          |
| 1,000    | 5,667        | 1,415,750      | 375          |
| 10,000   | 54,667       | 20,033,375     | 1,458        |
| 100,000  | 811,541      | 102,258,916    | 1,416        |
| 1,000,000| 479,333      | 9,298,397,542  | 2,500        |

**Task 3: Submission**

**3.1** Make sure to push your work to the main branch and verify it is pushed to GitHub for submission.

**3.2** On Canvas, attach all of your source code files.

**3.3** On Canvas, attach a screenshot of your IDE showing the result of your code when you run it.

**GOOD LUCK and HAVE FUN!**
