#### Spark High-Level Structured APIs:
Spark is a unified analytics engine for large-scale data processing. It provides high-level APIs in Scala, Java, Python, and R, and an optimized engine that supports general computation graphs for data analysis. It also supports a rich set of higher-level tools including Spark SQL for SQL and DataFrames, MLlib for machine learning, GraphX for graph processing, and Structured Streaming for stream processing. 

### Application
A user program built on Spark using its APIs. It consists of a driver program and
executors on the cluster.
### SparkSession
An object that provides a point of entry to interact with underlying Spark func‐
tionality and allows programming Spark with its APIs. In an interactive Spark
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
