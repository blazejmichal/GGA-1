package model;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Point.LocalExtreme;
import model.Point.Orientation;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {

  private Integer id;
  private List<Point> input = Lists.newArrayList();
  private List<Point> upMinimums = Lists.newLinkedList();
  private List<Point> downMaximums = Lists.newLinkedList();
  private Point minimum;
  private Point maximum;
  private Point lowestUpLocalMinimum;
  private Point highestDownLocalMaximum;
  private Boolean core;

  public Task(Integer id, List<Point> input) {
    this.id = id;
    this.input = input;
  }

  public void run() {

    Collections.reverse(this.input);
    for (int i = 0; i < this.input.size(); i++) {
      Point first = i == 0 ? this.input.get(this.input.size() - 1) : this.input.get(i - 1);
      Point second = this.input.get(i);
      Point third = i == (this.input.size() - 1) ? this.input.get(0) : this.input.get(i + 1);
      this.sgn(first, second, third);
    }
    this.minimum = this.input.stream().min(Comparator.comparing(Point::getY)).orElseThrow();
    this.maximum = this.input.stream().max(Comparator.comparing(Point::getY)).orElseThrow();
    this.lowestUpLocalMinimum =
        this.upMinimums.stream().min(Comparator.comparing(Point::getY)).orElseThrow();
    this.highestDownLocalMaximum =
        this.downMaximums.stream().max(Comparator.comparing(Point::getY)).orElseThrow();
    this.verifyCoreExistence();
    this.printResults();
  }

  // Funkcja signum z labow?
  public void sgn(
      Point first,
      Point second,
      Point third
  ) {

    if (isLocalMinimum(first, second, third)) {
      second.setLocalExtreme(LocalExtreme.MINIMUM);
      if (second.getX() < first.getX() && second.getX() > third.getX()) {
        second.setOrientation(Orientation.UP);
        this.upMinimums.add(second);
      } else if (second.getX() > first.getX() && second.getX() < third.getX()) {
        second.setOrientation(Orientation.DOWN);
      }
    } else if (isLocalMaximum(first, second, third)) {
      second.setLocalExtreme(LocalExtreme.MAXIMUM);
      if (second.getX() < first.getX() && second.getX() > third.getX()) {
        second.setOrientation(Orientation.UP);
      } else if (second.getX() > first.getX() && second.getX() < third.getX()) {
        second.setOrientation(Orientation.DOWN);
        this.downMaximums.add(second);
      }
    } else {
      second.setOrientation(Orientation.NEUTRAL);
      second.setLocalExtreme(LocalExtreme.NONE);
    }
  }

  public boolean isLocalMinimum(Point first, Point second, Point third) {

    boolean result = second.getY() < first.getY() && second.getY() < third.getY();

    return result;
  }

  public boolean isLocalMaximum(Point first, Point second, Point third) {

    boolean result = second.getY() > first.getY() && second.getY() > third.getY();

    return result;
  }

  public void verifyCoreExistence() {

    if (this.highestDownLocalMaximum.getY() > this.lowestUpLocalMinimum.getY()) {
      this.core = Boolean.FALSE;
    } else {
      this.core = Boolean.TRUE;
    }
  }

  public void printResults() {

    System.out.println(this.id);
    System.out.println(String.format("Posiada jądro: %s", this.core));
    System.out.println(String.format("yLocalMin= %s", this.lowestUpLocalMinimum.getY()));
    System.out.println(String.format("yLocalMax= %s", this.highestDownLocalMaximum.getY()));
    System.out.println(System.lineSeparator());
  }

}
