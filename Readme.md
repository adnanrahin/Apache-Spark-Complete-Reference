## Introduction to Apache Spark and Scala


## [Programming in Scala](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/programming-in-scala) 

1. [Sample Scala Code Intro](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/programming-in-scala/src/main/scala/org/programming/scala/introduction)
2. [Case Classes and Pattern Matching Example](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/programming-in-scala/src/main/scala/org/programming/scala/case_classes_and_pattern_matching)
3. [Class and Objects](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/blob/master/programming-in-scala/src/main/scala/org/programming/scala/clsss_and_objectsa)
4. [Control Abstraction](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/programming-in-scala/src/main/scala/org/programming/scala/control_abstraction)
5. [Functional Object](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/programming-in-scala/src/main/scala/org/programming/scala/functional_objects)
6. [Functional Programming in Scala](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/programming-in-scala/src/main/scala/org/programming/scala/functional_programming_scala)
7. [Scala Data Structures](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/programming-in-scala/src/main/scala/org/programming/scala/working_with_lists)

## [Spark Definitive Guide](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/spark-definitive-guide)

1. [Introduction to Spark](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/spark-definitive-guide/src/main/scala/org/spark/apis/introduction)
2. [Spark Data Frame Aggregations](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/spark-definitive-guide/src/main/scala/org/spark/apis/aggregations)
3. [Spark RDD](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/spark-definitive-guide/src/main/scala/org/spark/apis/rdd)
4. [Spark String](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/spark-definitive-guide/src/main/scala/org/spark/apis/strings)
5. [Structured API's](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/spark-definitive-guide/src/main/scala/org/spark/apis/structuredapis)

## Others Example Code

1. [Read Data from HDFS to Spark DataFrame](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/blob/master/spark-definitive-guide/src/main/scala/org/spark/apis/readfromcluster/ReadFileFromHdfsClusterToDF.scala)
2. [Working Example of Spark RDD](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/blob/master/spark-in-action/src/main/scala/org/apache/spark/spark_in_action/chapter_four_rdd/CustomerTransaction.scala)
3. [Flight Data Actions and Transformations With Spark RDD](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/blob/master/spark-resilient-distributed-dataset/src/main/scala/org/spark/rdd/flights_data/FlightsDataAnalysis.scala)
4. [Wikipedia Assignment From Coursera](https://github.com/adnanrahin/Apache-Spark-Complete-Reference/tree/master/spark-resilient-distributed-dataset/src/main/scala/org/spark/rdd/wikipedia)

## Spark Readings

### RDD: Resilient Distributed Dataset

```
The RDD is the fundamental abstraction in Spark. It represents a
collection of elements that is
    1. Immutable (read-only)
    2. Resilient (fault-tolerant)
    3. Distributed (dataset spread out to more than one node)
To summarize, the purpose of RDDs is to facilitate parallel operations on large datasets in a straightforward manner, abstracting away their distributed nature and inherent fault tolerance.
RDDs are resilient because of Spark’ s built-in fault recovery mechanics. Spark is capable of healing RDDs in case of node failure. 
```

### RDD Operations
```
There are two types of RDD Operations: 
    1. Trasformations.
    2. Actions.
```
#### RDD Transformations:
```
Transformations (for example, filter or map) are operations that produce a new RDD by performing some useful data manipulation on another RDD.

It’ s important to understand that transformations are evaluated lazily, meaning computation doesn’ t take place until you invoke an action. Once
an action is triggered on an RDD, Spark examines the RDD’ s lineage and uses that information to build a “ graph of operations” that needs to
be executed in order to compute the action. Think of a transformation as a sort of diagram that tells Spark which operations need to happen and
in which order once an action gets executed.

```
#### RDD Actions:
```
Actions(for example, count or foreach) trigger a computation in order to return the result to the calling program or to perform some actions on an RDD’ s elements.
```

### Apache Spark Partitions

#### What is a partition in Spark?
```
In Big Data world Spark RDD can be huge in size, it is not always possible to fit the dataset into one single node, and have be paritioned across various nodes. 
Spark automatically partitions RDDs and distributes the partitions across different nodes. A partition in spark is an atomic chunk of data (logical division of data) 
stored on a node in the cluster. Partitions are basic units of parallelism in Apache Spark. RDDs in Apache Spark are collection of partitions.
```
#### Characteristics of Partitions in Apache Spark
```
1. Every machine in a spark cluster contains one or more partitions.
2. The number of partitions in spark are configurable and having too few or too many partitions is not good.
3. Partitions in Spark do not span multiple machines.
```

#### Partitioning in Apache Spark
```
One important way to increase parallelism of spark processing is to increase the number of executors on the cluster. However, knowing how the data should be distributed, 
so that the cluster can process data efficiently is extremely important.  Partitioning in Spark might not be helpful for all applications, for instance, if a RDD is scanned 
only once, then portioning data within the RDD might not be helpful but if a dataset is reused multiple times in various key oriented operations like joins, then partitioning 
data will be helpful.
```
#### Types of Partition in Spark

```
    1. Hash Partitioning in Spark
    2. Range Partitioning in Spark
```


