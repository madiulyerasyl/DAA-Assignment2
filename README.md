# DAA Assignment 2 - Data Structures

## Description

This project implements three data structures from scratch:

- DynamicArray
- MyLinkedList
- MinHeap

All structures store primitive int values. The project also contains JUnit 5
tests, operation metrics, benchmark workloads, CSV results, and plots.

## Requirements

- Java 25
- Maven
- Python 3
- Matplotlib for generating plots

## Project Structure

```text
src/main/java/daa/
    DynamicArray.java
    MyLinkedList.java
    MinHeap.java
    IntList.java
    Metrics.java
    Benchmark.java

src/test/java/daa/
    DynamicArrayTest.java
    MyLinkedListTest.java
    MinHeapTest.java

results/
    results.csv
    plots/

REPORT.md
README.md
plot_results.py
```
## Run Tests

JUnit 5 tests can be executed in IntelliJ IDEA.

Open the Maven tool window and run:

```text
Lifecycle -> test
```

All tests should finish with `BUILD SUCCESS`.

## Compile Project

Open the Maven tool window in IntelliJ IDEA and run:

```text
Lifecycle -> compile
```

The project should finish with `BUILD SUCCESS`.

## Run Benchmark

Open:

```text
src/main/java/daa/Benchmark.java
```

Run the `main()` method in IntelliJ IDEA.

The benchmark automatically creates:

```text
results/results.csv
```

The benchmark uses `Random(42)`. Each case has a warm-up run and five measured
runs. The median execution time is saved to the CSV file.

## Generate Plots

Run this command from the project directory:

```bash
python plot_results.py
```

The generated PNG plots are saved in:

```text
results/plots/
```

## Benchmark Workloads

- W1 - Random Access
- W2 - Search
- W3 - Insert and Remove at Head and Middle
- W4 - Priority Processing with MinHeap

## GitHub

Repository:

https://github.com/madiulyerasyl/DAA-Assignment2

Main branch: `main`

Release tag: `v1.0`