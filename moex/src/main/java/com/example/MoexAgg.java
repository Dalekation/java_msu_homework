package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MoexAgg {
  // columns:
  // TRADENO TRADETIME SECBOARD SECCODE PRICE VOLUME ACCRUEDINT YIELD VALUE
  // task:
  // SECBOARD - TQBR or FQBR
  // for each SECCODE open and close price, diff
  // 1) top 10 by growth and top 10 by fall
  // 2) for each of them diff in %, amt and volume in RUB

  static final String file_path = "src/main/java/com/example/trades.txt";
  static final String file_path_result = "src/test/java/com/example/result.txt";

  public static int find_ind(String[] arr, String val) {
    for (int i = 0; i < arr.length; i++) {
      if (arr[i].equals(val)) return i;
    }
    return -1;
  }

  public static void main(String[] args) {
    try {
      var reader = new FileReader(file_path);
      var bufferedReader = new BufferedReader(reader);
      String line = bufferedReader.readLine();
      var header = line.split("\t");

      var str_comp =
          new Comparator<String[]>() {
            @Override
            public int compare(String[] p1, String[] p2) {
              return p1[find_ind(header, "TRADENO")].compareTo(p2[find_ind(header, "TRADENO")]);
            }
          };

      // as time repeats in different trades
      // it is supposed that TRADENO is incremental
      // and we can get first and last trades by TRADENO

      List<String[]> result =
          bufferedReader.lines().map(stream_line -> stream_line.split("\t"))
              .filter(
                  input_line ->
                      input_line[find_ind(header, "SECBOARD")].equals("TQBR")
                          || input_line[find_ind(header, "SECBOARD")].equals("FQBR"))
              .collect(Collectors.groupingBy(a -> a[find_ind(header, "SECCODE")])).values().stream()
              .map(
                  e ->
                      new String[] {
                        e.get(0)[find_ind(header, "SECBOARD")],
                        e.get(0)[find_ind(header, "SECCODE")],
                        // total volume (amount)
                        String.valueOf(
                            e.stream()
                                .mapToDouble(a -> Double.parseDouble(a[find_ind(header, "VOLUME")]))
                                .sum()),
                        // total volume (money)
                        String.valueOf(
                            e.stream()
                                .mapToDouble(a -> Double.parseDouble(a[find_ind(header, "VALUE")]))
                                .sum()),
                        // close price
                        // e.stream().collect(Collectors.maxBy(str_comp)).get()[find_ind(header,
                        // "PRICE")],
                        // open price
                        // e.stream().collect(Collectors.minBy(str_comp)).get()[find_ind(header,
                        // "PRICE")],
                        // relative difference in perc
                        String.valueOf(
                            100
                                * (Double.parseDouble(
                                        e.stream()
                                            .collect(Collectors.maxBy(str_comp))
                                            .get()[find_ind(header, "PRICE")])
                                    - Double.parseDouble(
                                        e.stream()
                                            .collect(Collectors.minBy(str_comp))
                                            .get()[find_ind(header, "PRICE")]))
                                / Double.parseDouble(
                                    e.stream()
                                        .collect(Collectors.minBy(str_comp))
                                        .get()[find_ind(header, "PRICE")]))
                      })
              .sorted(
                  (o1, o2) ->
                      Double.compare(
                          Double.parseDouble(o1[o1.length - 1]),
                          Double.parseDouble(o2[o1.length - 1])))
              .collect(Collectors.toList());

      bufferedReader.close();

      String[] column_names = {
        "SECBOARD", "SECCODE", "TOTAL_TRADES_AMOUNT", "TOTAL_TRADES_VOLUME", "DIFF_IN_PERC"
      };
      FileWriter fileWriter = new FileWriter(file_path_result);
      PrintWriter printWriter = new PrintWriter(fileWriter);
      int top = 10;
      int slide = 2;

      printWriter.println("TOP BY FALL");
      printWriter.println(String.join(" ".repeat(slide), column_names));
      for (int i = 0; i < top; i++) {
        var tmp_res = result.get(i);
        for (int j = 0; j < tmp_res.length; j++) {
          if (j < 2) {
            printWriter.printf(
                "%s" + " ".repeat(column_names[j].length() - tmp_res[j].length() + slide),
                tmp_res[j]);
          } else {
            String result_num = String.format("%.2f", Double.parseDouble(tmp_res[j]));
            printWriter.printf(
                "%s" + " ".repeat(column_names[j].length() - result_num.length() + slide),
                result_num);
          }
        }
        printWriter.println("");
      }

      printWriter.println("\nTOP BY GROWTH");
      printWriter.println(String.join(" ".repeat(slide), column_names));
      for (int i = result.size() - 1; i > result.size() - top - 1; i--) {
        var tmp_res = result.get(i);
        for (int j = 0; j < tmp_res.length; j++) {
          if (j < 2) {
            printWriter.printf(
                "%s" + " ".repeat(column_names[j].length() - tmp_res[j].length() + slide),
                tmp_res[j]);
          } else {
            String result_num = String.format("%.2f", Double.parseDouble(tmp_res[j]));
            printWriter.printf(
                "%s" + " ".repeat(column_names[j].length() - result_num.length() + slide),
                result_num);
          }
        }
        printWriter.println("");
      }

      printWriter.close();

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
