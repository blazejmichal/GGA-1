import com.google.common.collect.Lists;
import model.Point;
import model.Task;

public class Main {

    public static void main(String[] args) {

        Task task1 = new Task(
                1,
                Lists.newArrayList(
                        new Point(1, 1),
                        new Point(2, 2),
                        new Point(3, 1),
                        new Point(3, 4),
                        new Point(2, 3),
                        new Point(1, 4)
                )
        );
        task1.run();

        Task task2 = new Task(
                2,
                Lists.newArrayList(
                        new Point(1, 1),
                        new Point(2, 2),
                        new Point(3, 2),
                        new Point(4, 1),
                        new Point(4, 4),
                        new Point(3, 4),
                        new Point(2, 3),
                        new Point(1, 4)
                )
        );
        task2.run();

        Task task3 = new Task(
                3,
                Lists.newArrayList(
                        new Point(1, 1),
                        new Point(3, 1),
                        new Point(3, 2),
                        new Point(4, 2),
                        new Point(4, 4),
                        new Point(3, 4),
                        new Point(3, 3),
                        new Point(2, 3),
                        new Point(2, 2),
                        new Point(1, 2)
                )
        );
        task3.run();

//    Task task = new Task(
//        0,
//        Lists.newArrayList(
//            new Point(2, 0),
//            new Point(0, 10),
//            new Point(5, 7),
//            new Point(10, 11),
//            new Point(15, 3),
//            new Point(20, 11),
//            new Point(18, 0),
//            new Point(20, -11),
//            new Point(15, -7),
//            new Point(10, -11),
//            new Point(5, -3),
//            new Point(0, -10)
//        )
//    );
//    task.run();
//
//    Task task0 = new Task(
//        1,
//        Lists.newArrayList(
//            new Point(-2, 0),
//            new Point(0, 10),
//            new Point(5, 7),
//            new Point(10, 11),
//            new Point(15, 3),
//            new Point(20, 11),
//            new Point(25, 0),
//            new Point(20, -11),
//            new Point(15, -7),
//            new Point(10, -11),
//            new Point(5, -3),
//            new Point(0, -10)
//        )
//    );
//    task0.run();
//
//    Task task1 = new Task(
//        0,
//        Lists.newArrayList(
//            new Point(0, 10),
//            new Point(5, 7),
//            new Point(15, 3),
//            new Point(20, 11),
//            new Point(20, -11),
//            new Point(15, -7),
//            new Point(5, -3),
//            new Point(0, -10)
//        )
//    );
//    task1.run();
//
//    Task task2 = new Task(
//        0,
//        Lists.newArrayList(
//            new Point(0, 10),
//            new Point(5, 10),
//            new Point(5, -10),
//            new Point(0, -10)
//        )
//    );
//    task2.run();
//
//    Task task3 = new Task(
//        0,
//        Lists.newArrayList(
//            new Point(0, 10),
//            new Point(5, 7),
//            new Point(15, -5),
//            new Point(20, 11),
//            new Point(20, -11),
//            new Point(15, -7),
//            new Point(5, -3),
//            new Point(0, -10)
//        )
//    );
//    task3.run();
    }

}
