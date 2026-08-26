# ☕ Java — Complete M.Tech Reference Handbook
### Units I – V | Core Java → Web (Servlets/JSP) → Enterprise (RMI/EJB)

![Java](https://img.shields.io/badge/Java-SE%2FEE-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Units](https://img.shields.io/badge/Units-I%20to%20V-blue?style=for-the-badge)
![Hours](https://img.shields.io/badge/Total-40%20Hours-green?style=for-the-badge)

---

## 📚 Syllabus Map

| Unit | Hours | Focus |
|---|---|---|
| I | 8 | Core Java refresher — syntax, control flow, strings, arrays |
| II | 8 | Inheritance, Packages, Exceptions, Multithreading, Applets, JDBC |
| III | 8 | HTTP/Web & App Servers, Servlets |
| IV | 8 | JSP, DB connectivity, JavaBeans |
| V | 8 | MVC, RMI, EJB |

---
---

# UNIT-I: Review of Java Concepts

## 1.1 Features of Java

| Feature | Meaning |
|---|---|
| **Simple** | C++-like syntax minus pointers, operator overloading, multiple class inheritance |
| **Object-Oriented** | Everything (except primitives) is an object |
| **Platform Independent** | "Write Once, Run Anywhere" — compiles to bytecode run by any JVM |
| **Secure** | No explicit pointers, bytecode verifier, sandboxed execution (classloader + security manager) |
| **Robust** | Strong compile-time checking, automatic memory management, exception handling |
| **Multithreaded** | Built-in support for concurrent execution |
| **Architecture-Neutral** | Bytecode format doesn't depend on CPU/OS |
| **Portable** | No implementation-dependent data type sizes (`int` is always 32-bit) |
| **High Performance** | JIT (Just-In-Time) compiler translates bytecode to native code at runtime |
| **Distributed** | Networking libraries (`java.net`), RMI built in |
| **Dynamic** | Classes loaded on demand; supports reflection |

## 1.2 Object-Oriented Programming Overview

The four pillars — **Encapsulation, Inheritance, Polymorphism, Abstraction** — plus Java-specific notes:
- Java enforces OOP more strictly than C++ (no free functions, everything lives in a class).
- Single inheritance for classes; multiple inheritance only via interfaces.
- All methods are virtual (dynamically dispatched) by default.

## 1.3 Introduction to Java Technologies

```
JDK (Java Development Kit)  = JRE + development tools (javac, javadoc, jar, jdb, jshell)
JRE (Java Runtime Env.)     = JVM + core libraries — needed only to RUN Java programs
JVM (Java Virtual Machine)  = executes bytecode — platform-specific implementation

Java Editions:
├── Java SE (Standard Edition)   → core language, desktop apps   [this handbook's Unit I/II]
├── Java EE / Jakarta EE          → Servlets, JSP, EJB, enterprise APIs [Units III–V]
└── Java ME (Micro Edition)       → embedded/mobile devices (legacy)
```

**Compilation & Execution Flow:**
```
MyProgram.java --[javac]--> MyProgram.class (bytecode) --[java / JVM]--> Output
```

## 1.4 Writing a Simple Java Program

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
    }
}
```

```bash
javac HelloWorld.java     # compiles to HelloWorld.class
java HelloWorld           # runs it (JVM loads HelloWorld.class)
```

- `public class` name **must match** the filename.
- `main` is the JVM's entry point — must be exactly `public static void main(String[] args)`.
- `System.out` is a `PrintStream` object (standard output stream).

## 1.5 Data Types

```java
// ── 8 Primitive Types ──
byte    b  = 127;              // 1 byte,  -128 to 127
short   sh = 32000;             // 2 bytes
int     i  = 100000;            // 4 bytes  (default for integers)
long    l  = 10000000000L;      // 8 bytes  (note the 'L' suffix)
float   f  = 3.14f;             // 4 bytes  (note the 'f' suffix)
double  d  = 3.14159;           // 8 bytes  (default for decimals)
char    c  = 'A';               // 2 bytes, Unicode
boolean flag = true;            // 1 bit (JVM-dependent storage), true/false only

// ── Reference Types ──
String  s      = "Java";        // object
int[]   arr    = {1, 2, 3};     // array (object)
Object  obj    = new Object();  // any class

// Wrapper classes (autoboxing/unboxing bridges primitives ↔ objects)
Integer boxedInt = 10;    // autoboxing
int unboxed = boxedInt;   // unboxing
```

| Type | Size | Default | Range |
|---|---|---|---|
| byte | 1 byte | 0 | -128 to 127 |
| short | 2 bytes | 0 | -32,768 to 32,767 |
| int | 4 bytes | 0 | ~-2.1B to 2.1B |
| long | 8 bytes | 0L | ~-9.2×10¹⁸ to 9.2×10¹⁸ |
| float | 4 bytes | 0.0f | ~±3.4×10³⁸ (7 digits precision) |
| double | 8 bytes | 0.0d | ~±1.8×10³⁰⁸ (15 digits precision) |
| char | 2 bytes | '\u0000' | 0 to 65,535 (Unicode) |
| boolean | JVM-dependent | false | true / false |

## 1.6 Variables & Memory Concepts

```java
public class MemoryDemo {
    static int classField = 10;      // static/class variable — Method Area/Metaspace, ONE copy shared

    int instanceField = 20;          // instance variable — Heap, one copy PER OBJECT

    void demo() {
        int localVar = 30;           // local variable — Stack, exists only during method call
        // must be initialized before use — no default value like fields get
    }

