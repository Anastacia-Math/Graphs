import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import org.jdom.Document;
import org.jdom.Element;


public class City_Roads {
    public City_Roads(Document doc){
        city_map = doc;
    }

    private Document city_map;
    private Map <Long, Coords> getVertices (){
        Map <Long, Coords> Vertices = new HashMap<>();

        Element root = city_map.getRootElement();
        List <Element> elems = root.getChildren("node");
        for (Element el:elems){
            long id = Long.valueOf(el.getAttributeValue("id"));
            double lon = Double.valueOf(el.getAttributeValue("lon"));
            double lat = Double.valueOf(el.getAttributeValue("lat"));
            Vertices.put(id,new Coords(lat, lon));
        }
        return Vertices;
    }

    private List<Way>  getEdges (Map<Long,Coords> Vertices){
        Element root = city_map.getRootElement();
        List <Element> elems = root.getChildren("way");
        Map <Long,Node> result = new HashMap<>();
        List <Way> ways = new ArrayList<>(20000);
        long temp;
        for (Element el:elems){
            if (isCorrect(el.getChildren("tag"))){
                List <Element> nd = el.getChildren("nd");
                long st_point = Long.valueOf(nd.get(0).getAttributeValue("ref"));
                Coords st_point_Coords = Vertices.get(st_point);
                if (st_point_Coords == null) st_point_Coords = getLocation(st_point);
                st_point_Coords.transfornCoordsToDekart();
                double x = st_point_Coords.getDekart_width();
                double y = st_point_Coords.getDekart_heigth();
                
                long fin_point, fin ;
                if (result.containsKey(st_point)) {
                    temp = result.get(st_point).getId();


                }
                else {
                    result.put(st_point, new Node(x, y));
                    temp = result.get(st_point).getId();
                }
                for (int i=1; i<nd.size(); i++){
                    fin =Long.valueOf(nd.get(i).getAttributeValue("ref"));
                    Coords fin_Coords = Vertices.get(fin);
                    if (fin_Coords == null) fin_Coords = getLocation(fin);
                    fin_Coords.transfornCoordsToDekart();
                    x = fin_Coords.getDekart_width();
                    y = fin_Coords.getDekart_heigth();

                    if (result.containsKey(fin)) {

                        fin_point = result.get(fin).getId();

                    }
                    else {
                        result.put(fin, new Node(x, y));
                        fin_point = result.get(fin).getId();
                    }
                    Way road = new Way(temp,fin_point);
                    Way road1 = new Way(fin_point,temp);

                    ways.add(road);
                    ways.add(road1);
                    temp = fin_point;
                }

            }

        }
        printVertices(result);
        return ways;


    }

    private void printVertices (Map <Long, Node> result){

        try(FileWriter writer = new FileWriter("D:\\graphs\\vertices.csv", false))
        {
            writer.write("Vertices\n");
            Set<Long> ids = result.keySet();
            for (long el:ids){
                Node location = result.get(el);
                String elem = String.valueOf(el);
                writer.write(String.valueOf(location.getId()));
                writer.append(',');
                writer.write(elem);
                writer.append(',');
                writer.write(String.valueOf(location.getWidth()));
                writer.append(',');
                writer.write(String.valueOf(location.getHeight()
                ));
                writer.append('\n');
            }



            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }


    }

    private boolean isCorrect(List <Element> elems ){
        boolean isCorrect = false;
        for (Element el:elems) {
            if (el.getAttributeValue("k").equals("highway")) {
                String attr = el.getAttributeValue("v");
                if (attr.equals("motorway") || attr.equals("motorway_link") || attr.equals("trunk") || attr.equals("trunk_link")
                        || attr.equals("primary") || attr.equals("primary_link") || attr.equals("secondary")
                        || attr.equals("secondary_link") || attr.equals("tertiary") || attr.equals("tertiary_link")
                        || attr.equals("unclassified") || attr.equals("road") || attr.equals("residential"))
                    isCorrect = true;

            }
        }
        return isCorrect;
    }



    private Coords getLocation(Long nodeID) {
        String getNodeUrl = "https://www.openstreetmap.org/api/0.6/node/" + String.valueOf(nodeID);
        String xml = get(getNodeUrl);
        xml = xml.substring(xml.indexOf(">")+1);
        xml = xml.substring(xml.indexOf("lat"));
        xml = xml.substring(xml.indexOf("\"")+1);
        double lon = Double.valueOf(xml.substring(0,xml.indexOf("\"")));
        xml = xml.substring(xml.indexOf("lon"));
        xml = xml.substring(xml.indexOf("\"")+1);
        double lat = Double.valueOf(xml.substring(0,xml.indexOf("\"")));
        return new Coords(lon,lat);
    }


    private String get(String url) {
        try {
            URL _url = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) _url.openConnection();
            connection.setRequestMethod("GET");


            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }


    public void run () {
        Map<Long, Coords> Vertices;
        Vertices = getVertices();
        List <Way> ways = getEdges(Vertices);




        try(FileWriter writer = new FileWriter("D:\\graphs\\edges.csv", false))
        {

            writer.write("Starting point, Finish point\n");
            for (Way points:ways){
                writer.write(String.valueOf(points.getStart_id()));
                writer.append(',');
                writer.write(String.valueOf(points.getFinish_id()));

                writer.append('\n');
            }



            writer.flush();
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }


    }


    }
