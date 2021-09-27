## RDD: Resilient Distributed Dataset

```
The RDD is the fundamental abstraction in Spark. It represents a
collection of elements that is
    1. Immutable (read-only)
    2. Resilient (fault-tolerant)
    3. Distributed (dataset spread out to more than one node)
To summarize, the purpose of RDDs is to facilitate parallel operations on large datasets in a straightforward manner, abstracting away their distributed nature and inherent fault tolerance.
RDDs are resilient because of Spark’ s built-in fault recovery mechanics. Spark is capable of healing RDDs in case of node failure. 
```

## RDD Operations
```
There are two types of RDD Operations: 
    1. Trasformations.
    2. Actions.
```
### RDD Transformations: 
```
Transformations (for example, filter or map) are operations that produce a new RDD by performing some useful data manipulation on another RDD.

It’ s important to understand that transformations are evaluated lazily, meaning computation doesn’ t take place until you invoke an action. Once
an action is triggered on an RDD, Spark examines the RDD’ s lineage and uses that information to build a “ graph of operations” that needs to
be executed in order to compute the action. Think of a transformation as a sort of diagram that tells Spark which operations need to happen and
in which order once an action gets executed.

```
### RDD Actions:
```
Actions(for example, count or foreach) trigger a computation in order to return the result to the calling program or to perform some actions on an RDD’ s elements.
```
