# Synchronized vs Concurrent HashMap in Java

This document explains the differences between `Collections.synchronizedMap(HashMap)`, `Hashtable`, and `ConcurrentHashMap` — how they achieve thread safety, their performance characteristics, and when to use each.

---

## 1. HashMap (baseline)

`HashMap` is **not thread-safe**. If multiple threads modify it concurrently (especially during resize), it can cause data corruption or even infinite loops (in older JDKs) due to race conditions in the internal bucket/linked-list structure.

```java
Map<String, Integer> map = new HashMap<>(); // NOT thread-safe
```

---

## 2. Hashtable

- One of the original (legacy, JDK 1.0) thread-safe map implementations.
- Every method (`get`, `put`, `remove`, etc.) is `synchronized` on the **entire object**.
- Only one thread can access the map at a time — even for reads.
- Does **not** allow `null` keys or `null` values.

```java
Map<String, Integer> table = new Hashtable<>();
```

**Downside:** Coarse-grained locking → poor performance under high concurrency (threads block each other even for unrelated operations).

---

## 3. Collections.synchronizedMap(HashMap)

- Wraps a regular `HashMap` and synchronizes every method call using a single intrinsic lock (the map object itself, or a lock passed in).
- Behaves similarly to `Hashtable` in terms of locking granularity.
- Allows one `null` key and multiple `null` values (since it wraps `HashMap`).
- **Iteration is not thread-safe** — you must manually synchronize on the map while iterating.

```java
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

// Manual sync required for iteration
synchronized (syncMap) {
    for (Map.Entry<String, Integer> entry : syncMap.entrySet()) {
        // safe here
    }
}
```

**Downside:** Same coarse-grained, single-lock bottleneck as `Hashtable`.

---

## 4. ConcurrentHashMap

- Introduced in `java.util.concurrent` (JDK 1.5), redesigned significantly in JDK 8.
- **JDK 7 and earlier:** used *segment-based locking* (lock striping) — the map was divided into segments, each with its own lock, allowing concurrent writes to different segments.
- **JDK 8+:** dropped segments in favor of **per-bucket (per-node) locking** using `synchronized` blocks on individual bin heads, combined with CAS (Compare-And-Swap) operations for lock-free reads and initial inserts.
- **Reads are lock-free** (using `volatile` reads), so `get()` doesn't block.
- Writes only lock the specific bucket being modified — not the entire map.
- Does **not** allow `null` keys or `null` values (to avoid ambiguity between "key not found" and "key mapped to null" in concurrent contexts).
- Iterators are **weakly consistent** — they won't throw `ConcurrentModificationException` and reflect the state of the map at some point during iteration (may or may not show concurrent updates).

```java
Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
```

**Advantage:** Much higher throughput under concurrent access since threads working on different buckets don't block each other.

---

## 5. Comparison Table

| Feature                     | HashMap        | Hashtable         | synchronizedMap(HashMap) | ConcurrentHashMap            |
|------------------------------|----------------|--------------------|----------------------------|-------------------------------|
| Thread-safe                  | No             | Yes                | Yes                        | Yes                           |
| Locking granularity          | N/A            | Whole object       | Whole object                | Per-bucket/node (JDK8+)      |
| Null keys/values             | Allowed        | Not allowed        | 1 null key allowed          | Not allowed                   |
| Read performance             | N/A            | Blocking           | Blocking                    | Lock-free (mostly)            |
| Write performance            | N/A            | Poor (single lock) | Poor (single lock)          | Good (fine-grained locks)     |
| Iterator behavior            | Fail-fast      | Fail-fast          | Fail-fast (needs manual sync)| Weakly consistent            |
| Legacy                       | No             | Yes (JDK 1.0)       | No                          | No                             |

---

## 6. When to Use What

- **Single-threaded code:** `HashMap`
- **Legacy codebase already using it:** `Hashtable` (otherwise avoid — considered obsolete)
- **Need a synchronized wrapper quickly, low concurrency:** `Collections.synchronizedMap()`
- **High-concurrency production code:** `ConcurrentHashMap` — almost always the right choice today

---

## 7. Quick Notes for Interviews

- `ConcurrentHashMap` never locks the entire map for reads — that's its key advantage over `Hashtable`/`synchronizedMap`.
- In JDK 8+, `ConcurrentHashMap` also uses **tree bins** (red-black trees) instead of linked lists when a bucket has too many collisions (like `HashMap` in JDK 8+), improving worst-case lookup from O(n) to O(log n).
- `size()` on `ConcurrentHashMap` is an approximation under concurrent modification — it doesn't lock the whole map to compute an exact count.
- Compound operations (e.g., check-then-act like `if (!map.containsKey(k)) map.put(k, v)`) are still **not atomic** unless you use `putIfAbsent()`, `computeIfAbsent()`, etc.

