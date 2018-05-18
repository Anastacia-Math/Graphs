import java.util.*;

public class Kommivoyager {

    public static HashMap<Integer, Integer> matchRestr;

    public static double lowerBound =0;
    public static int [][] NearestNeighbour(Graph G, List<Integer> restIDs, Coords storage) {
        double [][] routes = new double [11][11];
        double totalroute = 0;
        HashMap<List<Integer>, List<Integer>> fastestWays = new HashMap<>();
        List<Integer> notVisited = new ArrayList<>();
        matchRestr = new HashMap<>();
        int [][] resultWays = new int [11][];


        for (int i = 0; i < restIDs.size(); i++) {
            notVisited.add(restIDs.get(i));
            matchRestr.put(restIDs.get(i),i+1);
        }
        int storagePoint = G.findNearestVertex(storage);
        int[] res = Algorithms.Levit(G, storagePoint);
        matchRestr.put(storagePoint,0);

        double distance ;

        for (int j=0; j<restIDs.size(); j++) {
            int i = restIDs.get(j);
            distance = 0;
            List <Integer> way = new ArrayList<>();
            List <Integer> startEnd = new ArrayList<>();
            List <Integer> endStart = new ArrayList<>();
            endStart.add(0,storagePoint);
            endStart.add(1, restIDs.get(j));

            startEnd.add(0,restIDs.get(j));
            startEnd.add(1,storagePoint);
            way.add(i);
            while (res[i] != -1) {
                Coords st = G.getCoordById(i);
                Coords fin = G.getCoordById(res[i]);
                distance += Math.sqrt((st.getWigth() - fin.getWigth()) * (st.getWigth() - fin.getWigth()) + (st.getHeight() - fin.getHeight()) * (st.getHeight() - fin.getHeight()));
                i = res[i];
                way.add(i);
            }
            routes[0][j+1] = distance;
            routes[j+1][0] = distance;
            fastestWays.put(startEnd,way);

            fastestWays.put(endStart,way);
        }



        for (int i=0; i<restIDs.size(); i++){
            int current =  restIDs.get(i);
            res = Algorithms.Levit(G,current);
            for (int j=i+1; j<restIDs.size(); j++) {
                int cur = restIDs.get(j);
                distance = 0;
                List <Integer> way = new ArrayList<>();
                List <Integer> startEnd = new ArrayList<>();
                List <Integer> endStart = new ArrayList<>();
                endStart.add(0,cur);
                endStart.add(1,current);
                startEnd.add(0,current);
                startEnd.add(1,cur);
                way.add(cur);
                while (res[cur] != -1) {
                    Coords st = G.getCoordById(cur);
                    Coords fin = G.getCoordById(res[cur]);
                    distance += Math.sqrt((st.getWigth() - fin.getWigth()) * (st.getWigth() - fin.getWigth()) + (st.getHeight() - fin.getHeight()) * (st.getHeight() - fin.getHeight()));
                    cur = res[cur];
                    way.add(cur);
                }
                routes[i+1][j+1] = distance;
                routes [j+1][i+1] = distance;
                fastestWays.put(startEnd,way);

                fastestWays.put(endStart,way);
            }

        }

        int point=storagePoint;
        int end = 0;
        int cnt =0;
        while (!notVisited.isEmpty()) {
            double min = Double.MAX_VALUE;
            for (int ID: notVisited) {
                if (routes[matchRestr.get(point)][matchRestr.get(ID)] < min) {
                    min = routes[matchRestr.get(point)][matchRestr.get(ID)];
                    end = ID;
                }
            }
            totalroute+=min;
            List <Integer> temp = fastestWays.get(Arrays.asList(point,end));
            point = end;
            notVisited.remove(Integer.valueOf(point));
            int [] tempMas = new int [temp.size()];
            for (int i=0; i<temp.size(); i++)
                tempMas[i] = temp.get(i);
            resultWays[cnt++] = tempMas;
        }

        List <Integer > temp = fastestWays.get(Arrays.asList(end,storagePoint));
        int [] tempMas = new int [temp.size()];
        for (int i=0; i<temp.size(); i++)
            tempMas[i] = temp.get(i);
        resultWays[cnt] = tempMas;


        System.out.println(totalroute);

        return resultWays;
    }

