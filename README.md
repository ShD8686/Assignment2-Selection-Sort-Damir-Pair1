# Assignment 2 - Selection Sort (Student B)


This project contains an implementation of Selection Sort with an early sortedness check, a performance tracker, a simple CLI benchmark runner, and JUnit5 tests.


## Build & Run


Build:
```
mvn clean package
```


Run tests:
```
mvn test
```


Run benchmark (jar must be built first):
```
java -cp target/assignment2-selection-sort-1.0-SNAPSHOT.jar cli.BenchmarkRunner --sizes 100,1000,10000 --repeats 3 --distribution random
```


Outputs are printed to stdout and written to `benchmark_results.csv`.


## Notes on optimizations
- We perform an initial O(n) check for already sorted input which helps nearly-sorted inputs.
- Selection sort remains O(n^2) in worst case; the provided optimizations are practical but do not change asymptotic worst-case bounds.
