/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fizzbuzz;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.*;

public class Fizzbuzz {
    private static final String NUMBER = "\\d+";

    public String basic() {
        return IntStream.rangeClosed(1, 20)
                .parallel()
                .mapToObj(i -> i % 15 == 0 ? "fizzbuzz"
                        : i % 3 == 0 ? "fizz"
                        : i % 5 == 0 ? "buzz"
                        : Integer.toString(i))
                .collect(joining(" "));
    }

    public String lucky() {
        return IntStream.rangeClosed(1, 20)
                .parallel()
                .mapToObj(i -> Integer.toString(i).contains("3") ? "lucky" // this is the only change from basic()
                        : i % 15 == 0 ? "fizzbuzz"
                        : i % 3 == 0 ? "fizz"
                        : i % 5 == 0 ? "buzz"
                        : Integer.toString(i))
                .collect(joining(" "));
    }

    public String counter() {
        List<String> fizzBuzzList = IntStream.rangeClosed(1, 20)
                .parallel()
                .mapToObj(i -> Integer.toString(i).contains("3") ? "lucky"
                        : i % 15 == 0 ? "fizzbuzz"
                        : i % 3 == 0 ? "fizz"
                        : i % 5 == 0 ? "buzz"
                        : Integer.toString(i))
                .collect(Collectors.toList());

        Map<String, Long> countMap = fizzBuzzList
                .parallelStream()
                .collect(groupingBy(s -> s.matches(NUMBER) ? "integer" : s, counting()));

        // reports

        String fizzbuzz = fizzBuzzList.parallelStream().collect(joining(" "));

        String counts = countMap.entrySet().parallelStream()
                .map(e -> e.getKey() + ": " + e.getValue())
                .collect(joining("\n"));

        return fizzbuzz + "\n" + counts;
    }
}