    public static void main(String[] args) {
        MemoryDemo obj1 = new MemoryDemo();   // obj1 reference on Stack, object data on Heap
        MemoryDemo obj2 = new MemoryDemo();

        obj1.instanceField = 99;
        System.out.println(obj2.instanceField);  // 20 — separate copy, unaffected

        classField = 77;
        System.out.println(MemoryDemo.classField);  // 77 — shared by ALL instances
    }
}
```

```
JVM Runtime Memory Areas:
┌─────────────────────────────────────────────┐
│ Method Area / Metaspace  → class metadata, static fields, constant pool │
│ Heap                     → all objects & instance fields (GC managed)   │
│ Stack (per thread)       → local variables, method call frames          │
│ PC Register (per thread) → address of currently executing instruction   │
│ Native Method Stack      → for native (non-Java) method calls           │
└─────────────────────────────────────────────┘
```

## 1.7 Control Statements

```java
// if / else if / else
int marks = 75;
if (marks >= 90) System.out.println("A+");
else if (marks >= 75) System.out.println("A");
else System.out.println("B");

// switch (classic + modern arrow form, Java 14+)
int day = 3;
switch (day) {
    case 1: System.out.println("Mon"); break;
    case 2: System.out.println("Tue"); break;
    default: System.out.println("Other");
}

String dayName = switch (day) {
    case 1 -> "Mon";
    case 2 -> "Tue";
    default -> "Other";
};

// ternary
String result = (marks >= 40) ? "Pass" : "Fail";
```

## 1.8 Looping Constructs

```java
// for
for (int i = 0; i < 5; i++) System.out.println(i);

// while
int i = 0;
while (i < 5) { System.out.println(i); i++; }

// do-while — executes AT LEAST once
int j = 0;
do { System.out.println(j); j++; } while (j < 5);

// enhanced for (for-each) — see also Section 1.13
int[] nums = {10, 20, 30};
for (int n : nums) System.out.println(n);

// break / continue with labels
outer:
for (int a = 0; a < 3; a++) {
    for (int b = 0; b < 3; b++) {
        if (b == 1) continue outer;
        if (a == 2) break outer;
        System.out.println(a + "," + b);
    }
}
```

## 1.9 Method Call Stack & Activation Records

Every method call creates a new **stack frame (Activation Record)** on the calling thread's **call stack**.

```java
public class CallStackDemo {
    static int factorial(int n) {
        if (n <= 1) return 1;
        return n * factorial(n - 1);   // each call pushes a NEW activation record
    }

    public static void main(String[] args) {
        System.out.println(factorial(4));
    }
}
```

```
Call stack while computing factorial(4):
┌───────────────────────┐
│ factorial(1) → returns 1   │  ← top of stack (most recent call)
├───────────────────────┤
│ factorial(2) → n=2, waiting │
├───────────────────────┤
│ factorial(3) → n=3, waiting │
├───────────────────────┤
│ factorial(4) → n=4, waiting │
├───────────────────────┤
│ main()                       │  ← bottom of stack
└───────────────────────┘
```

Each **Activation Record** stores: the method's parameters, local variables, the return address (where to resume the caller), and the operand stack for intermediate computation (per the JVM spec).

> ⚠️ Excessive recursion depth → `StackOverflowError` (the stack has a fixed size per thread).

## 1.10 Argument Promotion and Casting

```java
// Widening (automatic / implicit) — smaller type → larger type, no data loss
byte b = 10;
int i = b;          // byte → int: automatic
long l = i;         // int → long: automatic
double d = l;        // long → double: automatic

// Narrowing (explicit cast required) — larger type → smaller type, possible data loss
double pi = 3.99;
int truncated = (int) pi;    // 3 — explicit cast needed, fraction is lost
long big = 130L;
byte overflowed = (byte) big;  // overflow — result wraps around (unpredictable without care)

// Argument promotion in expressions/method calls:
// byte, short, char are promoted to int automatically in arithmetic expressions
byte x = 5, y = 10;
int sum = x + y;   // byte+byte promotes to int automatically — CANNOT assign to byte directly
// byte wrongSum = x + y;  // ❌ compile error — needs an explicit cast: (byte)(x + y)

// Widening also applies when choosing an overloaded method (see 1.11)
void show(long n) { System.out.println("long: " + n); }
show(5);   // int 5 is WIDENED to long automatically — calls show(long)
```

## 1.11 Scope of Declaration and Method Overloading

```java
public class ScopeDemo {
    int field = 100;               // class-level scope — visible throughout the class

    void method1() {
        int local = 10;             // method-level scope — only visible inside method1()
        {
            int blockVar = 20;      // block scope — only visible inside these { }
            System.out.println(local + blockVar);
        }
        // blockVar is NOT accessible here — out of scope
    }

    // ── Method Overloading — same name, different parameter list ──
    void print(int i)               { System.out.println("int: " + i); }
    void print(double d)            { System.out.println("double: " + d); }
    void print(String s)            { System.out.println("String: " + s); }
    void print(int a, int b)        { System.out.println("two ints: " + (a + b)); }

    public static void main(String[] args) {
        ScopeDemo obj = new ScopeDemo();
        obj.print(5);
        obj.print(3.14);
        obj.print("Hello");
        obj.print(2, 3);
    }
}
```

**Overload resolution order (compile-time):** (1) exact match, (2) widening primitive conversion, (3) autoboxing/unboxing, (4) varargs — Java tries these in order and picks the most specific match.

## 1.12 String Handling

### String Constructors

```java
String s1 = "Java";                              // string literal — stored in the String Pool
String s2 = new String("Java");                  // new object on heap — NOT pooled
String s3 = new String(new char[]{'J','a','v','a'});
String s4 = new String(s1);                       // copy constructor style
byte[] bytes = {72, 105};
String s5 = new String(bytes);                    // "Hi" — from a byte array

System.out.println(s1 == s2);        // false — different objects
System.out.println(s1.equals(s2));   // true — same content
```

### String Operators

```java
String a = "Hello";
String b = "World";
String concatenated = a + " " + b;      // '+' is Java's ONLY overloaded operator, for String concatenation
concatenated += "!";                    // '+=' also works for concatenation
System.out.println(concatenated);        // Hello World!

// String is IMMUTABLE — every "modification" creates a NEW String object
String x = "Hi";
x.concat(" there");   // return value discarded — x is unchanged!
System.out.println(x);  // still "Hi"
```

### Character Extraction

```java
String str = "Java Programming";

