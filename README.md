## Manacher Algorithm implementation in Scala


You will find two different scala implementations of [Manacher Algorithm](https://en.wikipedia.org/wiki/Longest_palindromic_substring#Manacher.27s_algorithm) here. The algorithm is a building block for a problem of counting number of all palindromic substrings in given string.


The aformentioned solutions list:

- [imperative implementation](https://github.com/int8/Manacher-Algorithm-in-Scala/blob/master/src/main/scala/solvers/ImperativeLinear.scala) that does not differ much from those found online (with vars, Arrays while loops)

- [functional implementation](https://github.com/int8/Manacher-Algorithm-in-Scala/blob/master/src/main/scala/solvers/FunctionalLinearImmutable.scala) with immutable data structures (using Vectors, values and tail recursion)


There is also [quadratic solution](https://github.com/int8/Manacher-Algorithm-in-Scala/blob/master/src/main/scala/solvers/Quadratic.scala) added as a sanity check  


---
**Remark about complexity**

Interestingly, functional implementation suffers from Vectors' operators overheads. As a result for some very random sequences quadratic may outperform linear solution (especially when there is very small number of short palindromic substrings in a string).

Let's try to explain why:

[Functional implementation](https://github.com/int8/Manacher-Algorithm-in-Scala/blob/master/src/main/scala/solvers/FunctionalLinearImmutable.scala) uses Vector to store centers and palindromic substrings radiuses at these centers. Assuming ```K``` being maximum length of a palindromic substring and ```N``` being size of the input sentence and ```K << N```, interesting thing happens:

At the very end of search for palindromic substrings when we reach index close to ```N```, if palindromic substrings are short - we will request very recent indices (non siginificantly lower than ```N```), therefore each lookup for a mirror palindrome length (```minLength```) within main palindrome has complexity ```~ log32(N)```. After that lookup we perform additional lookups while expanding the palindrome itself, again complexity of this operation is ```~log32(N)```. For some pairs ```(N,K)``` then, it may result in complexity being worse than naive quadratic. For a pair: ```N = 10^6``` (```log32(10^6) =~ 4```), ```K = 4``` at each iteration everytime we access mirror value (+values while expanding palindrome) we in fact perform ```~4``` operations instead of one. This overhead is critical and leads to quadratic solution being better. I think, it is safe bet to claim that functional linear solution wins whenever there is lots of palindromic substrings + they are much longer than ```log32(N)```.

This overhead does not hold for  [imperative implementation](https://github.com/int8/Manacher-Algorithm-in-Scala/blob/master/src/main/scala/solvers/ImperativeLinear.scala) where mutable arrays are used for centers/palindromes storage with constant access complexity ```O(1)```


---

To run main app
```
sbt run
```

To run tests
```
sbt test
```


To generate (still poor) documentation with scaladocs
```
sbt doc
```

Perf tests:
----------------

I've made some perf benchmarking (not best due to jvm :( ) but cache-misses should be accurate:

```
       perf stat -e task-clock,cycles,instructions,cache-references,cache-misses scala  /Manacher-Algorithm-in-Scala/target/scala-2.12/palindromes_2.12-0.1.0.jar quadratic
```

 Performance counter stats for quadratic':

```
       1763,671845      task-clock (msec)         #    2,029 CPUs utilized          
        5377473460      cycles                    #    3,049 GHz                    
        8206343568      instructions              #    1,53  insn per cycle         
         223135924      cache-references          #  126,518 M/sec                  
          47412156      cache-misses              #   21,248 % of all cache refs    

       0,869382455 seconds time elapsed
```

 Performance counter stats for functional':

```
       3235,989841      task-clock (msec)         #    2,631 CPUs utilized          
        9775014003      cycles                    #    3,021 GHz                    
       12839187724      instructions              #    1,31  insn per cycle         
         252587935      cache-references          #   78,056 M/sec                  
          58124513      cache-misses              #   23,012 % of all cache refs    

       1,229895126 seconds time elapsed
```

Performance counter stats for imperative':

```
       1217,352018      task-clock (msec)         #    1,805 CPUs utilized          
        3691657142      cycles                    #    3,033 GHz                    
        4744865789      instructions              #    1,29  insn per cycle         
         148720517      cache-references          #  122,167 M/sec                  
          24369674      cache-misses              #   16,386 % of all cache refs    

       0,674409035 seconds time elapsed
```
