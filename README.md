# Pattern recognition

Recognize line patterns in a given set of points.

## Task

Given set of n distinct points in the place, find every (maximal) line segment
that connects a subset of 4 or more of the points.

## Implementation constraints
- Fixed public API for `Point`, `BruteCollinearPoints` and `FastCollinearPoints`
- Running time of `BruteCollinearPoints` should be n<sup>4</sup> in the worst
case and it should use space proportional to n plus the number of line
segments returned
- Running time of `FastCollinearPoints` should be n<sup>2</sup>log n in the
worst case and it should use space proportional to n plus the number of line
segments returned
- `Point`, `BruteCollinearPoints` and `FastCollinearPoints` should not call
library functions except those in `java.lang` and `java.util`

## Examples

Build project:

    $ ./gradlew assemble