char ch = str.charAt(0);                 // 'J'
char[] chars = str.toCharArray();        // full char array
str.getChars(0, 4, chars, 0);            // copies a range into a destination array
byte[] bytes = str.getBytes();           // byte array (platform default encoding)

System.out.println("Length: " + str.length());
System.out.println("Substring: " + str.substring(5));       // "Programming"
System.out.println("Substring: " + str.substring(0, 4));    // "Java"
```

### String Comparison

```java
String s1 = "apple";
String s2 = "Apple";

System.out.println(s1.equals(s2));            // false — case-sensitive
System.out.println(s1.equalsIgnoreCase(s2));  // true
System.out.println(s1.compareTo(s2));         // >0 (lowercase 'a' > uppercase 'A' in Unicode)
System.out.println(s1.compareToIgnoreCase(s2)); // 0
System.out.println(s1 == s2);                  // false (different content anyway)
System.out.println(s1.regionMatches(0, s2, 0, 3)); // false — 'a' vs 'A'
System.out.println(s1.startsWith("app"));      // true
System.out.println(s1.endsWith("le"));         // true
System.out.println(s1.contains("ppl"));        // true
```

### StringBuffer / StringBuilder (Mutable Strings)

```java
StringBuffer sb = new StringBuffer("Hello");    // thread-safe (synchronized methods)
StringBuilder sbd = new StringBuilder("Hello"); // NOT thread-safe, but FASTER — prefer this for single-threaded code

sb.append(" World");           // Hello World
sb.insert(5, ",");              // Hello, World
sb.reverse();                   // dlroW ,olleH
sb.reverse();                   // back to Hello, World
sb.replace(0, 5, "Hi");         // Hi, World
sb.delete(0, 3);                // World
sb.setCharAt(0, 'w');           // world
System.out.println(sb);
System.out.println("Capacity: " + sb.capacity());  // default 16 + initial string length
```

| String | StringBuffer | StringBuilder |
|---|---|---|
| Immutable | Mutable | Mutable |
| Thread-safe (immutability) | Thread-safe (synchronized) | NOT thread-safe |
| Slower for repeated modification | Slower (sync overhead) | **Fastest** for single-threaded use |

## 1.13 Arrays

### Declaring and Creating Arrays

```java
int[] arr1;                 // declaration (preferred style)
int arr2[];                 // declaration (C-style, also legal)

arr1 = new int[5];          // creation — default-initialized to 0
int[] arr3 = new int[]{1, 2, 3, 4, 5};   // creation + initialization
int[] arr4 = {1, 2, 3, 4, 5};            // shorthand — only at declaration
```

### Enhanced for Statement (for-each)

```java
int[] nums = {10, 20, 30, 40};
for (int n : nums) {
    System.out.println(n);
}
// Works with any array or Iterable (List, Set, etc.)
// Read-only view — modifying 'n' does NOT change the array element
```

### Passing Arrays to Methods

```java
public class ArrayPassDemo {
    static void modify(int[] arr) {
        arr[0] = 999;   // arrays are objects — passed by reference (the reference itself is copied)
    }

    public static void main(String[] args) {
        int[] data = {1, 2, 3};
        modify(data);
        System.out.println(data[0]);  // 999 — the original array WAS modified
    }
}
```

### Multidimensional Arrays

```java
int[][] matrix = new int[3][3];               // 3x3, all zeros
int[][] initialized = {{1,2,3}, {4,5,6}, {7,8,9}};

for (int[] row : initialized) {
    for (int val : row) System.out.print(val + " ");
    System.out.println();
}

// Jagged arrays — Java arrays-of-arrays can have DIFFERENT row lengths
int[][] jagged = new int[3][];
jagged[0] = new int[]{1};
jagged[1] = new int[]{1, 2};
jagged[2] = new int[]{1, 2, 3};
```

### Variable-Length Argument Lists (Varargs)

```java
public class VarargsDemo {
    static int sum(int... numbers) {    // '...' means "zero or more ints", accessible as an array
        int total = 0;
        for (int n : numbers) total += n;
        return total;
    }

    // Varargs must be the LAST parameter
    static void describe(String name, int... scores) {
        System.out.println(name + " has " + scores.length + " scores");
    }

    public static void main(String[] args) {
        System.out.println(sum());            // 0
        System.out.println(sum(1, 2, 3));      // 6
        System.out.println(sum(1, 2, 3, 4, 5)); // 15
        describe("Rohit", 90, 85, 78);
    }
}
```

### Using Command-line Arguments

```java
public class CmdArgsDemo {
    public static void main(String[] args) {
        System.out.println("Number of args: " + args.length);
        for (String arg : args) {
            System.out.println("Arg: " + arg);
        }
    }
}
```

```bash
javac CmdArgsDemo.java
java CmdArgsDemo hello world 123
# Output:
# Number of args: 3
# Arg: hello
# Arg: world
# Arg: 123
```

---
---

# UNIT-II

## 2.1 Inheritance: Extending Classes

```java
class Vehicle {
    protected String brand;
    protected int speed;

    Vehicle(String brand, int speed) { this.brand = brand; this.speed = speed; }

    void display() { System.out.println(brand + " @ " + speed + " km/h"); }
}

class Car extends Vehicle {           // 'extends' — single inheritance only
    int doors;

    Car(String brand, int speed, int doors) {
        super(brand, speed);          // must be FIRST statement — calls parent constructor
        this.doors = doors;
    }

    @Override
    void display() {
        super.display();              // call parent's version explicitly
        System.out.println("Doors: " + doors);
    }
}

public class InheritanceDemo {
    public static void main(String[] args) {
        Car c = new Car("Tata", 180, 4);
        c.display();

        Vehicle v = c;                 // upcasting — always safe
        v.display();                   // still calls Car's overridden display() — dynamic dispatch

        // Downcasting — needs explicit cast + instanceof check
        if (v instanceof Car car2) {
            System.out.println("Downcast successful, doors: " + car2.doors);
        }
    }
}
```

Key rules: single inheritance for classes, `super` for parent constructor/method access, every class implicitly extends `Object` if no other superclass is named, `final` class/method prevents further extension/overriding.

## 2.2 Packages and Interfaces

### Defining a Package

```java
// File: com/rohit/utils/MathHelper.java
package com.rohit.utils;