# HashMap Concurrency Demos & Benchmark

Companion code examples for `README.md` (Synchronized vs Concurrent HashMap).
Both snippets are self-contained — just paste each into its own `.java` file and run.

---

## 1. ConcurrentModificationException Demo

Shows how a `HashMap` (or even `synchronizedMap`-wrapped one, without manual locking)
throws `ConcurrentModificationException` when modified while being iterated by another thread —
and how `ConcurrentHashMap` doesn't.

```java
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CMEDemo {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== HashMap: expect ConcurrentModificationException ===");
        runDemo(new HashMap<>());

        Thread.sleep(500);

        System.out.println("\n=== ConcurrentHashMap: no exception, weakly consistent iteration ===");
        runDemo(new ConcurrentHashMap<>());
    }

    private static void runDemo(Map<Integer, Integer> map) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            map.put(i, i);
        }

        Thread writer = new Thread(() -> {
            for (int i = 1000; i < 2000; i++) {
                map.put(i, i);
                try { Thread.sleep(1); } catch (InterruptedException ignored) {}
            }
        });

        Thread reader = new Thread(() -> {
            try {
                int count = 0;
                for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
                    count++;
                    Thread.sleep(1);
                }
                System.out.println("Iteration finished normally. Entries seen: " + count);
            } catch (java.util.ConcurrentModificationException e) {
                System.out.println("Caught ConcurrentModificationException!");
            } catch (InterruptedException ignored) {}
        });

        writer.start();
        reader.start();
        writer.join();
        reader.join();
    }
}
```

**Expected output pattern:**
- `HashMap` → reader thread throws `ConcurrentModificationException` almost immediately.
- `ConcurrentHashMap` → reader finishes iteration cleanly (may or may not include entries added mid-iteration — that's the "weakly consistent" behavior).

---

## 2. Throughput Benchmark

A simple multi-threaded benchmark comparing `Hashtable`, `Collections.synchronizedMap(HashMap)`,
and `ConcurrentHashMap` under mixed read/write load. Not JMH-grade precision, but good enough
to *see the difference* clearly.

```java
import java.util.Hashtable;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class MapBenchmark {

    private static final int THREAD_COUNT = 8;
    private static final int OPS_PER_THREAD = 500_000;

    public static void main(String[] args) throws InterruptedException {
        benchmark("Hashtable", new Hashtable<>());
        benchmark("synchronizedMap(HashMap)", Collections.synchronizedMap(new HashMap<>()));
        benchmark("ConcurrentHashMap", new ConcurrentHashMap<>());
    }

    private static void benchmark(String label, Map<Integer, Integer> map) throws InterruptedException {
        ExecutorService pool = Executors.newFixedThreadPool(THREAD_COUNT);
        AtomicLong totalOps = new AtomicLong();

        long start = System.nanoTime();

        for (int t = 0; t < THREAD_COUNT; t++) {
            final int threadId = t;
            pool.submit(() -> {
                for (int i = 0; i < OPS_PER_THREAD; i++) {
                    int key = (threadId * OPS_PER_THREAD + i) % 10_000;
                    if (i % 5 == 0) {
                        map.put(key, i);       // 20% writes
                    } else {
                        map.get(key);           // 80% reads
                    }
                    totalOps.incrementAndGet();
                }
            });
        }

        pool.shutdown();
        pool.awaitTermination(60, TimeUnit.SECONDS);

        long elapsedMs = (System.nanoTime() - start) / 1_000_000;
        System.out.printf("%-30s | ops: %,d | time: %d ms | throughput: %,d ops/sec%n",
                label, totalOps.get(), elapsedMs, (totalOps.get() * 1000L) / Math.max(elapsedMs, 1));
    }
}
```

**What to expect:**
- `Hashtable` and `synchronizedMap` will show similar (slower) throughput — both serialize all access through one lock.
- `ConcurrentHashMap` should noticeably outperform both, especially as `THREAD_COUNT` increases, since reads don't block and writes only lock the affected bucket.

> Run this on a multi-core machine for the difference to actually show — on a single core, all three will look similar since there's no real parallelism to exploit.

---

## 3. Notes

- These are teaching/demo benchmarks, not rigorous microbenchmarks. For real performance testing, use [JMH](https://github.com/openjdk/jmh) to avoid JIT warm-up and dead-code-elimination pitfalls.
- The CME demo's outcome can vary run-to-run depending on thread scheduling — that's expected, since it depends on exact timing of `put()` vs iteration.
