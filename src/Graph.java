import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Graph {
    private byte[][] matrixSmeg;
    private ArrayList<Vertex> vertexList;
    private  static  int numOfVertices;
    private  static double xmin;
    private  static double xmax;
    private  static double ymin;
    private  static  double ymax;

    public double getXmin() {
        return xmin;
    }

    public double getXmax() {
        return xmax;
    }

    public double getYmin() {
        return ymin;
    }

    public double getYmax() {
        return ymax;
    }

    private static final String SAMPLE_CSV_FILE_PATH_VERTICES = "D:\\myGraphs\\vertices.csv";
    private static final String SAMPLE_CSV_FILE_PATH_EDGES = "D:\\myGraphs\\edges.csv";
    public Graph(int size){
        matrixSmeg = new byte[size][size];
        for (int i=0;i<size; i++)
            for (int j=0; j<size; j++)
                matrixSmeg[i][j]=0;
        numOfVertices =0;
        vertexList = new ArrayList<>(size);
        for(int i=0;i<size;++i)
            vertexList.add(i,null);

    }

    public Coords getCoordById(int id){
        return vertexList.get(id).getLatLon();
    }



    public void addVertex(int i,Vertex ver){
        vertexList.add(i,ver);
        numOfVertices++;
    }

    public void addEdge(int i, int j){
        matrixSmeg[i][j]=1;
        matrixSmeg[j][i]=1;
    }


    public void makeGraph() throws IOException {
        numOfVertices = 0;
        Reader reader = null;
        try {
            reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH_VERTICES));

        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVReader csvReader = new CSVReader(reader);
        List<String[]> records = csvReader.readAll();
        records.remove(0);
        //System.out.println(vertexList.size());
        xmax = Double.MIN_VALUE;
        ymax = Double.MIN_VALUE;
        ymin = Double.MAX_VALUE;
        xmin = Double.MAX_VALUE;
        for (String[] record: records) {
            if (Double.valueOf(record[2])>xmax)
                xmax = Double.valueOf(record[2]);
            if (Double.valueOf(record[3])>ymax)
                ymax = Double.valueOf(record[3]);
            if (Double.valueOf(record[2])<xmin)
                xmin = Double.valueOf(record[2]);
            if (Double.valueOf(record[3])<ymin)
                ymin = Double.valueOf(record[3]);
            vertexList.set(Integer.valueOf(record[0]), new Vertex(new Coords(Double.valueOf(record[2]),Double.valueOf(record[3]))));
            numOfVertices++;

        }


        try {
            reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH_EDGES));

        } catch (IOException e) {
            e.printStackTrace();
        }
        CSVReader csvReader1 = new CSVReader(reader);
        List<String[]> edges = csvReader1.readAll();
        edges.remove(0);
        for (String [] edge : edges){
            int first = Integer.valueOf(edge[0]);
            int second = Integer.valueOf(edge[1]);
            matrixSmeg[first][second] = 1;
            matrixSmeg[second][first]=1;
        }
       makeAdjList();
    }

    private List <List <Integer>>  AdjList = new ArrayList<>();


    public void makeAdjList() {
        for (int i=0; i<numOfVertices; i++) {
            List<Integer> lilList = new ArrayList<>();
            for (int j = 0; j < numOfVertices; j++)
                if (matrixSmeg[i][j] == 1) {
                    lilList.add(j);
                }
            AdjList.add(i, lilList);
        }
    }

    public List<Integer> getAdjList(int Vert){
        return AdjList.get(Vert);
    }

    public int getNumOfVertices() {
        return numOfVertices;
    }

    public int findNearestVertex(Coords myPoint){
        double distance =Double.MAX_VALUE;
        int res = 0;
        for (int i=0; i<numOfVertices; i++){
            Coords ver = vertexList.get(i).getLatLon();

            double dis = ver.getDistance(myPoint);

            if (dis < distance) {
                distance = dis;
                res = i;
            }

        }
        return res;
    }



}
