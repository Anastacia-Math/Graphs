import org.apache.commons.lang3.ObjectUtils;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class Algorithms {

    public static int [] Dijkstra(Graph G, int startVertex){
        double [] distances = new double[G.getNumOfVertices()];
        int [] previous = new int [G.getNumOfVertices()];
        for (int i=0; i<G.getNumOfVertices(); i++){
            distances[i]=Double.MAX_VALUE;
            previous[i]=-1;
        }
        distances[startVertex] = 0;
        PriorityQueue <Pair> graphQueue = new PriorityQueue<>(G.getNumOfVertices());
        for (int i=0; i<G.getNumOfVertices(); i++)
            graphQueue.add(new Pair(i,distances[i]));

        while (!graphQueue.isEmpty()) {
            Pair u = graphQueue.poll();
            List <Integer> adjList = G.getAdjList(u.getId());
            for (int i=0; i<adjList.size(); i++) {
                int v = adjList.get(i);
                double edgeW = G.getCoordById(u.getId()).getDistance(G.getCoordById(i));
                if (distances[v]>distances[u.getId()]+edgeW){
                    distances[v] = distances[u.getId()]+edgeW;
                    previous[v] = u.getId();
                    changePriority (graphQueue,v,distances[v]);
                }
            }


        }
        return previous;

    }

    public static void changePriority(PriorityQueue<Pair> queue, int v, double distV){
        queue.remove(new Pair(v));
        queue.add(new Pair(v,distV));
    }


    public static int [] Levit(Graph G, int startVertex) {
        double  [] distances = new double  [G.getNumOfVertices()];
        int [] previous = new int [G.getNumOfVertices()];
        int [] state = new int [G.getNumOfVertices()];

        for (int i=0; i<G.getNumOfVertices(); i++) {
            distances[i]=Double.MAX_VALUE;
            previous[i]=-1;
            state[i] = 2;
        }
        distances[startVertex]=0;
        state[startVertex] = 1;
        LinkedList <Integer> graphQueue = new LinkedList<>();
        graphQueue.addLast(startVertex);

        while (!graphQueue.isEmpty()) {
            int vertex = graphQueue.pollFirst();
            state[vertex] =0;
            List <Integer> adjList = G.getAdjList(vertex);
            for (int i=0; i<adjList.size(); i++) {
                double  dis = distances[vertex] + G.getCoordById(vertex).getDistance(G.getCoordById(adjList.get(i)));
                if (distances[adjList.get(i)]> dis){
                    distances[adjList.get(i)] = dis;
                    if (state[adjList.get(i)]==2){
                        graphQueue.addLast(adjList.get(i));
                    }
                    else if (state[adjList.get(i)]==0)
                        graphQueue.addFirst(adjList.get(i));
                    previous[adjList.get(i)] = vertex;
                    state[adjList.get(i)] = 1;

                }

            }
        }
        return previous;
    }


    public static int [] A_star (Graph G, int startVertex, int finishVertex, int evristicType) {
        double [] distances = new double [G.getNumOfVertices()];
        int [] previous = new int [G.getNumOfVertices()];

        for (int i=0; i<G.getNumOfVertices(); i++){
            distances[i] =Double.MAX_VALUE;
            previous[i]= -1;
        }
        distances[startVertex] = 0;
        HashSet <Integer> U = new HashSet<>();
        PriorityQueue <Integer> Q = new PriorityQueue<Integer>( (u,v)->{
            return Double.compare(distances[u]+countDistance(G.getCoordById(v),G.getCoordById(finishVertex),evristicType),
                    distances[v]+countDistance(G.getCoordById(u),G.getCoordById(finishVertex),evristicType));
        });
        Q.add(startVertex);
        while (!Q.isEmpty()) {
            int cur = Q.poll();
            if (cur==finishVertex)
                return previous;
            U.add(cur);
            List <Integer> adjList = G.getAdjList(cur);
            for (int i=0; i<adjList.size(); i++){
                double dis = distances[cur] + G.getCoordById(cur).getDistance1(G.getCoordById(adjList.get(i)));
                if (U.contains(adjList.get(i)) && dis >=distances[adjList.get(i)])
                    continue;
                if (!U.contains(adjList.get(i)) || dis < distances[adjList.get(i)]){
                    previous[adjList.get(i)] = cur;
                    distances[adjList.get(i)] = dis;
                    if (!Q.contains(adjList.get(i)))
                        Q.add(adjList.get(i));
                }
            }
        }
        return previous;
    }

    public  static double countDistance (Coords first, Coords second, int evristicType) {
       // евклидово расстояние
        if (evristicType == 1) {
            return Math.sqrt( (first.getWigth()-second.getWigth())*(first.getWigth()-second.getWigth())+
                    +(first.getHeight()-second.getHeight())*(first.getHeight()-second.getHeight()));
        }
        // манхэтонновское расстояние
        else if (evristicType == 2){
            return (Math.abs(first.getWigth()-second.getWigth())+Math.abs(first.getHeight()-second.getHeight()));
        }
        // расстояние Чебышёва
        else if (evristicType == 3){
            return  Math.max(Math.abs(first.getWigth()-first.getHeight()),
                    Math.abs(second.getWigth()-second.getHeight()));

        }
        else return 0.0;

    }
}