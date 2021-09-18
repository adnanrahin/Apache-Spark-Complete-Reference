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
+----------------------------+--------------------------------+
|Airline Names               |Total Number Of Flight Cancelled|
+----------------------------+--------------------------------+
|United Air Lines Inc.       |6573                            |
|Virgin America              |534                             |
|US Airways Inc.             |4067                            |
|American Eagle Airlines Inc.|15025                           |
|Southwest Airlines Co.      |16043                           |
+----------------------------+--------------------------------+
```

#### 3. Find total number flights by Airport IATA Code.
```
+---------------------------------------+-------------------+
|Airport Name                           |Total Number Flight|
+---------------------------------------+-------------------+
|LaGuardia Airport (Marine Air Terminal)|95074              |
+---------------------------------------+-------------------+
```

#### 4. Find most cancelled Airline Name and total Number of Cancelled Flights
```
+----------------------+-------------------+
|Airline Name          |Total Number Flight|
+----------------------+-------------------+
|Southwest Airlines Co.|16043              |
+----------------------+-------------------+
```

#### 5. Find Average delayed by Airliner
```
+----------------------------+------------------+
|Airline Name                |Average Delay     |
+----------------------------+------------------+
|US Airways Inc.             |3.553065019933418 |
|American Eagle Airlines Inc.|6.197287621554539 |
|Atlantic Southeast Airlines |6.3779928369489856|
|Hawaiian Airlines Inc.      |6.511530728899752 |
|Skywest Airlines Inc.       |6.522445811066178 |
|Southwest Airlines Co.      |6.575752200171454 |
|JetBlue Airways             |6.608200264868403 |
|Alaska Airlines Inc.        |6.608791285524754 |
|Delta Air Lines Inc.        |6.629987489349894 |
|United Air Lines Inc.       |6.6460316213296675|
|Spirit Air Lines            |6.72094474539545  |
|Virgin America              |6.727663804200818 |
|Frontier Airlines Inc.      |6.748626008332595 |
|American Airlines Inc.      |7.330276268590967 |
+----------------------------+------------------+
```
