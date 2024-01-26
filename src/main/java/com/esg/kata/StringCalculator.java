package com.esg.kata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Code Kata - StringCalculator.
 * 
 * @author István Rátkai (Selindek)
 *
 */
public class StringCalculator {

  public int add(String numbers) {
    if(numbers==null) {
      return 0;
    }
    var data = numbers;
    var delimiters = new ArrayList<String>();
    delimiters.add("\n");
    
    if(numbers.startsWith("//")) {
      // custom delimiters
      int nl = numbers.indexOf("\n");
      data = numbers.substring(nl+1);
      if(nl>2) {
        var custom = numbers.substring(2,nl);
        if(custom.length()==1) {
          delimiters.add(custom);
        } else if(custom.startsWith("[") && custom.endsWith("]")){
          var customDelimiters = Arrays.asList(custom.split("\\]\\["));
          for( String dl: customDelimiters) {
            if(dl.length()>0) {
              delimiters.add(dl.replace("|", "\\|"));
            }
          }
        }
      } else {
        // invalid custom delimiter -> add default
        delimiters.add(",");
      }
    } else {
      // no custom delimiters -> add default
      delimiters.add(",");
    }
    
    var delimiter = delimiters.stream().collect(Collectors.joining("|"));
    
    var arguments = Arrays.asList(data.split(delimiter));
    
    var negatives = new ArrayList<String>();
    var sum = arguments.stream().mapToInt(a->{
      try {
        int value = Integer.parseInt(a);
        if(value>1000) {
          value = 0;
        } else if(value<0) {
          negatives.add(a);
        }
        return value;
      } catch (NumberFormatException ex) {
        return 0;
      }
    }).sum();
    
    if(!negatives.isEmpty()) {
      throw new IllegalArgumentException("Negatives not allowed: "
          +negatives.stream().collect(Collectors.joining(",")));
    }
    
    return sum;
  }
  
}