    public static  int [][] BranchNBoundMethod (Graph G, List<Integer> restIDs, Coords storage) {
        int [][]resultWays = new int [11][];
        double [][] routes = new double [11][11];
        HashMap<List<Integer>, List<Integer>> fastestWays = new HashMap<>();
        storage.setWigth(storage.getDekart_heigth());
        storage.setHeight(storage.getDekart_width());
        int storagePoint = G.findNearestVertex(storage);
        //System.out.println(storage.getWigth()+ " "+storage.getHeight());
        int[] res = Algorithms.Levit(G, storagePoint);
        HashMap <Integer, Integer> matchingIDs = new HashMap<>();
        matchingIDs.put(0,storagePoint);
        for (int i=0; i<restIDs.size(); i++)
            matchingIDs.put(i+1,restIDs.get(i));

        double distance ;

        for (int j=0; j<restIDs.size(); j++) {
            int i = restIDs.get(j);
            distance = 0;
            List <Integer> way = new ArrayList<>();
            List <Integer> startEnd = new ArrayList<>();
            List <Integer> endStart = new ArrayList<>();
            endStart.add(0,storagePoint);
            endStart.add(1, restIDs.get(j));

            startEnd.add(0,restIDs.get(j));
            startEnd.add(1,storagePoint);
            way.add(i);
            while (res[i] != -1) {
                Coords st = G.getCoordById(i);
                //System.out.println(st.getWigth()+" "+st.getHeight());
                Coords fin = G.getCoordById(res[i]);
                distance += Math.sqrt((st.getWigth() - fin.getWigth()) * (st.getWigth() - fin.getWigth()) + (st.getHeight() - fin.getHeight()) * (st.getHeight() - fin.getHeight()));

                i = res[i];
                way.add(i);
            }
            way.add(i);
            routes[0][j+1] = distance;
            routes[j+1][0] = distance;
            fastestWays.put(startEnd,way);

            fastestWays.put(endStart,way);
        }



        for (int i=0; i<restIDs.size(); i++){
            int current =  restIDs.get(i);
            res = Algorithms.Levit(G,current);
            for (int j=i+1; j<restIDs.size(); j++) {
                int cur = restIDs.get(j);
                distance = 0;
                List <Integer> way = new ArrayList<>();
                List <Integer> startEnd = new ArrayList<>();
                List <Integer> endStart = new ArrayList<>();
                endStart.add(0,cur);
                endStart.add(1,current);
                startEnd.add(0,current);
                startEnd.add(1,cur);
                way.add(cur);
                while (res[cur] != -1) {
                    Coords st = G.getCoordById(cur);
                    Coords fin = G.getCoordById(res[cur]);
                    distance += Math.sqrt((st.getWigth() - fin.getWigth()) * (st.getWigth() - fin.getWigth()) + (st.getHeight() - fin.getHeight()) * (st.getHeight() - fin.getHeight()));
                    cur = res[cur];
                    way.add(cur);
                }
                routes[i+1][j+1] = distance;
                routes [j+1][i+1] = distance;
                fastestWays.put(startEnd,way);

                fastestWays.put(endStart,way);
            }

        }

        /*for (int i=0; i<routes.length; i++) {
            for (int j = 0; j < routes[i].length; j++)
                System.out.print(routes[i][j]+ " ");
            System.out.println();
        }*/
        double [][] copyOfRoutes = new double[routes.length][routes[0].length];
        double [][] matrixOfBounds = new double[routes.length][routes[0].length];
        //System.out.println(storage.getWigth()+" "+storage.getHeight());
        for (int i=0; i<matrixOfBounds.length; i++)
            for (int j=0; j<matrixOfBounds[i].length;j++) {
                if (j==i) matrixOfBounds[i][j] = Double.MIN_VALUE;
                else matrixOfBounds[i][j] = 0;
        }
        //System.out.println(storagePoint);
        List <Integer> notStarted = new ArrayList<>();
        List<Integer> notVisited = new ArrayList<>();
        for (int i = 0; i < restIDs.size(); i++) {
            notVisited.add(restIDs.get(i));
            notStarted.add(restIDs.get(i));
        }
        notStarted.add(storagePoint);
        notVisited.add(storagePoint);
        double [] rowMin ;
        double [] colMin ;
        for (int i=0; i<copyOfRoutes.length; i++)
            for (int j=0; j<copyOfRoutes[i].length; j++) {
               if (j==i) copyOfRoutes[i][j] = Double.MAX_VALUE;
               else  copyOfRoutes[i][j] = routes[i][j];
            }
            Set <Integer> Visited = new HashSet<>();
            Set <Integer> Started = new HashSet<>();

        /*for (int i=0; i<copyOfRoutes.length; i++) {
            for (int j = 0; j < copyOfRoutes[i].length; j++)
                System.out.print(copyOfRoutes[i][j]+" ");
            System.out.println();
        }
        System.out.println();*/
        int cnt = 0;
        while (!notVisited.isEmpty()  ) {
            //System.out.println("непосещенные вершины");
            //for (int el:notVisited)
              //  System.out.print(el+" ");
           // System.out.println();
            //System.out.println(" вершины из которых не выходил путь");
            //for (int el:notStarted)
              //  System.out.print(el+" ");
           /* System.out.println();
            System.out.println("Итерация: "+  cnt);
            System.out.println("Матрица путей до редукции");
            for (int i=0; i<copyOfRoutes.length; i++) {
                for (int j = 0; j < copyOfRoutes[i].length; j++)
                    System.out.print(copyOfRoutes[i][j]+ " ");
                System.out.println();
            }*/


            //cnt++;
            rowMin = rowsMin(copyOfRoutes);
            copyOfRoutes = reductionOfRows(copyOfRoutes,rowMin);
            colMin =  colsMin(copyOfRoutes);

           /*System.out.println();
            for (int i=0; i<rowMin.length; i++) {
                System.out.print(rowMin[i] + " ");
            }
            System.out.println();
            for (int i=0; i<colMin.length; i++) {
                System.out.print(colMin[i] + " ");
            }
            System.out.println(); */
            copyOfRoutes = reductionOfCols(copyOfRoutes,colMin);
            for (int i=0; i<copyOfRoutes.length; i++)
                for (int j=0; j<copyOfRoutes[i].length; j++)
                    if (copyOfRoutes[i][j]==0) {
                        if (Started.contains(matchingIDs.get(j)) && !Visited.contains(matchingIDs.get(i)))
                        {
                            copyOfRoutes[i][j]=Double.MAX_VALUE;
                            matrixOfBounds[i][j] =Double.MIN_VALUE;
                            continue;

                        }
                        matrixOfBounds[i][j] = rowsMin2(copyOfRoutes,i,j)+colsMin2(copyOfRoutes,i,j);
                       // System.out.println(rowsMin2(copyOfRoutes,i,j)+ " "+i+" "+j);
                    }
           /*System.out.println();
            System.out.println("матрица путей после редукции");
            for (int i=0; i<copyOfRoutes.length; i++) {
                for (int j = 0; j < copyOfRoutes[i].length; j++)
                    System.out.print(copyOfRoutes[i][j]+" ");
                System.out.println();
            }
            System.out.println();
            System.out.println("Матрица границ");
            for (int i=0; i<matrixOfBounds.length; i++) {
                for (int j = 0; j < matrixOfBounds[i].length; j++)
                    System.out.print(matrixOfBounds[i][j]+ " ");
                System.out.println();
            }
            System.out.println();*/
            int [] ij = findMatrixMax(matrixOfBounds);
            int m = ij[0];
            int n = ij[1];
           // System.out.println(m+" " + matchingIDs.get(m));
            //System.out.println(n + " "+matchingIDs.get(n));

            List <Integer> foundWay = fastestWays.get(Arrays.asList(matchingIDs.get(m), matchingIDs.get(n)));
            lowerBound+=routes[m][n];
            if (m==n){
                foundWay = fastestWays.get(Arrays.asList(notStarted.get(0), notVisited.get(0)));
                notVisited.remove(0);
                notStarted.remove(0);
            }
            int  [] tempMas = new int [foundWay.size()];
            for (int i=0; i<foundWay.size(); i++)
               tempMas[i] = foundWay.get(i);
            resultWays[cnt++] = tempMas;
            notVisited.remove(matchingIDs.get(m));
            notStarted.remove(matchingIDs.get(n));
            Visited.add(matchingIDs.get(n));
            Started.add(matchingIDs.get(m));
            for (int i=0; i<copyOfRoutes.length; i++) {
                copyOfRoutes[m][i] = Double.MAX_VALUE;
                copyOfRoutes[i][n] = Double.MAX_VALUE;
            }
            copyOfRoutes[n][m] = Double.MAX_VALUE;
            matrixOfBounds = makeNull(matrixOfBounds);

           /* System.out.println("посещенные вершины");
            for (int el:Visited)
                System.out.print(el+" ");
            System.out.println();
            System.out.println(" вершины из которых выходил путь");
            for (int el:Started)
                System.out.print(el+" ");
            System.out.println();*/

        }

        System.out.println(lowerBound);
        return resultWays;
    }

