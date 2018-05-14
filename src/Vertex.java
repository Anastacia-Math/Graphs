public class Vertex {
    private boolean isVisited;
    //private String name;
    private Coords latLon;

    public Vertex(Coords latLon) {
        this.isVisited = false;
        //this.name = name;
        this.latLon = latLon;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    //public String getName() {
      //  return name;
    //}

    /*public void setName(String name) {
        this.name = name;
    }*/

    public Coords getLatLon() {
        return latLon;
    }

    public void setLatLon(Coords latLon) {
        this.latLon = latLon;
    }



}
