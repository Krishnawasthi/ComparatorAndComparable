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
