package daa;

import java.io.File;
import java.io.PrintWriter;
import java.util.Random;

public class Benchmark {

    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final int RUNS = 5;
    private static final int SEED = 42;

    private static class Result {
        long timeNs;
        long steps;
        long moves;
        long comparisons;

        Result(long timeNs, Metrics metrics) {
            this.timeNs = timeNs;
            this.steps = metrics.steps;
            this.moves = metrics.moves;
            this.comparisons = metrics.comparisons;
        }

        double getTimeMs() {
            return timeNs / 1_000_000.0;
        }
    }

    private static void writeResult(
            PrintWriter writer,
            String workload,
            String variant,
            String structure,
            int n,
            Result result) {

        writer.println(
                workload + ","
                        + variant + ","
                        + structure + ","
                        + n + ","
                        + result.getTimeMs() + ","
                        + result.steps + ","
                        + result.moves + ","
                        + result.comparisons
        );
    }

    public static void main(String[] args) {

        try {
            File resultsDirectory = new File("results");
            resultsDirectory.mkdirs();

            PrintWriter writer = new PrintWriter("results/results.csv");

            writer.println("workload,variant,structure,n,time_ms,steps,moves,comparisons");

            for (int n : SIZES) {
                int[] data = generateData(n);

                //Warm-up
                runRandomAccessArray(data);
                runRandomAccessList(data);

                Result[] arrayResults = new Result[RUNS];
                Result[] listResults = new Result[RUNS];

                for (int run = 0; run < RUNS; run++) {
                    arrayResults[run] = runRandomAccessArray(data);
                    listResults[run] = runRandomAccessList(data);
                }

                Result arrayMedian = median(arrayResults);
                Result listMedian = median(listResults);
                writeResult(writer, "W1", "-", "DynamicArray", n, arrayMedian);
                writeResult(writer, "W1", "-", "MyLinkedList", n, listMedian);

                System.out.println("W1 Random Access, n = " + n);

                System.out.println(
                        "DynamicArray: time = " + arrayMedian.getTimeMs()
                                + " ms, steps = " + arrayMedian.steps
                                + ", moves = " + arrayMedian.moves
                                + ", comparisons = " + arrayMedian.comparisons
                );

                System.out.println(
                        "MyLinkedList: time = " + listMedian.getTimeMs()
                                + " ms, steps = " + listMedian.steps
                                + ", moves = " + listMedian.moves
                                + ", comparisons = " + listMedian.comparisons
                );

                System.out.println();

                // W2 Search warm-up
                runSearchArray(data);
                runSearchList(data);

                Result[] searchArrayResults = new Result[RUNS];
                Result[] searchListResults = new Result[RUNS];

                for (int run = 0; run < RUNS; run++) {
                    searchArrayResults[run] = runSearchArray(data);
                    searchListResults[run] = runSearchList(data);
                }

                Result searchArrayMedian = median(searchArrayResults);
                Result searchListMedian = median(searchListResults);
                writeResult(writer, "W2", "-", "DynamicArray", n, searchArrayMedian);
                writeResult(writer, "W2", "-", "MyLinkedList", n, searchListMedian);

                System.out.println("W2 Search, n = " + n);

                System.out.println(
                        "DynamicArray: time = " + searchArrayMedian.getTimeMs()
                                + " ms, steps = " + searchArrayMedian.steps
                                + ", moves = " + searchArrayMedian.moves
                                + ", comparisons = " + searchArrayMedian.comparisons
                );

                System.out.println(
                        "MyLinkedList: time = " + searchListMedian.getTimeMs()
                                + " ms, steps = " + searchListMedian.steps
                                + ", moves = " + searchListMedian.moves
                                + ", comparisons = " + searchListMedian.comparisons
                );

                System.out.println();

                // W3 Insert & Remove - HEAD warm-up
                runInsertRemoveArray(data, false);
                runInsertRemoveList(data, false);

                Result[] headArrayResults = new Result[RUNS];
                Result[] headListResults = new Result[RUNS];

                for (int run = 0; run < RUNS; run++) {
                    headArrayResults[run] = runInsertRemoveArray(data, false);
                    headListResults[run] = runInsertRemoveList(data, false);
                }

                Result headArrayMedian = median(headArrayResults);
                Result headListMedian = median(headListResults);
                writeResult(writer, "W3", "head", "DynamicArray", n, headArrayMedian);
                writeResult(writer, "W3", "head", "MyLinkedList", n, headListMedian);


                System.out.println("W3 Insert & Remove HEAD, n = " + n);

                System.out.println(
                        "DynamicArray: time = " + headArrayMedian.getTimeMs()
                                + " ms, steps = " + headArrayMedian.steps
                                + ", moves = " + headArrayMedian.moves
                                + ", comparisons = " + headArrayMedian.comparisons
                );

                System.out.println(
                        "MyLinkedList: time = " + headListMedian.getTimeMs()
                                + " ms, steps = " + headListMedian.steps
                                + ", moves = " + headListMedian.moves
                                + ", comparisons = " + headListMedian.comparisons
                );

                System.out.println();


// W3 Insert & Remove - MIDDLE warm-up
                runInsertRemoveArray(data, true);
                runInsertRemoveList(data, true);

                Result[] middleArrayResults = new Result[RUNS];
                Result[] middleListResults = new Result[RUNS];

                for (int run = 0; run < RUNS; run++) {
                    middleArrayResults[run] = runInsertRemoveArray(data, true);
                    middleListResults[run] = runInsertRemoveList(data, true);
                }

                Result middleArrayMedian = median(middleArrayResults);
                Result middleListMedian = median(middleListResults);
                writeResult(writer, "W3", "middle", "DynamicArray", n, middleArrayMedian);
                writeResult(writer, "W3", "middle", "MyLinkedList", n, middleListMedian);

                System.out.println("W3 Insert & Remove MIDDLE, n = " + n);

                System.out.println(
                        "DynamicArray: time = " + middleArrayMedian.getTimeMs()
                                + " ms, steps = " + middleArrayMedian.steps
                                + ", moves = " + middleArrayMedian.moves
                                + ", comparisons = " + middleArrayMedian.comparisons
                );

                System.out.println(
                        "MyLinkedList: time = " + middleListMedian.getTimeMs()
                                + " ms, steps = " + middleListMedian.steps
                                + ", moves = " + middleListMedian.moves
                                + ", comparisons = " + middleListMedian.comparisons
                );

                System.out.println();

                // W4 Priority Processing warm-up
                runPriorityProcessing(data);

                Result[] heapResults = new Result[RUNS];

                for (int run = 0; run < RUNS; run++) {
                    heapResults[run] = runPriorityProcessing(data);
                }

                Result heapMedian = median(heapResults);
                writeResult(writer, "W4", "-", "MinHeap", n, heapMedian);

                System.out.println("W4 Priority Processing, n = " + n);

                System.out.println(
                        "MinHeap: time = " + heapMedian.getTimeMs()
                                + " ms, steps = " + heapMedian.steps
                                + ", moves = " + heapMedian.moves
                                + ", comparisons = " + heapMedian.comparisons
                );

                System.out.println();
            }
            writer.close();
            System.out.println("results/results.csv created successfully.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static int[] generateData(int n) {
        Random random = new Random(SEED);
        int[] data = new int[n];

        for (int i = 0; i < n; i++) {
            data[i] = random.nextInt(1_000_000);
        }

        return data;
    }

    private static Result runRandomAccessArray(int[] data) {
        Metrics metrics = new Metrics();
        IntList array = new DynamicArray(metrics);

        for (int value : data) {
            array.add(value);
        }

        metrics.reset();

        Random random = new Random(SEED);

        long start = System.nanoTime();

        for (int i = 0; i < 10000; i++) {
            int index = random.nextInt(data.length);
            array.get(index);
        }

        long end = System.nanoTime();

        return new Result(end - start, metrics);
    }

    private static Result runRandomAccessList(int[] data) {
        Metrics metrics = new Metrics();
        IntList list = new MyLinkedList(metrics);

        for (int value : data) {
            list.add(value);
        }

        metrics.reset();

        Random random = new Random(SEED);

        long start = System.nanoTime();

        for (int i = 0; i < 10000; i++) {
            int index = random.nextInt(data.length);
            list.get(index);
        }

        long end = System.nanoTime();

        return new Result(end - start, metrics);
    }

    private static Result median(Result[] results) {
        for (int i = 0; i < results.length - 1; i++) {
            for (int j = i + 1; j < results.length; j++) {
                if (results[j].timeNs < results[i].timeNs) {
                    Result temp = results[i];
                    results[i] = results[j];
                    results[j] = temp;
                }
            }
        }

        return results[results.length / 2];
    }
    private static Result runSearchArray(int[] data) {
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(metrics);

        for (int value : data) {
            array.add(value);
        }

        metrics.reset();

        long start = System.nanoTime();

        for (int i = 0; i < 500; i++) {
            array.contains(data[i % data.length]);
        }

        for (int i = 0; i < 500; i++) {
            array.contains(-1 - i);
        }

        long end = System.nanoTime();

        return new Result(end - start, metrics);
    }

    private static Result runSearchList(int[] data) {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        for (int value : data) {
            list.add(value);
        }

        metrics.reset();

        long start = System.nanoTime();

        for (int i = 0; i < 500; i++) {
            list.contains(data[i % data.length]);
        }

        for (int i = 0; i < 500; i++) {
            list.contains(-1 - i);
        }

        long end = System.nanoTime();

        return new Result(end - start, metrics);
    }

    private static Result runInsertRemoveArray(int[] data, boolean middle) {
        Metrics metrics = new Metrics();
        DynamicArray array = new DynamicArray(metrics);

        for (int value : data) {
            array.add(value);
        }

        metrics.reset();

        int index;

        if (middle) {
            index = data.length / 2;
        } else {
            index = 0;
        }

        long start = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            array.add(index, i);
        }

        for (int i = 0; i < 1000; i++) {
            array.remove(index);
        }

        long end = System.nanoTime();

        return new Result(end - start, metrics);
    }

    private static Result runInsertRemoveList(int[] data, boolean middle) {
        Metrics metrics = new Metrics();
        MyLinkedList list = new MyLinkedList(metrics);

        for (int value : data) {
            list.add(value);
        }

        metrics.reset();

        int index;

        if (middle) {
            index = data.length / 2;
        } else {
            index = 0;
        }

        long start = System.nanoTime();

        for (int i = 0; i < 1000; i++) {
            list.add(index, i);
        }

        for (int i = 0; i < 1000; i++) {
            list.remove(index);
        }

        long end = System.nanoTime();

        return new Result(end - start, metrics);
    }

    private static Result runPriorityProcessing(int[] data) {
        Metrics metrics = new Metrics();
        MinHeap heap = new MinHeap(metrics);

        metrics.reset();

        long start = System.nanoTime();

        for (int value : data) {
            heap.insert(value);
        }

        int previous = heap.extractMin();

        while (heap.size() > 0) {
            int current = heap.extractMin();

            if (current < previous) {
                throw new IllegalStateException("Heap output is not sorted");
            }

            previous = current;
        }

        long end = System.nanoTime();

        return new Result(end - start, metrics);
    }
}