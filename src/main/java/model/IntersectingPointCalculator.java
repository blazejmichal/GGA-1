package model;

public interface IntersectingPointCalculator {

  public default Boolean isPointInCore(
      Point point,
      Integer topYLimit,
      Integer bottomYLimit
  ) {

    Boolean condition1 = point.getY() <= topYLimit;
    Boolean condition2 = point.getY() >= bottomYLimit;
    Boolean result = condition1 && condition2;

    return result;
  }

  public default boolean arePointsIntersectingCoreVerticalLimit(
      Point first,
      Point second,
      Point coreLimit
  ) {

    Boolean condition1 = first.getY() > coreLimit.getY();
    Boolean condition2 = second.getY() < coreLimit.getY();
    Boolean result = condition1 && condition2;

    return result;
  }

  public default Point calculateIntersectingPoint(
      Point first,
      Point second,
      Integer yOfMiddlePoint
  ) {

    int a = second.getY() - first.getY();
    int b = first.getX() - second.getX();
    int c = a * (first.getX()) + b * (first.getY());
    int xOfMiddlePoint;
    if (b < 0) {
      xOfMiddlePoint = (c + b * yOfMiddlePoint) / a;
    } else {
      xOfMiddlePoint = (c - b * yOfMiddlePoint) / a;
    }
    Point middlePoint = new Point(xOfMiddlePoint, yOfMiddlePoint);

    return middlePoint;
  }

}
