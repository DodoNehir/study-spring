package com.example.chatservice;

import java.util.Arrays;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChatServiceApplicationTests {

  //    System.out.println(LongStream.iterate(x, i -> i + x).limit(n).toArray());
  @Test
  public long solution(long n) {
    Arrays.sort(String.valueOf(n).toCharArray());
  }

}
