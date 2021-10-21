package model;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Supplier;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Point.LocalExtreme;
import model.Point.Orientation;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task implements IntersectingPointCalculator {

  private Integer id;
  private List<Point> input = Lists.newArrayList();
  private Point minimum;
  private Point maximum;
  private Point lowestPositiveLocalMinimum;
  private Point highestPositiveLocalMaximum;
  private Boolean isCore;
  private List<Point> core = Lists.newLinkedList();

  public Task(Integer id, List<Point> input) {
    this.id = id;
    this.input = input;
  }

  public void run() {

    try {
      Collections.reverse(this.input);
      this.validateInput();
      for (int i = 0; i < this.input.size(); i++) {
        Point first = i == 0 ? this.input.get(this.input.size() - 1) : this.input.get(i - 1);
        Point second = this.input.get(i);
        Point third = i == (this.input.size() - 1) ? this.input.get(0) : this.input.get(i + 1);
        this.verifyExtreme(first, second, third);
        this.verifyOrientation(first, second, third);
      }
      this.minimum = this.input.stream().min(Comparator.comparing(Point::getY)).orElseThrow(
          (Supplier<Throwable>) () -> new Exception("Nie można znaleźć minimalny punkt figury"));
      this.maximum = this.input.stream().max(Comparator.comparing(Point::getY)).orElseThrow(
          (Supplier<Throwable>) () -> new Exception("Nie można znaleźć maksymalny punkt figury"));
      this.lowestPositiveLocalMinimum =
          this.input.stream()
                    .filter(point -> Orientation.POSITIVE.equals(point.getOrientation())
                        && LocalExtreme.LOCAL_MINIMUM.equals(point.getLocalExtreme()))
                    .min(Comparator.comparing(Point::getY))
                    .orElse(this.maximum);
      this.highestPositiveLocalMaximum =
          this.input.stream()
                    .filter(point -> Orientation.POSITIVE.equals(point.getOrientation())
                        && LocalExtreme.LOCAL_MAXIMUM.equals(point.getLocalExtreme()))
                    .max(Comparator.comparing(Point::getY))
                    .orElse(this.minimum);
      this.verifyCoreExistence();
      Collections.reverse(this.input);
      this.calculateCorePoints();
      this.printResults();
    } catch (Throwable exception) {
      System.out.println(this.id);
      System.out.println(exception.getMessage());
      System.out.println(System.lineSeparator());
    }
  }

  public void validateInput() throws Exception {

    if (this.input.size() < 3) {
      throw new Exception("Niewystarczająca ilość wierzchołków.");
    }
  }

  public void verifyExtreme(
      Point first,
      Point second,
      Point third
  ) {

    if (isLocalMinimum(first, second, third)) {
      second.setLocalExtreme(LocalExtreme.LOCAL_MINIMUM);
    } else if (isLocalMaximum(first, second, third)) {
      second.setLocalExtreme(LocalExtreme.LOCAL_MAXIMUM);
    } else {
      second.setLocalExtreme(LocalExtreme.NONE);
    }
  }

  public void verifyOrientation(
      Point first,
      Point second,
      Point third
  ) {

    Integer result = (second.getY() - first.getY())
        * (third.getX() - second.getX())
        - (second.getX() - first.getX())
        * (third.getY() - second.getY());
    if (result > 0) {
      second.setOrientation(Orientation.POSITIVE);
    } else if (result < 0) {
      second.setOrientation(Orientation.NEGATIVE);
    } else {
      second.setOrientation(Orientation.NEUTRAL);
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

    if (this.highestPositiveLocalMaximum.getY() > this.lowestPositiveLocalMinimum.getY()) {
      this.isCore = Boolean.FALSE;
    } else {
      this.isCore = Boolean.TRUE;
    }
  }

  public void printResults() {

    System.out.println(this.id);
    System.out.println(String.format("Posiada jądro: %s", this.core));
    System.out.println(String.format("yLocalMin= %s", this.lowestPositiveLocalMinimum.getY()));
    System.out.println(String.format("yLocalMax= %s", this.highestPositiveLocalMaximum.getY()));
    System.out.println(System.lineSeparator());
  }

  public void calculateCorePoints() {

    for (int i = 0; i < this.input.size(); i++) {
      Point first = this.input.get(i);
      Point second = i == this.input.size() - 1 ? this.input.get(0) : this.input.get(i + 1);
      Boolean isTopRightCoreCornerIntersection = this.arePointsIntersectingCoreVerticalLimit(
          first,
          second,
          this.lowestPositiveLocalMinimum
      );
      if (isTopRightCoreCornerIntersection) {
        Point topRightCornerPoint = this.calculateIntersectingPoint(
            first,
            second,
            this.lowestPositiveLocalMinimum.getY()
        );
        this.core.add(topRightCornerPoint);
        continue;
      }
      Boolean isBottomRightCorner = this.arePointsIntersectingCoreVerticalLimit(
          first,
          second,
          this.highestPositiveLocalMaximum
      );
      if (isBottomRightCorner) {
        Point bottomRightCornerPoint = this.calculateIntersectingPoint(
            first,
            second,
            this.highestPositiveLocalMaximum.getY()
        );
        if (first.getY() <= this.lowestPositiveLocalMinimum.getY()
            && first.getY() >= this.highestPositiveLocalMaximum.getY()) {
          this.core.add(first);
        }
        this.core.add(bottomRightCornerPoint);
        continue;
      }
      Boolean isBottomLeftCorner = this.arePointsIntersectingCoreVerticalLimit(
          second,
          first,
          this.highestPositiveLocalMaximum
      );
      if (isBottomLeftCorner) {
        Point bottomLeftCornerPoint = this.calculateIntersectingPoint(
            second,
            first,
            this.highestPositiveLocalMaximum.getY()
        );
        this.core.add(bottomLeftCornerPoint);
        continue;
      }
      Boolean isTopLeftCoreCornerIntersection = this.arePointsIntersectingCoreVerticalLimit(
          second,
          first,
          this.lowestPositiveLocalMinimum
      );
      if (isTopLeftCoreCornerIntersection) {
        Point topLeftCornerPoint = this.calculateIntersectingPoint(
            second,
            first,
            this.lowestPositiveLocalMinimum.getY()
        );
        if (first.getY() <= this.lowestPositiveLocalMinimum.getY()
            && first.getY() >= this.highestPositiveLocalMaximum.getY()) {
          this.core.add(first);
        }
        this.core.add(topLeftCornerPoint);
        continue;
      }
      Boolean isInCore = this.isPointInCore(
          first,
          this.lowestPositiveLocalMinimum.getY(),
          this.highestPositiveLocalMaximum.getY()
      );
      if (isInCore) {
        this.core.add(first);
        continue;
      }
    }
  }

}

// Funkcja signum z labow?
//  public void sgn(
//      Point first,
//      Point second,
//      Point third
//  ) {
//
//    if (isLocalMinimum(first, second, third)) {
//      second.setLocalExtreme(LocalExtreme.LOCAL_MINIMUM);
//      this.localMinimums.add(second);
//      if (second.getX() < first.getX() && second.getX() > third.getX()) {
//        second.setOrientation(Orientation.UP);
//        this.upMinimums.add(second);
//      } else if (second.getX() > first.getX() && second.getX() < third.getX()) {
//        second.setOrientation(Orientation.DOWN);
//      }
//    } else if (isLocalMaximum(first, second, third)) {
//      second.setLocalExtreme(LocalExtreme.LOCAL_MAXIMUM);
//      this.localMaximums.add(second);
//      if (second.getX() < first.getX() && second.getX() > third.getX()) {
//        second.setOrientation(Orientation.UP);
//      } else if (second.getX() > first.getX() && second.getX() < third.getX()) {
//        second.setOrientation(Orientation.DOWN);
//        this.downMaximums.add(second);
//      }
//    } else {
//      second.setOrientation(Orientation.NEUTRAL);
//      second.setLocalExtreme(LocalExtreme.NONE);
//    }
//  }

//  public void run() {
//
//    Collections.reverse(this.input);
//    for (int i = 0; i < this.input.size(); i++) {
//      Point first = i == 0 ? this.input.get(this.input.size() - 1) : this.input.get(i - 1);
//      Point second = this.input.get(i);
//      Point third = i == (this.input.size() - 1) ? this.input.get(0) : this.input.get(i + 1);
//      this.sgn(first, second, third);
//    }
//    this.minimum = this.input.stream().min(Comparator.comparing(Point::getY)).orElseThrow();
//    this.maximum = this.input.stream().max(Comparator.comparing(Point::getY)).orElseThrow();
//    this.lowestUpLocalMinimum =
//        this.upMinimums.stream().min(Comparator.comparing(Point::getY)).orElseThrow();
//    this.highestDownLocalMaximum =
//        this.downMaximums.stream().max(Comparator.comparing(Point::getY)).orElseThrow();
//    this.verifyCoreExistence();
//    this.printResults();
//  }