package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Point {

  private Integer x;

  private Integer y;

  private Orientation orientation;

  private LocalExtreme localExtreme;

  public Point(Integer x, Integer y) {
    this.x = x;
    this.y = y;
  }

  public static enum Orientation {
    UP,
    DOWN,
    NEUTRAL;
  }

  public static enum LocalExtreme {
    LOCAL_MINIMUM,
    LOCAL_MAXIMUM,
    NONE;
  }

}