public class MathHelper {
    public static int square(int x) { return x * x; }
}
```

Directory structure **must mirror** the package name: `com/rohit/utils/MathHelper.java`.

### Understanding CLASSPATH

```
CLASSPATH tells the JVM/compiler where to look for .class files and libraries.

Set via:
- Environment variable:  export CLASSPATH=/path/to/classes:/path/to/lib.jar
- Command-line flag:     java -cp /path/to/classes MainClass
- Default: current directory (.) if CLASSPATH is unset

Example:
javac -d build src/com/rohit/utils/MathHelper.java   # -d places compiled classes per package structure
java -cp build com.rohit.utils.Main
```

### Access Protection

| Modifier | Same Class | Same Package | Subclass (diff. package) | Other Package |
|---|---|---|---|---|
| `private` | ✅ | ❌ | ❌ | ❌ |
| *(default)* | ✅ | ✅ | ❌ | ❌ |
| `protected` | ✅ | ✅ | ✅ | ❌ |
| `public` | ✅ | ✅ | ✅ | ✅ |

### Importing Packages

```java
import java.util.List;             // import a single class
import java.util.*;                // import all classes in a package (not sub-packages)
import static java.lang.Math.PI;   // static import — use PI directly instead of Math.PI
import com.rohit.utils.MathHelper;

public class ImportDemo {
    public static void main(String[] args) {
        System.out.println(MathHelper.square(5));
        System.out.println(PI);
        List<Integer> nums = new ArrayList<>();
    }
}
```

### Creating Your Own Packages — Full Example

```java
// File: shapes/Shape.java
package shapes;

public interface Shape {
    double area();
}

// File: shapes/Circle.java
package shapes;

public class Circle implements Shape {
    private double radius;
    public Circle(double radius) { this.radius = radius; }
    public double area() { return Math.PI * radius * radius; }
}

// File: Main.java (default/no package, or a different package)
import shapes.Shape;
import shapes.Circle;

public class Main {
    public static void main(String[] args) {
        Shape s = new Circle(5);
        System.out.println("Area: " + s.area());
    }
}
```

```bash
javac -d . shapes/Shape.java shapes/Circle.java Main.java
java Main
```

## 2.3 Exception Handling

### Introduction & Keywords

```java
try {
    // risky code
} catch (SpecificException e) {
    // handle it
} catch (Exception e) {
    // catch-all fallback
} finally {
    // ALWAYS runs — cleanup code (closing files, connections, etc.)
}

throw new IllegalArgumentException("bad input");   // manually raise an exception

void riskyMethod() throws java.io.IOException {     // declare a CHECKED exception the method may throw
    // ...
}
```

### Overview & When to Use

```java
public class ExceptionDemo {
    static int divide(int a, int b) {
        if (b == 0) throw new ArithmeticException("Cannot divide by zero");
        return a / b;
    }

    public static void main(String[] args) {
        try {
            System.out.println(divide(10, 2));
            System.out.println(divide(5, 0));   // throws here
        } catch (ArithmeticException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            System.out.println("Cleanup always runs");
        }

        // Custom exception
        try {
            validateAge(15);
        } catch (InvalidAgeException e) {
            System.out.println("Custom exception: " + e.getMessage());
        }
    }

    static void validateAge(int age) throws InvalidAgeException {
        if (age < 18) throw new InvalidAgeException("Must be 18+");
    }
}

class InvalidAgeException extends Exception {   // checked — extends Exception, not RuntimeException
    public InvalidAgeException(String msg) { super(msg); }
}
```

**When to use exceptions:** for *exceptional*, unexpected conditions (file not found, network failure, invalid input) — NOT for normal control flow (don't use exceptions to break out of a loop, for example). Use **checked exceptions** for recoverable conditions the caller must handle; use **unchecked (`RuntimeException`)** for programming errors.

## 2.4 Multithreading

### What are Threads?

A **thread** is the smallest unit of CPU execution — a program can run multiple threads concurrently, sharing the same process memory (unlike separate processes, which don't share memory).

### The Java Thread Model

```java
// Method 1: extending Thread
class MyThread extends Thread {
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(getName() + ": " + i);
        }
    }
}

