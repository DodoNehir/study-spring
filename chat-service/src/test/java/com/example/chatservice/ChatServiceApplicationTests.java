package com.example.chatservice;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatServiceApplicationTests {

  //    System.out.println(LongStream.iterate(x, i -> i + x).limit(n).toArray());
  @Test
  public void solution() {
    String s = "one4seveneight";

    String replaced = s.replaceAll("zero", "0")
        .replaceAll("one", "1")
        .replaceAll("two", "2")
        .replaceAll("three", "3")
        .replaceAll("four", "4")
        .replaceAll("five", "5")
        .replaceAll("six", "6")
        .replaceAll("seven", "7")
        .replaceAll("eight", "8")
        .replaceAll("nine", "9");

//    Pattern pattern = Pattern.compile("(zero|one|two|three|four|five|six|seven|eight|nine)");
//    Matcher matcher = pattern.matcher(s);
//    while (matcher.find()) {
//      switch (matcher.group()) {
//        case "zero" -> s.replaceAll("zero", "0");
//        case "one" -> s.replaceAll("one", "1");
//        case "two" -> s.replaceAll("two", "2");
//        case "three" -> s.replaceAll("three", "3");
//        case "four" -> s.replaceAll("four", "4");
//        case "five" -> s.replaceAll("five", "5");
//        case "six" -> s.replaceAll("six", "6");
//        case "seven" -> s.replaceAll("seven", "7");
//        case "eight" -> s.replaceAll("eight", "8");
//        case "nine" -> s.replaceAll("nine", "9");
//      }
//    }

    System.out.println(replaced);

  }


}
