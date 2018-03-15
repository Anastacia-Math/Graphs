import java.io.*;

import com.opencsv.CSVReader;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Visualisation {

    private static final String SAMPLE_CSV_FILE_PATH_VERTICES = "D:\\graphs\\vertices.csv";
    private static final String SAMPLE_CSV_FILE_PATH_EDGES = "D:\\graphs\\edges.csv";


    public void CSVtoSVG() throws IOException {
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
        //System.out.println(y.get(0));
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
        //System.out.println(y.get(0));
        //System.out.println(xmax);
        //System.out.println(xmean);
        //System.out.println(xmin);
        for (int i=0; i<id.size(); i++){
            nodes.put(id.get(i), new Coords(x.get(i),y.get(i)));
            //System.out.println(id.get(i)+" "+x.get(i)+' '+ y.get(i));
        }

        try {
            reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH_EDGES));

        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVReader csvReader1 = new CSVReader(reader);
        int width=5000, height=5000;

        List<String[]> edges = csvReader1.readAll();
        FileWriter writer = new FileWriter("D:\\graphs\\graph.svg");
        writer.write("<?xml version='1.0' standalone='no'?>\n");
        writer.write("<svg width='5000'  height='5000' version='1.1' xmlns='http://www.w3.org/2000/svg'>\n");


        edges.remove(0);



        for (int i=0; i<edges.size();i++) {
            if (i % 2 == 0) {
                long id1 = Long.valueOf(edges.get(i)[0]);
                long id2 = Long.valueOf(edges.get(i)[1]);
                // System.out.println(id1+" "+id2);
                Coords first = nodes.get(id1);
                Coords second = nodes.get(id2);
                double x1 = first.getWigth() * width;
                double x2 = second.getWigth() * width;
                double y1 = first.getHeight() * height;
                double y2 = second.getHeight() * height;
                //System.out.println(x1+" "+x2+' '+y1+' '+y2);
                String line = "<line x1=" + "'" + String.format(String.valueOf(x1)) + "'" + " x2=" + "'" + String.format(String.valueOf(x2)) + "'" + " y1=" + "'" + String.format(String.valueOf(y1)) + "'" +
                        " y2=" + "'" + String.format(String.valueOf(y2)) + "'" + " stroke='blue' style='stroke-width: 0.4px; stroke: blue;'/>";
                writer.write(line);

                writer.write('\n');

            }
        }
        writer.write("</svg>");

        }



        }








