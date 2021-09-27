Town Route Graph


- Directed graph with weighted wedges.
- implimented in Java.

Input:

Graph: AB5, BC4, CD8, DC8, DE6, AD5, CE2, EB3, AE7
- The distance of the route A-B-C.
- The distance of the route A-D.
- The distance of the route A-D-C.
- The distance of the route A-E-B-C-D.
- The distance of the route A-E-D.
- The number of trips starting at C and ending at C with a maximum of 3 stops.  In the sample data below, there are two such trips: C-D-C (2 stops). and C-E-B-C (3 stops).
- The number of trips starting at A and ending at C with exactly 4 stops.  In the sample data below, there are three such trips: A to C (via B,C,D); A to C (via D,C,D); and A to C (via D,E,B).
- The length of the shortest route (in terms of distance to travel) from A to C.
- The length of the shortest route (in terms of distance to travel) from B to B.
- The number of different routes from C to C with a distance of less than 30.  In the sample data, the trips are: CDC, CEBC, CEBCDC, CDCEBC, CDEBC, CEBCEBC, CEBCEBCEBC.

Output:
#1: 9
#2: 5
#3: 13
#4: 22
#5: NO SUCH ROUTE
#6: 2
#7: 3
#8: 9
#9: 9
#10: 7

