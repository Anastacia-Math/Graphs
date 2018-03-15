import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;


public class Main {

    public static void main(String[] args) {

        try {
            File inputFile = new File("D:\\graphs\\src\\VDV.osm");
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = saxBuilder.build(inputFile);
            City_Roads build_graph = new City_Roads(document);
            build_graph.run();
        } catch (JDOMException | IOException e) {
            System.out.println(e.getMessage());
        }

    }
}