// Method 2: implementing Runnable (preferred — allows extending another class too)
class MyRunnable implements Runnable {
    public void run() {
        for (int i = 1; i <= 3; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

public class ThreadDemo {
    public static void main(String[] args) throws InterruptedException {
        MyThread t1 = new MyThread();
        t1.start();                        // NEVER call run() directly — start() creates a new OS thread

        Thread t2 = new Thread(new MyRunnable());
        t2.start();

        // Lambda (Runnable is a functional interface)
        Thread t3 = new Thread(() -> System.out.println("Lambda thread running"));
        t3.start();

        t1.join();   // wait for t1 to finish before continuing
        System.out.println("All threads started");
    }
}
```

### Thread Priorities

```java
Thread t = new Thread(() -> System.out.println("Running"));
t.setPriority(Thread.MAX_PRIORITY);   // 10
t.setPriority(Thread.MIN_PRIORITY);   // 1
t.setPriority(Thread.NORM_PRIORITY);  // 5 (default)
// Priority is a HINT to the scheduler — not a guarantee, behavior is JVM/OS dependent
```

### Thread Life Cycle

```
NEW → RUNNABLE → (BLOCKED / WAITING / TIMED_WAITING) → TERMINATED
                          ↑___________________________|
                     (waiting for lock, join(), sleep(), etc.)
```

| State | Meaning |
|---|---|
| `NEW` | Thread object created, `start()` not yet called |
| `RUNNABLE` | Executing or ready to execute (scheduler decides) |
| `BLOCKED` | Waiting to acquire a monitor lock (`synchronized`) |
| `WAITING` | Waiting indefinitely for another thread (`wait()`, `join()`) |
| `TIMED_WAITING` | Waiting for a specified time (`sleep(ms)`, `join(ms)`) |
| `TERMINATED` | `run()` method has completed |

### Thread Synchronization

```java
class Counter {
    private int count = 0;

    // synchronized method — only ONE thread can execute this at a time per object instance
    public synchronized void increment() {
        count++;
    }

    public int getCount() { return count; }
}

public class SyncDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        t1.start(); t2.start();
        t1.join(); t2.join();

        System.out.println("Final count: " + counter.getCount());  // 2000 — safe due to synchronization
        // Without 'synchronized', race conditions could give an inconsistent/lower count!
    }
}

// synchronized block — locks only the critical section, not the whole method
void transfer(Account from, Account to, double amount) {
    synchronized (from) {
        synchronized (to) {
            from.debit(amount);
            to.credit(amount);
        }
    }
}
```

## 2.5 Applets

> ⚠️ **Note:** Applets are **deprecated** (Java 9) and **removed** (Java 17+) — browsers dropped Java plugin support years ago. Covered here for syllabus/legacy-exam purposes only; do not use for new projects.

### Applet Basics & Architecture

```java
import java.applet.Applet;
import java.awt.Graphics;

public class HelloApplet extends Applet {
    public void paint(Graphics g) {
        g.drawString("Hello, Applet World!", 20, 20);
    }
}
```

```html
<!-- HelloApplet.html -->
<applet code="HelloApplet.class" width="300" height="100"></applet>
```

**Architecture:** an applet runs inside a **browser's JVM sandbox** — restricted from local file access, arbitrary network connections, and running native code, unlike standalone applications.

### Applet Life Cycle Methods

```
init()    → called ONCE, when the applet is first loaded (setup)
start()   → called after init(), and every time the applet's page is revisited
paint()   → called whenever the applet needs to redraw itself
stop()    → called when the user navigates away from the page
destroy() → called ONCE, when the browser shuts down / applet is unloaded
```

```java
import java.applet.Applet;
import java.awt.Graphics;

public class LifecycleApplet extends Applet {
    public void init()    { System.out.println("init: setup resources"); }
    public void start()   { System.out.println("start: begin execution"); }
    public void paint(Graphics g) { g.drawString("Running...", 10, 10); }
    public void stop()    { System.out.println("stop: pause execution"); }
    public void destroy() { System.out.println("destroy: release resources"); }
}
```

## 2.6 Database Connectivity: JDBC

### The Design of JDBC

```
Java Application
      │
      ▼
JDBC API (java.sql, javax.sql)
      │
      ▼
JDBC Driver Manager  →  selects the right driver
      │
      ▼
JDBC Driver (vendor-specific: MySQL Connector/J, OJDBC, etc.)
      │
      ▼
Database (MySQL, Oracle, PostgreSQL, ...)
```

Four Driver Types (historically): Type 1 (JDBC-ODBC bridge, removed), Type 2 (native-API), Type 3 (network protocol), **Type 4 (pure Java, thin driver — the modern standard, e.g. `mysql-connector-j`)**.

### Typical Uses of JDBC

```java
import java.sql.*;

public class JdbcDemo {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/schooldb";
        String user = "root";
        String password = "password";

