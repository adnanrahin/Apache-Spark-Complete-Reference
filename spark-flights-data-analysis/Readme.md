## Airline Flights Data Analysis: Find all the informations about cancelled and delayed. 

#### 1. Find all the flight that cancelled in 2015
```
+-------+----------+-------------+------------------+-------------------+
|airline|tailNumber|originAirport|destinationAirport|cancellationsReason|
+-------+----------+-------------+------------------+-------------------+
|     AS|    N431AS|          ANC|               SEA|                  A|
|     AA|    N3BDAA|          PHX|               DFW|                  B|
|     OO|    N746SK|          MAF|               IAH|                  B|
|     MQ|    N660MQ|          SGF|               DFW|                  B|
|     OO|    N583SW|          RDD|               SFO|                  A|
+-------+----------+-------------+------------------+-------------------+
only showing top 5 rows
```

#### 2. Find total of number flights cancelled byt Airline Name
```
+--------------------+--------------------------------+
|       Airline Names|Total Number Of Flight Cancelled|
+--------------------+--------------------------------+
|United Air Lines ...|                            6573|
|      Virgin America|                             534|
|     US Airways Inc.|                            4067|
|American Eagle Ai...|                           15025|
|Southwest Airline...|                           16043|
+--------------------+--------------------------------+
only showing top 5 rows
```
```bigquery
select * from where id = megatron
```
