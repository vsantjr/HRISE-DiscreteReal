package br.inpe.cocte.labac.hrise.jhelper.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * The type Weight vectors generator.
 */
public class WeightArrayListsGenerator {

  private final int hparam;

  private final int mparam;

  private final double tparam;

  private final double dparam;

  /**
   * Instantiates a new Weight vectors generator.
   *
   * @param m the mparam
   * @param h the hparam
   * @param d the dparam
   * @param t the tparam
   */
  public WeightArrayListsGenerator(int m, int h, double d, double t) {
    this.mparam = m;
    this.hparam = h;
    this.tparam = t;
    this.dparam = d;
  }

  /**
   * The entry point of application.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    int h = 200;
    int m = 2;
    double t = 1.0;

    Map<String, String> arguments = new HashMap<>();

    for (int i = 0; i < args.length; i += 2) {
      arguments.put(args[i], args[i + 1]);
    }

    if (arguments.get("-hparam") != null) {
      h = Integer.parseInt(arguments.get("-hparam"));
    }
    if (arguments.get("-mparam") != null) {
      m = Integer.parseInt(arguments.get("-mparam"));
    }
    if (arguments.get("-tparam") != null) {
      t = Double.parseDouble(arguments.get("-tparam"));
    }

    double d = 1.0 / (double) h;
    (new WeightArrayListsGenerator(m, h, d, t)).generate();
  }

  /**
   * Generate.
   */
  protected void generate() {
    ArrayList<Integer> count = new ArrayList<>();
    for (int i = 0; i < mparam; ++i) {
      count.add(0);
    }
    for (int j = 1; j < Math.pow(hparam + 1, mparam); ++j) {
      count = update(count);
      int accumulate = 0;
      for (int i : count) {
        accumulate += i;
      }
      if (hparam == accumulate) {
        print(count);
      }
    }
  }

  /**
   * Print.
   *
   * @param v the v
   */
  protected void print(ArrayList<Integer> v) {
    for (int i : v) {
      double value = (1.0 - tparam) / (double) mparam;
      value += tparam * ((double) i) * dparam;
      System.out
          .print(value
              + "\t");
    }
    System.out.println();
  }

  /**
   * Update vector.
   *
   * @param v the v
   * @return the vector
   */
  protected ArrayList<Integer> update(ArrayList<Integer> v) {
    for (int i = 0; i < v.size(); i = ((v.get(i) != 0) ? v.size() : i + 1)) {
      v.set(i, v.get(i) + 1);
      v.set(i, v.get(i) % (hparam + 1));
    }
    return v;
  }

}
