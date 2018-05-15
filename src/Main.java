import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


public class Main {

    public static void main(String[] args) throws IOException {
/*
        Graph G = new Graph(6);
        G.addVertex(0, new Vertex(new Coords(0.0, 0.5)));
        G.addVertex(1,new Vertex(new Coords(1,1)));
        G.addVertex(2,new Vertex(new Coords(1,2)));
        G.addVertex(3,new Vertex(new Coords(2,2)));
        G.addVertex(4, new Vertex(new Coords(3,1)));
        G.addVertex(5, new Vertex(new Coords(4,5)));
        G.addEdge(0,1);
        G.addEdge(0,2);
        G.addEdge(2,3);
        G.addEdge(3,5);
        G.addEdge(1,3);
        G.addEdge(1,4);
        G.addEdge(4,5);
       // G.makeAdjList(6);
        int [] prev = Algorithms.Dijkstra(G,0);
        for (int i=0; i<prev.length; i++){
            System.out.println(prev[i]);
        }

*/

        int size=0;
        /*try {
            File inputFile = new File("D:\\myGraphs\\src\\VDV.osm");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            City_Roads build_graph = new City_Roads(document);
            size = build_graph.run();
        } catch (JDOMException | IOException e) {
            System.out.println(e.getMessage());
        }*/
        Graph G = new Graph(31648);
        try {
            G.makeGraph();
        } catch (IOException e) {
            e.printStackTrace();
        }



        List<Coords> restraunts = new ArrayList<>();
        restraunts.add(new Coords(43.1124516, 131.8791986));
        restraunts.add(new Coords(43.1155007, 131.9049247));
        restraunts.add(new Coords(43.1013713, 131.8990184));
        restraunts.add(new Coords(43.1168519, 131.8806968));
        restraunts.add(new Coords(43.1193366, 131.8821367));
        restraunts.add(new Coords(43.1202920, 131.8851400));
        restraunts.add(new Coords(43.1140839, 131.9013803));
        restraunts.add(new Coords(43.1174760, 131.8757273));
        restraunts.add(new Coords(43.1096183, 131.8782636));
        restraunts.add(new Coords(43.1138031, 131.9353779));
        for (int i=0; i<restraunts.size();i++){
            restraunts.get(i).transfornCoordsToDekart();
        }

        List<Integer> restIDs = new ArrayList<>();
        restraunts.forEach((el)-> {
            restIDs.add(G.findNearestVertex(el));
        });
        //TestsForAlgorithms.test(G,);
        System.out.println("Если вы хотите найти ближайший ресторан нажмите 1");
        System.out.println("Если вы хотите увидеть решение задачи коммивояжера нажмите 2");
        Scanner reader = new Scanner(System.in);
        int choice = reader.nextInt();
        if (choice==1) {
            System.out.println("Введите широту:");
            double x = reader.nextDouble();
            System.out.println("Введите долготу:");
            double y = reader.nextDouble();
            Coords user = new Coords(x, y);
            user.transfornCoordsToDekart();
            int startPoint = G.findNearestVertex(user);
            //int [] way = Algorithms.Dijkstra(G,startPoint);


            try (FileWriter writer = new FileWriter("D:\\myGraphs\\Ways.csv", false)) {
                for (int el : restIDs) {
                    int[] way = Algorithms.A_star(G, startPoint, el, 3);
                    int i = el;
                    double distance = 0;
                    writer.write(String.valueOf(i));
                    writer.append(",");
                    while (way[i] != -1) {
                        Coords st = G.getCoordById(i);
                        Coords fin = G.getCoordById(way[i]);
                        distance += Math.sqrt((st.getWigth() - fin.getWigth()) * (st.getWigth() - fin.getWigth()) + (st.getHeight() - fin.getHeight()) * (st.getHeight() - fin.getHeight()));
                        i = way[i];
                        writer.write(String.valueOf(i));
                        writer.append(",");
                    }
                    writer.write(String.valueOf(i));
                    writer.append(",");
                    writer.write(String.valueOf(distance));
                    writer.append("\n");


                }
            }
        }
        if (choice==2) {
            try (FileWriter writer = new FileWriter("D:\\myGraphs\\ways.csv", false)) {
                System.out.println("Введите широту:");
                double x = reader.nextDouble();
                System.out.println("Введите долготу:");
                double y = reader.nextDouble();
                Coords user = new Coords(x, y);
                user.transfornCoordsToDekart();
                int[][] resultWays = Kommivoyager.NearestNeighbour(G, restIDs, user);
                for (int j = 0; j < resultWays.length; j++) {
                    for (int i=0; i<resultWays[j].length; i++){
                    writer.write(String.valueOf(resultWays[j][i]));
                    writer.append(",");

                    }
                    writer.write(String.valueOf(11-j));
                    writer.append("\n");
                }
            }
        }

        Visualisation graph = new Visualisation();
        try {
            graph.CSVtoSVG(G);
        } catch (IOException e) {
            e.printStackTrace();
        }
        graph = null;



    }
}
