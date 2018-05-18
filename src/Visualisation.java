import java.io.*;

import com.opencsv.CSVReader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Visualisation {

    private static final String SAMPLE_CSV_FILE_PATH_VERTICES = "D:\\myGraphs\\vertices.csv";
    private static final String SAMPLE_CSV_FILE_PATH_EDGES = "D:\\myGraphs\\edges.csv";
    private static final String SAMPLE_CSV_FILE_PATH_DIGKSTRA_WAYS = "D:\\myGraphs\\Ways.csv";
    private static final String out = "<line x1='%s' x2='%s' y1='%s' y2='%s' stroke='%s' style='stroke-width: %spx; stroke: %s;'/>";


    public void CSVtoSVG(Graph G) throws IOException {
        List <Coords> restraunts = new ArrayList<>();
        restraunts.add(new Coords(43.1124516, 131.8791986));
        restraunts.add(new Coords(43.1155007, 131.9049247));
        restraunts.add(new Coords(43.1013713, 131.8990184));
        restraunts.add(new Coords(43.1202920, 131.8851400));
        restraunts.add(new Coords(43.1140839, 131.9013803));
        restraunts.add(new Coords(43.1174760, 131.8757273));
        restraunts.add(new Coords(43.1096183, 131.8782636));
        restraunts.add(new Coords(43.1168519, 131.8806968));
        restraunts.add(new Coords(43.1193366, 131.8821367));
        restraunts.add(new Coords(43.1138031, 131.9353779));
        for (int i=0; i<restraunts.size();i++){

            restraunts.get(i).transfornCoordsToDekart();

        }

        //for (int i=0; i<restraunts.size();i++) System.out.println(restraunts.get(i).getDekart_heigth());
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH_VERTICES));

        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVReader csvReader = new CSVReader(reader);
        List <Long> id = new ArrayList<>(20000);
        List <Double> x = new ArrayList<>(20000);
        List <Double> y = new ArrayList<>(20000);
        Map <Long, Coords> nodes = new HashMap<>();

        List<String[]> records = csvReader.readAll();
        records.remove(0);
        double xmax=-Double.MAX_VALUE, ymax=-Double.MAX_VALUE, xmean=0,ymean=0,ymin= Double.MAX_VALUE, xmin=Double.MAX_VALUE;
        for (String[] record:records) {

            id.add(Long.valueOf(record[0]));
            x.add(Double.valueOf(record[2]));
            if (Double.valueOf(record[2]) >= xmax)
                xmax = Double.valueOf(record[2]);
            if (Double.valueOf(record[2]) <= xmin)
                xmin = Double.valueOf(record[2]);
            xmean += Double.valueOf(record[2]) / records.size();
            y.add(Double.valueOf(record[3]));

            if (Double.valueOf(record[3]) >= ymax)
                ymax = Double.valueOf(record[3]);
            if (Double.valueOf(record[3]) <= ymin)
                ymin = Double.valueOf(record[3]);
            ymean += Double.valueOf(record[3]) / records.size();

        }

        for (int i=0; i<x.size();i++){
            double el=x.get(i);

            el = (el-xmean)/(xmax-xmin)+1;
            el/=2;
            x.set(i,el);
        }

        for (int k=0; k<y.size();k++){
            double el=y.get(k);
            el = (el-ymean)/(ymax-ymin)+1;
            el/=2;
            y.set(k,el);
        }
        for (int i=0; i<restraunts.size(); i++){

            double yR = restraunts.get(i).getDekart_width();
            double xR = restraunts.get(i).getDekart_heigth();
            xR = (xR-xmean)/(xmax-xmin)+1;
            xR/=2;

            yR = (yR-ymean)/(ymax-ymin)+1;
            yR/=2;
            //System.out.println(yR);
            restraunts.set(i,new Coords(xR,yR));
        }
        for (int i=0; i<id.size(); i++){
            nodes.put(id.get(i), new Coords(x.get(i),y.get(i)));
        }

        try {
            reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH_EDGES));

        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVReader csvReader1 = new CSVReader(reader);
        int width=2000, height=2000;

        List<String[]> edges = csvReader1.readAll();
        FileWriter writer = new FileWriter("D:\\mygraphs\\graph.svg");
        writer.write("<?xml version='1.0' standalone='no'?>\n");
        writer.write("<svg width='2000'  height='2000' version='1.1' xmlns='http://www.w3.org/2000/svg'>\n");

        try {
            reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH_DIGKSTRA_WAYS));

        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVReader csvReader2 = new CSVReader(reader);
        List<String[]> ways = csvReader2.readAll();
        edges.remove(0);
        String[] colors = {"red","green","yellow","pink","orange","purple","grey","brown","golden","coral","olive"};
        for (int i=0; i<edges.size();i++) {
            if (i % 2 == 0) {
                long id1 = Long.valueOf(edges.get(i)[0]);
                long id2 = Long.valueOf(edges.get(i)[1]);

                Coords first = nodes.get(id1);
                Coords second = nodes.get(id2);
                double x1 = first.getWigth() * width;
                double x2 = second.getWigth() * width;
                double y1 = first.getHeight() * height;
                double y2 = second.getHeight() * height;

                String line = "<line x1=" + "'" + String.format(String.valueOf(x1)) + "'" + " x2=" + "'" + String.format(String.valueOf(x2)) + "'" + " y1=" + "'" + String.format(String.valueOf(y1)) + "'" +
                        " y2=" + "'" + String.format(String.valueOf(y2)) + "'" + " stroke='blue' style='stroke-width: 0.4px; stroke: blue;'/>";
                writer.write(line);

                writer.write('\n');

            }
        }
        for (Coords el:restraunts){
            String line =  "<circle cx=" + "'"+ String.valueOf(el.getWigth()*width)+ "'" + " cy="+ "'"+ String.valueOf(el.getHeight()*height)+ "'"+" r='0.1'  stroke='red' fill='red' stroke-width='1'/>";
            writer.write(line);
            writer.write('\n');

        }
        double fastestRoute = Double.MAX_VALUE;
        int numOfFastestRoute = 0;
        int j=0;
        for (String [] route: ways){
            for (int i=1; i<route.length-1; i++){
                double x1 = G.getCoordById(Integer.valueOf(route[i-1])).getWigth();
                double x2 = G.getCoordById(Integer.valueOf(route[i])).getWigth();
                //нормализация
                x1 = (x1-xmean)/(xmax-xmin)+1;
                x1/=2;
                x1*=width;
                x2 = (x2-xmean)/(xmax-xmin)+1;
                x2/=2;
                x2*=width;

                double y1 = G.getCoordById(Integer.valueOf(route[i-1])).getHeight();
                double y2 = G.getCoordById(Integer.valueOf(route[i])).getHeight();
                y1 = (y1-ymean)/(ymax-ymin)+1;
                y1/=2;
                y1*=height;
                y2 = (y2-ymean)/(ymax-ymin)+1;
                y2/=2;
                y2*=height;


                String line = String.format(out,x1,x2,y1,y2,colors[j],"0,6",colors[j]);
                writer.write(line);
                writer.write('\n');

            }
            if (Double.valueOf(route[route.length-1])<fastestRoute){
                fastestRoute = Double.valueOf(route[route.length-1]);
                numOfFastestRoute = j;

            }
            j++;

        }
        //рисуем самый оптимальный маршрут отдельно
        String [] route = ways.get(numOfFastestRoute);
        for (int i=1; i<route.length-1; i++) {
            double x1 = G.getCoordById(Integer.valueOf(route[i-1])).getWigth();
            double x2 = G.getCoordById(Integer.valueOf(route[i])).getWigth();
            //нормализация
            x1 = (x1 - xmean) / (xmax - xmin) + 1;
            x1 /= 2;
            x1 *= width;
            x2 = (x2 - xmean) / (xmax - xmin) + 1;
            x2 /= 2;
            x2 *= width;

            double y1 = G.getCoordById(Integer.valueOf(route[i - 1])).getHeight();
            double y2 = G.getCoordById(Integer.valueOf(route[i])).getHeight();
            y1 = (y1 - ymean) / (ymax - ymin) + 1;
            y1 /= 2;
            y1 *= height;
            y2 = (y2 - ymean) / (ymax - ymin) + 1;
            y2 /= 2;
            y2 *= height;


            String line = "<line x1=" + "'" + String.format(String.valueOf(x1)) + "'" + " x2=" + "'" + String.format(String.valueOf(x2)) + "'" + " y1=" + "'" + String.format(String.valueOf(y1)) + "'" +
                    " y2=" + "'" + String.format(String.valueOf(y2)) + "'" + " stroke='red' style='stroke-width: 0.7px; stroke: red;'/>";
            writer.write(line);
            writer.write('\n');
        }


        writer.write("</svg>");
        writer.close();
    }
}







