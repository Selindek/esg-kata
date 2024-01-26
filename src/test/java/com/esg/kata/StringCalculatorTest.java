package com.esg.kata;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Unit tests for {@link StringCalculator}.
 * @author István Rátkai (Selindek)
 *
 */
public class StringCalculatorTest {

  private StringCalculator sc = new StringCalculator();
  
  @Test
  public void testEmptyString() {
    assertThat(sc.add(""), is(0));
  }
  
  @Test
  public void testOneParameter() {
    assertThat(sc.add("1"), is(1));
  }

  @Test
  public void testTwoParameters() {
    assertThat(sc.add("1,2"), is(3));
  }

  @Test
  public void testMoreParameters() {
    assertThat(sc.add("1,2,3"), is(6));
  }

  @Test
  public void testNewlineDelimiter() {
    assertThat(sc.add("1\n2,3"), is(6));
  }

  @Test
  public void testCustomDelimiter() {
    assertThat(sc.add("//;\n1;2"), is(3));
  }

  @Test
  public void testMissingCustomDelimiter() {
    assertThat(sc.add("//\n1,2"), is(3));
  }

  @Test()
  public void testNegative() {
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      var v = sc.add("2,-4,3,-5");
      System.err.println(v);
    });

    String expectedMessage = "Negatives not allowed: -4,-5";
    String actualMessage = exception.getMessage();

    assertTrue(actualMessage.contains(expectedMessage));
  }
  
  @Test
  public void testGreatNumbers() {
    assertThat(sc.add("1001,2"), is(2));
  }

  @Test
  public void testLongDelimiter() {
    assertThat(sc.add("//[|||]\n1|||2|||3"), is(6));
  }
  
  @Test
  public void testMultipleDelimiters() {
    assertThat(sc.add("//[|][%]\n1|2%3"), is(6));
  }

  @Test
  public void testMultipleLongDelimiters() {
    assertThat(sc.add("//[|||][blah]\n1|||2blah3"), is(6));
  }

}
