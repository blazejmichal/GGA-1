package model;

import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import model.Point.Orientation;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Task {

  private List<Point> input = Lists.newArrayList(
      new Point(0, 1),
      new Point(1, 3),
      new Point(1, -1),
      new Point(0, -2)
  );

  public void run() {

    Collections.reverse(this.input);
    List<Point> minimums = Lists.newLinkedList();
    List<Point> maximums = Lists.newLinkedList();
    for (int i = 0; i < this.input.size(); i++) {
      Point first = i == 0 ? this.input.get(this.input.size() - 1) : this.input.get(i - 1);
      Point second = this.input.get(i);
      Point third = i == (this.input.size() - 1) ? this.input.get(0) : this.input.get(i + 1);
      this.verifyOrientation(first, second, third, minimums, maximums);
    }
  }

  public void verifyOrientation(
      Point first,
      Point second,
      Point third,
      List<Point> minimums,
      List<Point> maximums
  ) {

    if (second.getY() < first.getY() && second.getY() < third.getY()) {
      // TODO: co z porownaniem do trzeciego?
      if (second.getX() <= first.getX()) {
        second.setOrientation(Orientation.UP);
        maximums.add(second);
      } else {
        second.setOrientation(Orientation.DOWN);
      }
    } else if (second.getY() > first.getY() && second.getY() > third.getY()) {
      // TODO: co z porownaniem do trzeciego?
      if (second.getX() <= first.getX()) {
        second.setOrientation(Orientation.UP);
      } else {
        second.setOrientation(Orientation.DOWN);
        minimums.add(second);
      }
    }
  }

}
