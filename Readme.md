### Spark High-Level Structured APIs:
Spark is a unified analytics engine for large-scale data processing. It provides high-level APIs in Scala, Java, Python, and R, and an optimized engine that supports general computation graphs for data analysis. It also supports a rich set of higher-level tools including Spark SQL for SQL and DataFrames, MLlib for machine learning, GraphX for graph processing, and Structured Streaming for stream processing. 

---

### Application
A user program built on Spark using its APIs. It consists of a driver program and
executors on the cluster.
### SparkSession
An object that provides a point of entry to interact with underlying Spark functionality 
and allows programming Spark with its APIs. In an interactive Spark
shell, the Spark driver instantiates a SparkSession for you, while in a Spark
application, you create a SparkSession object yourself.
### Job
A parallel computation consisting of multiple tasks that gets spawned in response
to a Spark action (e.g., save(), collect()).
### Stage
Each job gets divided into smaller sets of tasks called stages that depend on each
other.
### Task
A single unit of work or execution that will be sent to a Spark executor.

----------------------------------------------------

#### Spark operations on distributed data can be classified into two types: transformations and actions. 

1. Transformations: as the name suggests, transform a Spark DataFrame
into a new DataFrame without altering the original data, giving it the property of
immutability. Transformations can be classified as having either narrow dependencies or wide dependencies.
     1. Any transformation where a single output partition can be computed
        from a single input partition is a narrow transformation. For example, in the previous
        code snippet, filter() and contains() represent narrow transformations because
        they can operate on a single partition and produce the resulting output partition
        without any exchange of data.
     2. However, groupBy() or orderBy() instruct Spark to perform wide transformations,
        where data from other partitions is read in, combined, and written to disk. Since each
        partition will have its own count of the word that contains the “Spark” word in its row
        of data, a count (groupBy()) will force a shuffle of data from each of the executor’s
        partitions across the cluster. In this transformation, orderBy() requires to be output from
        other partitions to compute the final aggregation. 

