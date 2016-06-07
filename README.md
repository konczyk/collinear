# Pattern recognition

Recognize line patterns in a given set of points.

## Task

Given set of n distinct points in the place, find every (maximal) line segment
that connects a subset of 4 or more of the points.

## Implementation constraints
- Fixed public API for `Point`, `BruteCollinearPoints` and `FastCollinearPoints`
- Running time of `BruteCollinearPoints` should be n<sup>4</sup> in the worst
case and it should use space proportional to n plus the number of line
segments returned. For simplicity, `BruteCollinearPoints` handles only input
with no more than 4 collinear points.
- Running time of `FastCollinearPoints` should be n<sup>2</sup>log n in the
worst case and it should use space proportional to n plus the number of line
segments returned
- `Point`, `BruteCollinearPoints` and `FastCollinearPoints` should not call
library functions except those in `java.lang` and `java.util`

## Sample client

Build project:

    $ ./gradlew assemble

Client options:

    $ java -cp build/libs/collinear.jar Patterns -h

Print line segments with 4 or more collinear points using data read from the
input stream and the default fast algorithm:

    $ cat data/collinear.txt | java -cp build/libs/collinear.jar Patterns -

    (12000, 0) -> (0, 12000)
    (1000, 1000) -> (10000, 28000)

Print line segments with exactly 4 collinear points using data read from the
input stream and the brute-force algorithm:

    $ cat data/collinear.txt | java -cp build/libs/collinear.jar Patterns -b -

    (12000, 0) -> (0, 12000)
    (1000, 1000) -> (10000, 28000)

Print line segments with 4 or more collinear points using data of 6000 randomly
generated unique points on a 32768x32768 plane and the default fast algorithm:

    $ java -cp build/libs/collinear.jar Patterns -n 6000

    (18186, 633) -> (11185, 21636)
    (2814, 13642) -> (21037, 13642)
    (9123, 20815) -> (29749, 20815)
    (8736, 29620) -> (31253, 29620)

Visualize line segments with 4 or more collinear points using data of 6000
randomly generated unique points on a 32768x32768 plane (scaled down to 600x600
for display) and the default fast algorithm
([see sample animation](data/visualizer.gif?raw=true)):

    $ java -cp build/libs/collinear.jar Patterns -n 6000 -g

    (8967, 7) -> (30571, 32413)
    (1442, 9582) -> (25792, 9582)
    (32178, 16524) -> (11010, 19548)
    (15814, 17245) -> (29334, 20625)
    (4514, 29252) -> (31359, 29252)
    (6850, 29850) -> (20870, 29850)
