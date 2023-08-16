# Travelling Salesman Solution
Travelling Salesman Problem, solved with a modification of the Traditional Depth First Search

Code has been commented properly and an explaination of the algorithm can be found alongside the Main.java File.

Recommended to be run on Eclipse IDE for Java.

Feel free to optimize and improve code.

# Explaination

The algorithm used is a modification of the Depth-First Search Algorithm, the modification is required due to the fact that 2d arrays are used instead of a graph or binary tree which can be easily traversed.

Firstly data from the text file is read line by line into the "cityDistances" 2d array.
Another 2d array "absDistances" stores the euclidean distance of each city from the origin point (0,0).

Now the DFS Algorithm is ready to be executed.

For each city excluding the starting & ending ones (both being city #1). All possible viable permutations are found. All possible city paths are stored in an "possiblePaths" ArrayList. The shortest path & distance are stored accordingly.

Finally the shortest path and its total distance is stored.