        // try-with-resources — auto-closes Connection, Statement, ResultSet
        try (Connection conn = DriverManager.getConnection(url, user, password);
             Statement stmt = conn.createStatement()) {

            // SELECT
            ResultSet rs = stmt.executeQuery("SELECT id, name, cgpa FROM students");
            while (rs.next()) {
                System.out.println(rs.getInt("id") + " | " + rs.getString("name") + " | " + rs.getFloat("cgpa"));
            }

            // INSERT / UPDATE / DELETE via PreparedStatement (prevents SQL injection)
            try (PreparedStatement ps = conn.prepareStatement(
                    "INSERT INTO students (name, cgpa) VALUES (?, ?)")) {
                ps.setString(1, "Rohit");
                ps.setFloat(2, 9.2f);
                ps.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
```

Common uses: querying data for reports, inserting/updating transactional records, connection pooling (via `DataSource`), calling stored procedures (`CallableStatement`), batch updates.

---
---

# UNIT-III: HTTP, Web Servers & Servlets

## 3.1 Introduction to HTTP, Web Servers, and Application Servers

```
HTTP (HyperText Transfer Protocol) — stateless request/response protocol
Client (Browser) --request (GET/POST/PUT/DELETE)--> Server --response (status + body)--> Client

Web Server        → serves STATIC content (HTML, CSS, JS, images) — e.g. Apache HTTPD, Nginx
Application Server → runs DYNAMIC Java code (Servlets, JSP, EJB) — e.g. Apache Tomcat, WildFly, GlassFish
                     (Tomcat is technically a "Servlet/JSP container" — a lightweight app server)
```

### Installation of Application Servers (Apache Tomcat example)

```bash
# 1. Download Tomcat from tomcat.apache.org
# 2. Extract it
tar -xzf apache-tomcat-10.x.tar.gz

# 3. Set JAVA_HOME (Tomcat needs a JDK)
export JAVA_HOME=/path/to/jdk

# 4. Start the server
cd apache-tomcat-10.x/bin
./startup.sh          # Linux/Mac
startup.bat           # Windows

# 5. Access it
# http://localhost:8080

# 6. Stop the server
./shutdown.sh
```

### Config Files

```
apache-tomcat/
├── conf/
│   ├── server.xml       → ports, connectors, hosts configuration
│   ├── web.xml           → DEFAULT deployment descriptor (applies to ALL apps)
│   ├── context.xml       → per-application context settings
│   └── tomcat-users.xml  → manager/admin credentials
├── webapps/              → deployed applications go here (as .war files or exploded folders)
├── lib/                  → shared JAR libraries
└── logs/                 → server & application logs
```

### web.xml (Deployment Descriptor)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns="https://jakarta.ee/xml/ns/jakartaee"
         version="5.0">

    <servlet>
        <servlet-name>HelloServlet</servlet-name>
        <servlet-class>com.rohit.HelloServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>HelloServlet</servlet-name>
        <url-pattern>/hello</url-pattern>
    </servlet-mapping>

    <welcome-file-list>
        <welcome-file>index.html</welcome-file>
    </welcome-file-list>

    <session-config>
        <session-timeout>30</session-timeout> <!-- minutes -->
    </session-config>
</web-app>
```

## 3.2 Java Servlets

### What is a Servlet?

A **Servlet** is a Java class that runs inside a **Servlet container** (Tomcat) and handles HTTP requests/responses dynamically — the Java EE answer to CGI scripts, but faster (loaded once, handles many requests via threads).

### Servlet Development Process

```
1. Write a class extending HttpServlet (or implementing Servlet)
2. Override doGet()/doPost() etc.
3. Compile against the Servlet API JAR (provided by the container)
4. Package into a WAR (Web ARchive) with correct directory structure
5. Configure via web.xml OR @WebServlet annotation
6. Deploy the .war to the container's webapps/ folder
7. Container loads, initializes, and routes requests to it
```

### Deployment Descriptor (web.xml) vs Annotations

```java
// Modern approach — annotation-based (Servlet 3.0+), no web.xml entry needed
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;

@WebServlet("/hello")
public class HelloServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h1>Hello from Servlet!</h1>");
    }
}
```

### Generic Servlet vs HTTP Servlet

```java
import jakarta.servlet.*;
import java.io.*;

// GenericServlet — protocol-independent (rarely used directly; you override service())
public class MyGenericServlet extends GenericServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws IOException {
        res.setContentType("text/plain");
        res.getWriter().println("Handled by GenericServlet");
    }
}

// HttpServlet — HTTP-specific (the one you use 99% of the time)
// provides doGet(), doPost(), doPut(), doDelete(), doHead(), etc.
```

### Lifecycle of a Servlet

```
init()     → called ONCE when the servlet is first loaded (config setup, resource acquisition)
service()  → called for EVERY request; internally dispatches to doGet()/doPost()/etc.
             based on the HTTP method
destroy()  → called ONCE when the container unloads/shuts down the servlet
```

```java
import jakarta.servlet.http.*;
import jakarta.servlet.*;
import java.io.*;

public class LifecycleServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("init(): Servlet loaded");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.getWriter().println("service()/doGet(): handling request");
    }

    @Override
    public void destroy() {
        System.out.println("destroy(): Servlet unloaded");
    }
}
```

## 3.3 Servlet Packages, Classes, Interfaces & Methods

```
jakarta.servlet            → Servlet, ServletConfig, ServletContext, ServletRequest, ServletResponse (protocol-agnostic)
jakarta.servlet.http       → HttpServlet, HttpServletRequest, HttpServletResponse, HttpSession, Cookie

Key interfaces:
- Servlet          → init(), service(), destroy(), getServletConfig(), getServletInfo()
- ServletRequest   → getParameter(), getAttribute(), getInputStream()
- ServletResponse  → getWriter(), setContentType(), getOutputStream()

Key classes:
- GenericServlet   → abstract, protocol-independent base implementing Servlet
- HttpServlet      → abstract, extends GenericServlet, HTTP-specific (doGet/doPost/...)
- HttpServletRequest/Response → HTTP-specific request/response
- HttpSession      → per-user server-side session storage
- Cookie           → small client-side key-value data
```

## 3.4 Handling Forms with a Servlet

```html
<!-- form.html -->
<form action="register" method="post">
    Name: <input type="text" name="username">
    Email: <input type="email" name="email">
    <input type="submit" value="Register">
</form>
```

```java
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String username = req.getParameter("username");   // reads form field by 'name' attribute
        String email = req.getParameter("email");

        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h2>Registered: " + username + " (" + email + ")</h2>");
    }
}
```

## 3.5 Session Handling — Various Methods

```java
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;

@WebServlet("/session-demo")
public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // Method 1: HttpSession (most common — server-side storage, session ID via cookie/URL)
        HttpSession session = req.getSession();   // creates one if it doesn't exist
        Integer visits = (Integer) session.getAttribute("visits");
        visits = (visits == null) ? 1 : visits + 1;
        session.setAttribute("visits", visits);

        // Method 2: Cookies (client-side storage)
        Cookie cookie = new Cookie("lastVisit", String.valueOf(System.currentTimeMillis()));
        cookie.setMaxAge(60 * 60 * 24);   // 1 day
        resp.addCookie(cookie);

        // Method 3: URL Rewriting (fallback when cookies are disabled)
        String encodedUrl = resp.encodeURL("/session-demo");

        // Method 4: Hidden form fields — pass session data as invisible <input type="hidden">

        resp.getWriter().println("Visits: " + visits + " | Encoded URL: " + encodedUrl);
    }
}
```

## 3.6 Elements of Deployment Descriptors

```xml
<web-app>
    <servlet>...</servlet>                  <!-- register a servlet class -->
    <servlet-mapping>...</servlet-mapping>  <!-- map a URL pattern to a servlet -->
    <filter>...</filter>                    <!-- register a Filter (pre/post-processing) -->
    <filter-mapping>...</filter-mapping>
    <listener>...</listener>                <!-- lifecycle event listeners -->
    <context-param>...</context-param>      <!-- application-wide init parameters -->
    <welcome-file-list>...</welcome-file-list>  <!-- default page (index.html, etc.) -->
    <error-page>...</error-page>            <!-- custom error pages per status code/exception -->
    <session-config>...</session-config>    <!-- session timeout settings -->
    <security-constraint>...</security-constraint>  <!-- URL-based access control -->
</web-app>
```

---
---

# UNIT-IV: JSP & Database-Backed Web Apps

## 4.1 JSP Basics

**JSP (JavaServer Pages)** = HTML + embedded Java, compiled into a Servlet **behind the scenes** by the container the first time it's requested.

### JSP Lifecycle

```
1. Translation → JSP file (.jsp) is translated into a Servlet's Java source (_jsp.java)
2. Compilation  → that Java source is compiled into a .class file
3. jspInit()    → called once, like a servlet's init()
4. _jspService()→ called for every request (auto-generated, handles doGet/doPost)
5. jspDestroy() → called once, like a servlet's destroy()
```

### Directives

```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.*, java.sql.*" %>
<%@ include file="header.jsp" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
```

### Scripting Elements

```jsp
<%-- comment (not sent to client) --%>

<% // scriptlet — raw Java code
   int x = 10;
   int y = 20;
%>

<%= x + y %>   <!-- expression — printed directly into the HTML output -->

<%! // declaration — declares a field/method at the servlet class level
    int counter = 0;
    int increment() { return ++counter; }
%>
```

### Standard Actions

```jsp
<jsp:useBean id="student" class="com.rohit.Student" scope="session"/>
<jsp:setProperty name="student" property="name" value="Rohit"/>
<jsp:getProperty name="student" property="name"/>
<jsp:include page="footer.jsp"/>
<jsp:forward page="nextPage.jsp"/>
```

### Implicit Objects

| Object | Type | Purpose |
|---|---|---|
| `request` | `HttpServletRequest` | Incoming request data |
| `response` | `HttpServletResponse` | Outgoing response |
| `session` | `HttpSession` | Per-user session |
| `application` | `ServletContext` | App-wide shared data |
| `out` | `JspWriter` | Write output to the page |
| `config` | `ServletConfig` | Servlet init parameters |
| `pageContext` | `PageContext` | Access to all other scopes |
| `page` | `Object` (this) | The current JSP instance |
| `exception` | `Throwable` | Only in error pages (`isErrorPage="true"`) |

```jsp
<html><body>
<%
    String name = request.getParameter("name");
    session.setAttribute("user", name);
%>
<h2>Hello, <%= name %>! Session ID: <%= session.getId() %></h2>
</body></html>
```

## 4.2 JSP/Servlet Database Connectivity — Oracle, MS-SQL, MySQL

```java
// java.sql package — the standard JDBC API, same regardless of DB vendor
import java.sql.*;

public class DbConnectExamples {
    // MySQL
    static Connection connectMySQL() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/mydb", "root", "password");
    }

    // Oracle
    static Connection connectOracle() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:oracle:thin:@localhost:1521:XE", "system", "password");
    }

    // MS-SQL Server
    static Connection connectMSSQL() throws SQLException {
        return DriverManager.getConnection(
            "jdbc:sqlserver://localhost:1433;databaseName=mydb", "sa", "password");
    }
}
```

Each driver JAR (e.g. `mysql-connector-j-x.x.x.jar`, `ojdbc11.jar`, `mssql-jdbc-x.x.jar`) must be on the classpath / in `WEB-INF/lib` for a web app.

### Querying, Inserting, Deleting, Modifying Records

```java
try (Connection conn = DriverManager.getConnection(url, user, pass)) {

    // SELECT
    try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM students WHERE cgpa > ?")) {
        ps.setFloat(1, 8.0f);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) System.out.println(rs.getString("name"));
    }

    // INSERT
    try (PreparedStatement ps = conn.prepareStatement(
            "INSERT INTO students (name, cgpa) VALUES (?, ?)")) {
        ps.setString(1, "Anjali");
        ps.setFloat(2, 9.1f);
        ps.executeUpdate();
    }

    // UPDATE
    try (PreparedStatement ps = conn.prepareStatement(
            "UPDATE students SET cgpa = ? WHERE name = ?")) {
        ps.setFloat(1, 9.5f);
        ps.setString(2, "Anjali");
        ps.executeUpdate();
    }

    // DELETE
    try (PreparedStatement ps = conn.prepareStatement("DELETE FROM students WHERE name = ?")) {
        ps.setString(1, "Anjali");
        ps.executeUpdate();
    }
}
```

### Types of Statement

| Type | Use Case |
|---|---|
| `Statement` | Static SQL, no parameters — vulnerable to SQL injection, avoid for user input |
| `PreparedStatement` | Precompiled, parameterized (`?` placeholders) — faster on repeat, injection-safe |
| `CallableStatement` | Calls stored procedures (`{call procName(?, ?)}`) |

## 4.3 Separating Business Logic and Presentation Logic

```
Bad (logic mixed into JSP):
   <% Connection conn = DriverManager.getConnection(...); ResultSet rs = ...; %>
   <table><% while(rs.next()) { %><tr>...</tr><% } %></table>

Good (JavaBean/DAO handles logic, JSP only PRESENTS):
   Servlet/Controller → calls a StudentDAO (business logic) → stores result as a request attribute
   JSP                → simply loops over the attribute and renders HTML (no direct DB code)
```

### Building and Using a JavaBean

A **JavaBean** is a plain Java class following conventions: **no-arg constructor**, **private fields**, **public getters/setters**, and (ideally) `Serializable`.

```java
// StudentBean.java
package com.rohit.beans;

import java.io.Serializable;

public class StudentBean implements Serializable {
    private String name;
    private float cgpa;

    public StudentBean() { }   // required no-arg constructor

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public float getCgpa() { return cgpa; }
    public void setCgpa(float cgpa) { this.cgpa = cgpa; }
}
```

```jsp
<jsp:useBean id="student" class="com.rohit.beans.StudentBean" scope="page"/>
<jsp:setProperty name="student" property="*"/>  <!-- auto-binds matching request params -->

<p>Name: <jsp:getProperty name="student" property="name"/></p>
<p>CGPA: <jsp:getProperty name="student" property="cgpa"/></p>
```

## 4.4 Session Handling in JSP

Identical mechanisms to Servlets (Section 3.5), accessed via the implicit `session` object:

```jsp
<%
    session.setAttribute("username", request.getParameter("username"));
    session.setMaxInactiveInterval(30 * 60);  // 30 minutes, in seconds
%>
Welcome back, <%= session.getAttribute("username") %>!
```

## 4.5 Types of Errors and Exception Handling (JSP)

```jsp
<%-- errorPage.jsp --%>
<%@ page isErrorPage="true" %>
<h2>An error occurred: <%= exception.getMessage() %></h2>

<%-- source.jsp — declares which page handles ITS errors --%>
<%@ page errorPage="errorPage.jsp" %>
<%
    int x = 10 / 0;   // throws ArithmeticException — forwarded to errorPage.jsp
%>
```

Error categories: **Translation-time errors** (bad JSP syntax), **Compilation-time errors** (bad embedded Java), **Runtime exceptions** (`NullPointerException`, `SQLException`, etc. — handled via `errorPage`/`isErrorPage`, or `<error-page>` in `web.xml` for status-code-based handling like 404/500).

---
---

# UNIT-V: MVC, RMI & EJB

## 5.1 MVC Architecture

```
┌─────────────┐      ┌──────────────┐      ┌─────────────┐
│    Model    │◄────►│  Controller   │◄────►│    View     │
│ (data + biz │      │  (Servlet)    │      │   (JSP)     │
│   logic)    │      │  handles req, │      │  renders    │
│  JavaBeans/ │      │  calls Model, │      │  HTML from  │
│  DAO layer  │      │  picks View   │      │  Model data │
└─────────────┘      └──────────────┘      └─────────────┘
        ▲                                          │
        └──────────── User interacts ◄─────────────┘
```

- **Model** — JavaBeans / DAO classes representing data & business rules.
- **View** — JSP pages, purely presentation (Section 4.3).
- **Controller** — a Servlet that receives requests, invokes the Model, then forwards to the appropriate View (`RequestDispatcher.forward()`).

```java
@WebServlet("/students")
public class StudentController extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        StudentDAO dao = new StudentDAO();                     // Model
        List<StudentBean> students = dao.getAllStudents();
        req.setAttribute("students", students);
        req.getRequestDispatcher("studentList.jsp").forward(req, resp);  // View
    }
}
```

**Benefits:** separation of concerns, easier testing, parallel development (frontend/backend), reusable Model across multiple Views.

## 5.2 Introduction to Remote Method Invocation (RMI)

RMI lets a Java object on one JVM **invoke methods on an object running in a different JVM** (potentially on a different machine) — as if it were a local call.

```java
// 1. Remote interface
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GreetingService extends Remote {
    String greet(String name) throws RemoteException;
}

// 2. Server-side implementation
import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class GreetingServiceImpl extends UnicastRemoteObject implements GreetingService {
    protected GreetingServiceImpl() throws RemoteException { super(); }

    @Override
    public String greet(String name) throws RemoteException {
        return "Hello, " + name + "! (from the remote server)";
    }
}

// 3. Server — registers the object with the RMI registry
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) throws Exception {
        GreetingServiceImpl service = new GreetingServiceImpl();
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("GreetingService", service);
        System.out.println("RMI server ready...");
    }
}

// 4. Client — looks up and calls the remote object
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        GreetingService service = (GreetingService) registry.lookup("GreetingService");
        System.out.println(service.greet("Rohit"));
    }
}
```

```
RMI Architecture:
Client → Stub (local proxy) → Network → Skeleton/Registry → Remote Object (Server)
```

## 5.3 Introduction to Enterprise JavaBeans (EJB)

**EJB** is a server-side component model (part of Jakarta EE) for building distributed, transactional, secure business logic — the container handles concurrency, transactions, security, and lifecycle so developers focus purely on business logic.

### Types of EJB

```
1. Session Bean
   ├── Stateless — no client-specific state retained between calls (pooled & reused, most scalable)
   ├── Stateful  — retains conversational state for ONE client across multiple calls
   └── Singleton — exactly ONE instance shared application-wide

2. Message-Driven Bean (MDB) — invoked asynchronously in response to JMS messages, not direct calls

(Entity Beans existed in EJB 2.x for O/R mapping — deprecated in favor of JPA in modern Jakarta EE)
```

### Creating and Working with a Session Bean

```java
// Remote/Local business interface
import jakarta.ejb.Local;

@Local
public interface CalculatorService {
    int add(int a, int b);
}

// Stateless Session Bean implementation
import jakarta.ejb.Stateless;

@Stateless
public class CalculatorServiceBean implements CalculatorService {
    @Override
    public int add(int a, int b) {
        return a + b;
    }
}

// Stateful Session Bean example — retains state ACROSS calls for one client
import jakarta.ejb.Stateful;

@Stateful
public class ShoppingCartBean {
    private java.util.List<String> items = new java.util.ArrayList<>();

    public void addItem(String item) { items.add(item); }
    public java.util.List<String> getItems() { return items; }   // remembers items across multiple calls
}

// Injecting and using an EJB from a Servlet (in a Jakarta EE container)
import jakarta.ejb.EJB;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.WebServlet;
import java.io.*;

@WebServlet("/calc")
public class CalcServlet extends HttpServlet {
    @EJB
    private CalculatorService calculator;   // container injects the bean automatically

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int result = calculator.add(5, 10);
        resp.getWriter().println("Result: " + result);
    }
}
```

**Container-provided services for EJBs:** declarative transactions (`@TransactionAttribute`), security (`@RolesAllowed`), pooling & lifecycle management, remote/local invocation transparency, dependency injection (`@EJB`, `@Resource`).

---
---

# 📚 Recommended Resources

| Resource | Covers |
|---|---|
| *Java: The Complete Reference* — Herbert Schildt | Unit I & II |
| *Head First Servlets & JSP* | Unit III & IV |
| *Head First EJB* / Oracle Jakarta EE Tutorial | Unit V |
| [docs.oracle.com/javase](https://docs.oracle.com/en/java/javase/) | Core Java API reference |
| [Jakarta EE Specifications](https://jakarta.ee/specifications/) | Servlet, JSP, EJB specs |
| [Apache Tomcat Docs](https://tomcat.apache.org/tomcat-10.1-doc/) | Server setup & deployment |

---

*Java M.Tech Reference Handbook | Units I–V | Core Java → Servlets/JSP → RMI/EJB*
