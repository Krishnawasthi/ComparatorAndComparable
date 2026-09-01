# Comparable vs Comparator in Java

Understanding **Comparable** and **Comparator** is important when working with collections in Java, especially when you need to sort objects.

---

## Natural Ordering

**Natural ordering** means the **default way an object should normally be sorted**.

For example, suppose we have a `Student` class. We might decide that the natural order of students should be based on their **Student ID**.

This ordering is defined **inside the class itself** using the `Comparable` interface.

### Think of it as:

> **"This is the normal/default order of my objects."**

For example:

```text
Student ID

101
102
103
104
```

The class itself defines how its objects should naturally be compared.

---

## Custom Ordering

**Custom ordering** means you want to sort the **same objects in different ways**, depending on the requirement.

For example, an `Organization` class might contain:

* Employee Name
* Employee ID
* Department
* Salary

Different situations may require different sorting rules.

You might want to:

* Sort employees by **name**
* Sort employees by **salary**
* Sort employees by **department**
* Sort employees by **employee ID**

These different sorting requirements can be handled using the **Comparator** interface.

The comparison logic is defined **outside the original class**.

### Think of it as:

> **"I already have my objects, but now I want to sort them differently."**

---

## Real-Life Example

Imagine we have a company with many employees.

### Natural Ordering

Suppose the company normally identifies employees by their **Employee ID**.

Therefore, the default ordering could be:

```text
Employee ID

org123
org345
org424
org635
org644
```

This can be considered the **natural ordering** of the objects.

---

### Custom Ordering

Now suppose different departments need different reports.

The HR department might want:

```text
Sort by Employee Name
```

The Finance department might want:

```text
Sort by Salary
```

The management team might want:

```text
Sort by Department
```

These are **custom sorting requirements**.

Instead of changing the `Organization` class every time, we can create different `Comparator`s for different requirements.

---

## Comparable vs Comparator

| Feature          | Comparable                | Comparator               |
| ---------------- | ------------------------- | ------------------------ |
| Purpose          | Natural/default ordering  | Custom ordering          |
| Defined          | Inside the class          | Outside the class        |
| Sorting rules    | Usually one natural order | Can have multiple        |
| Interface method | `compareTo()`             | `compare()`              |
| Example          | Employee ID               | Name, Salary, Department |

---

## Key Difference

### Comparable

`Comparable` answers:

> **"How should objects of this class normally be sorted?"**

The class itself defines its natural ordering.

### Comparator

`Comparator` answers:

> **"How do you want these objects to be sorted this time?"**

The sorting logic can be created separately from the original class.

---

## Easy Way to Remember

```text
Comparable
    ↓
Natural / Default Ordering
    ↓
Defined inside the class


Comparator
    ↓
Custom / Different Ordering
    ↓
Defined outside the class
```

### Shortcut

> **Comparable = "How should I normally be sorted?"**

> **Comparator = "How do you want me to be sorted this time?"**

---

## Summary

* **Comparable** is used when a class has a **natural/default ordering**.
* **Comparator** is used when you need **custom sorting logic**.
* Comparable's comparison logic is generally defined **inside the class**.
* Comparator's comparison logic is defined **outside the class**.
* A class can have **one natural ordering** but can have **multiple Comparators** for different sorting requirements.
* Comparator is especially useful when the same objects need to be sorted in different ways.
