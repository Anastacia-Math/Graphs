import java.util.List;
import java.util.Random;

public class TestsForAlgorithms {

    public static void test (Graph G, List<Integer> restIDs) {
        double DijkstraAverage = 0, DijkstraMax = Double.MIN_VALUE, DijkstraMin = Double.MAX_VALUE;
        double LevitAverage = 0, LevitMax = Double.MIN_VALUE, LevitMin = Double.MAX_VALUE;
        double AstarAverageEv = 0, AstarMaxEv = Double.MIN_VALUE, AstarMinEv = Double.MAX_VALUE;
        double AstarAverageMh = 0, AstarMaxMh = Double.MIN_VALUE, AstarMinMh = Double.MAX_VALUE;
        double AstarAverageCh = 0, AstarMaxCh = Double.MIN_VALUE, AstarMinCh = Double.MAX_VALUE;

        double [][] carTrip = new double [5][10];
        int [] res;
        double distance = 0;
        for (int i=0; i<100; i++) {
            Random rnd = new Random();
            double x = G.getXmin() + (G.getXmax()-G.getXmin()) * rnd.nextGaussian(),
                    y = G.getYmin() + (G.getYmax()-G.getYmin()) * rnd.nextGaussian();
            int startPoint = G.findNearestVertex(new Coords (x,y));
            //Дейкстра
            double start = System.currentTimeMillis();
            res = Algorithms.Dijkstra(G,startPoint);
            double time = (System.currentTimeMillis() - start)/1000.0;
            DijkstraAverage +=time;
            if (time >DijkstraMax) DijkstraMax = time;
            if (time < DijkstraMin) DijkstraMin = time;

            for (int k=0; k<10; k++) {
                int j = restIDs.get(k);
                distance = 0.0;
                while(res[j]!=-1) {

                    Coords st = G.getCoordById(j);
                    Coords fin = G.getCoordById(res[j]);
                    distance += Math.sqrt((st.getWigth() - fin.getWigth()) * (st.getWigth() - fin.getWigth()) + (st.getHeight() - fin.getHeight()) * (st.getHeight() - fin.getHeight()));
                    j = res[j];
                }
                carTrip[0][k]+=distance;

            }


            //Левит
            start = System.currentTimeMillis();
            res = Algorithms.Levit(G,startPoint);
            time = (System.currentTimeMillis() - start)/1000.0;
            LevitAverage +=time;
            if (time >LevitMax) LevitMax = time;
            if (time < LevitMin) LevitMin = time;
            for (int k=0; k<10; k++) {
                distance = 0.0;
                int j = restIDs.get(k);
                while(res[j]!=-1) {
                    Coords st = G.getCoordById(j);
                    Coords fin = G.getCoordById(res[j]);
                    distance += Math.sqrt((st.getWigth() - fin.getWigth()) * (st.getWigth() - fin.getWigth()) + (st.getHeight() - fin.getHeight()) * (st.getHeight() - fin.getHeight()));
                    j = res[j];
                }
                carTrip[1][k]+=distance;
            }


            time =0;
            //А со звездой с эвристикой Евклида
            int cnt =0;
            for (int el: restIDs) {
                distance = 0.0;
                start = System.currentTimeMillis();
                res = Algorithms.A_star(G,startPoint,el,1);
                time += (System.currentTimeMillis() - start)/1000.0;


                    int j = el;
                    while(res[j]!=-1) {
                        Coords st = G.getCoordById(j);
                        Coords fin = G.getCoordById(res[j]);
                        distance += Math.sqrt((st.getWigth() - fin.getWigth()) * (st.getWigth() - fin.getWigth()) + (st.getHeight() - fin.getHeight()) * (st.getHeight() - fin.getHeight()));
                        j = res[j];
                    }
                    carTrip[2][cnt]+=distance;
                    cnt++;
                }


            AstarAverageEv +=time;
            if (time > AstarMaxEv) AstarMaxEv = time;
            if (time < AstarMinEv) AstarMinEv = time;


            time =0;
            cnt = 0;
            //А со звездой с мантхэттоноской эвристикой
            for (int el: restIDs) {
                distance = 0.0;
                start = System.currentTimeMillis();
                res = Algorithms.A_star(G,startPoint,el,2);
                time += (System.currentTimeMillis() - start)/1000.0;
                int j = el;
                while(res[j]!=-1) {
                    Coords st = G.getCoordById(j);
                    Coords fin = G.getCoordById(res[j]);
                    distance += Math.sqrt((st.getWigth() - fin.getWigth()) * (st.getWigth() - fin.getWigth()) + (st.getHeight() - fin.getHeight()) * (st.getHeight() - fin.getHeight()));
                    j = res[j];
                }
                carTrip[3][cnt]+=distance;
                cnt++;
            }

            AstarAverageMh +=time;
            if (time > AstarMaxMh) AstarMaxMh = time;
            if (time < AstarMinMh) AstarMinMh = time;

            time=0;
            cnt = 0;
            //А со звездой с эвристикой Чебышёва
            for (int el: restIDs) {
                distance = 0.0;
                start = System.currentTimeMillis();
                Algorithms.A_star(G,startPoint,el,1);
                time += (System.currentTimeMillis() - start)/1000.0;

                int j = el;
                while(res[j]!=-1) {
                    Coords st = G.getCoordById(j);
                    Coords fin = G.getCoordById(res[j]);
                    distance += Math.sqrt((st.getWigth() - fin.getWigth()) * (st.getWigth() - fin.getWigth()) + (st.getHeight() - fin.getHeight()) * (st.getHeight() - fin.getHeight()));
                    j = res[j];
                }
                carTrip[4][cnt]+=distance;
                cnt++;

            }
            AstarAverageCh +=time;
            if (time > AstarMaxCh) AstarMaxCh = time;
            if (time < AstarMinCh) AstarMinCh = time;
        }


        System.out.println(" Dijkstra Results:");
        System.out.println("Average: "+ String.valueOf(DijkstraAverage/100));
        System.out.println("Max time: "+String.valueOf(DijkstraMax));
        System.out.println("Min time: " + String.valueOf(DijkstraMin));
        for (int i=0; i<10; i++) {
            System.out.println("Average time to drive to " +i + " restaurant " +String.valueOf(carTrip[0][i]/4000000.0));
        }
        System.out.println("--------------------------------------------------------------------------------------------");

        System.out.println(" Levit Results:");
        System.out.println("Average: "+ String.valueOf(LevitAverage/100));
        System.out.println("Max time: "+String.valueOf(LevitMax));
        System.out.println("Min time: " + String.valueOf(LevitMin));
        for (int i=0; i<10; i++) {
            System.out.println("Average time to drive to " +i + " restaurant " +String.valueOf(carTrip[1][i]/4000000.0));
        }
        System.out.println("--------------------------------------------------------------------------------------------");


        System.out.println(" A_star with Euclidian euristics Results:");
        System.out.println("Average: "+ String.valueOf(AstarAverageEv/100));
        System.out.println("Max time: "+String.valueOf(AstarMaxEv));
        System.out.println("Min time: " + String.valueOf(AstarMinEv));
        for (int i=0; i<10; i++) {
            System.out.println("Average time to drive to " +i + " restaurant " +String.valueOf(carTrip[2][i]/4000000.0));
        }
        System.out.println("--------------------------------------------------------------------------------------------");


        System.out.println(" A_star with Manhattan euristics Results:");
        System.out.println("Average: "+ String.valueOf(AstarAverageMh/100));
        System.out.println("Max time: "+String.valueOf(AstarMaxMh));
        System.out.println("Min time: " + String.valueOf(AstarMinMh));
        for (int i=0; i<10; i++) {
            System.out.println("Average time to drive to " +i + " restaurant " +String.valueOf(carTrip[3][i]/4000000.0));
        }

        System.out.println("--------------------------------------------------------------------------------------------");

        System.out.println(" A_star with Chebyshov euristics Results:");
        System.out.println("Average: "+ String.valueOf(AstarAverageCh/100));
        System.out.println("Max time: "+String.valueOf(AstarMaxCh));
        System.out.println("Min time: " + String.valueOf(AstarMinCh));
        for (int i=0; i<10; i++) {
            System.out.println("Average time to drive to " +i + " restaurant " +String.valueOf(carTrip[4][i]/4000000.0));
        }
        System.out.println("--------------------------------------------------------------------------------------------");


    }
}
