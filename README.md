# ComparatorAndComparable

## Comparator is used for external sorting.
# Comparable vs Comparator (Java)

A quick reference for the two core interfaces Java provides for defining object ordering.

## Table of Contents
- [Overview](#overview)
- [Comparable](#comparable)
- [Comparator](#comparator)
- [Key Differences](#key-differences)
- [When to Use Which](#when-to-use-which)
- [Multiple Field Sorting](#multiple-field-sorting)
- [Common Pitfalls](#common-pitfalls)

## Overview

Both interfaces let you define custom sorting logic for objects, but they solve different problems:

| | Comparable | Comparator |
|---|---|---|
| Package | `java.lang` | `java.util` |
| Method | `compareTo(T o)` | `compare(T o1, T o2)` |
| Sorting logic location | Inside the class itself | Outside the class (separate class/lambda) |
| Number of sort sequences | Only one (natural ordering) | Multiple |
| Affects original class | Yes, class must implement it | No, class is untouched |

## Comparable

Implemented by the class whose objects need to be sorted. Defines the class's **natural ordering**.

```java
public class Employee implements Comparable<Employee> {
    private String name;
    private int age;

    // constructor, getters...

    @Override
    public int compareTo(Employee other) {
        return this.age - other.age; // natural order: by age ascending
    }
}
```

Usage:

```java
List<Employee> employees = new ArrayList<>();
Collections.sort(employees); // uses compareTo()
```

## Comparator

A separate object passed to sorting methods. Used when you need custom or multiple ways to sort, or when you can't modify the source class.

```java
public class NameComparator implements Comparator<Employee> {
    @Override
    public int compare(Employee e1, Employee e2) {
        return e1.getName().compareTo(e2.getName());
    }
}
```

Usage:

```java
List<Employee> employees = new ArrayList<>();
Collections.sort(employees, new NameComparator());

// Or with a lambda (Java 8+)
employees.sort((e1, e2) -> e1.getName().compareTo(e2.getName()));

// Or with Comparator.comparing
employees.sort(Comparator.comparing(Employee::getName));
```

## Key Differences

1. **Ownership**: `Comparable` is implemented *by* the class being compared. `Comparator` is a *separate* class/lambda that compares two instances.
2. **Number of orderings**: A class can implement `Comparable` only once (single natural order). You can write as many `Comparator`s as you need.
3. **Modification**: `Comparable` requires changing the source class. `Comparator` doesn't — useful for sorting classes you don't own (e.g., library classes).
4. **Method signature**: `compareTo(T o)` (one argument, called on an instance) vs `compare(T o1, T o2)` (two arguments, static-style comparison).

## When to Use Which

- Use **Comparable** when there's one obvious, natural way to order objects of a class (e.g., `Integer`, `String` sort naturally).
- Use **Comparator** when:
  - You need multiple sort orders (by name, by age, by salary, etc.)
  - You can't modify the class (third-party library classes)
  - The sorting logic is situational and shouldn't live inside the domain class

## Multiple Field Sorting

```java
employees.sort(
    Comparator.comparing(Employee::getAge)
              .thenComparing(Employee::getName)
);

// Descending order
employees.sort(
    Comparator.comparing(Employee::getAge).reversed()
);

// Null-safe sorting
employees.sort(
    Comparator.comparing(Employee::getName, Comparator.nullsFirst(Comparator.naturalOrder()))
);
```

## Common Pitfalls

- **Integer overflow**: Avoid `return a - b` for large or negative numbers; prefer `Integer.compare(a, b)`.
- **Inconsistent with equals**: `compareTo`/`compare` returning 0 should ideally align with `equals()` — otherwise sorted collections like `TreeSet`/`TreeMap` can behave unexpectedly (they treat compare-equal elements as duplicates).
- **Not overriding correctly**: Ensure `compareTo` returns negative, zero, or positive consistently — not just `-1`, `0`, `1` arbitrarily, though that's also valid as long as it's consistent.
- **NullPointerException**: Neither method handles nulls automatically — use `Comparator.nullsFirst()` / `nullsLast()` when nulls are possible.

## Return Value Convention

Both `compareTo` and `compare` return:
- **Negative** → first object comes before the second
- **Zero** → objects are equal in terms of ordering
- **Positive** → first object comes after the second