    private static double [] rowsMin (double [][] matrix) {
        double [] columnOfMins = new double [matrix.length];
        double min;

        for (int i=0; i<matrix.length; i++){
            min = matrix[i][0];

            for (int j=0; j<matrix[i].length; j++) {
                if (matrix[i][j] < min) {
                    min = matrix[i][j];
                }
            }
            if (min>1E+06)  {
                columnOfMins[i] = 0;
            }
            else columnOfMins[i] = min;


        }
        return columnOfMins;
    }

    private static double  rowsMin2 (double [][] matrix, int m, int n) {
        double min;
        min = Double.MAX_VALUE;
        for (int i=0; i<matrix.length; i++){
                if (matrix[m][i] < min && i!=n ) {
                    min = matrix[m][i];
                }
        }
        return min;
    }


    private static double [] colsMin (double [][] matrix){
        double [] rowOfMins = new double [matrix[0].length];
        double min;

        for (int i=0; i<matrix.length; i++){
            min = matrix[0][i];
            for (int j=0; j<matrix[i].length; j++){
                if (i!=j) {
                    if (matrix[j][i] < min)
                        min = matrix[j][i];
                    rowOfMins[i] = min;
                    }

            }
            if (min>1E+08) rowOfMins[i]=0;
        }
        return  rowOfMins;

    }

