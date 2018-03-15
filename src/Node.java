public class Node {

    private static long count=0;
    private long id;
    private double  width;
    private double height;

    public Node (double _height, double _width){
        height = _height;
        width = _width;
        id = ++count;
    }


    public long getId(){
        return id;
    }

    public double getWidth(){
        return width;
    }


    public double getHeight() {
        return height;
    }
}
