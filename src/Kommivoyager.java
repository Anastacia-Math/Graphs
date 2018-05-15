import java.util.*;

public class Kommivoyager {

    public static HashMap<Integer, Integer> matchRestr;
    public static int [][] NearestNeighbour(Graph G, List<Integer> restIDs, Coords storage) {
        List<Integer> notVisited = new ArrayList<>();
        matchRestr = new HashMap<>();
        int [][] resultWays = new int [11][];


        for (int i = 0; i < restIDs.size(); i++) {
            notVisited.add(restIDs.get(i));
            matchRestr.put(restIDs.get(i),i+1);
        }


        HashMap<List<Integer>, List<Integer>> fastestWays = new HashMap<>();

        double[][] routes = new double[11][11];
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
           // System.out.println(fastestWays.get(startEnd));

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
               // System.out.println(fastestWays.get(startEnd));
                fastestWays.put(endStart,way);
            }

        }
        //for (int i=0; i<11; i++) {
          //  for (int j = 0; j < 11; j++)
            //    System.out.println(routes[i][j] + " ");
            //System.out.println();
        //}
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





        return resultWays;
    }

}