    private static double  colsMin2 (double [][] matrix,  int n,int m){
        double min;
        min = Double.MAX_VALUE;
        for (int i=0; i<matrix.length; i++){
            if (matrix[i][m] < min && i!=n ) {
                min = matrix[i][m];
            }
        }
        return min;


    }

    private static double [][] reductionOfRows(double [][]matrix, double [] rowsMin){
        for (int i=0; i<matrix.length; i++)
            for (int j=0; j<matrix[i].length; j++) {
                matrix[i][j] -= rowsMin[i];
                //lowerBound+=rowsMin[i];
            }
        return matrix;
    }

    private static double [][] reductionOfCols(double [][]matrix, double [] colsMin){
        for (int i=0; i<matrix.length; i++)
            for (int j=0; j<matrix[i].length; j++) {
                matrix[j][i] -= colsMin[i];
                //lowerBound+=colsMin[i];
            }
        return matrix;
    }

    private static int [] findMatrixMax(double[][]matrix) {
        int [] result = new int [2];
        double max = Double.MIN_VALUE;
        for (int i=0; i<matrix.length; i++)
            for (int j=0; j<matrix[i].length; j++)
                if (matrix[i][j] >= max){
                    max = matrix[i][j];
                    result[0] = i;
                    result[1] = j;
                }

         return result;
    }

    private static double [][] makeNull(double [][]matrix){
        for (int i=0; i<matrix.length; i++)
            for (int j=0; j<matrix[i].length; j++)
                matrix[i][j] = Double.MIN_VALUE;
        return matrix;

    }
}





