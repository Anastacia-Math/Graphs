public class Pair implements Comparable<Pair>{
    private double distance;
    private int id;

    public Pair(int id){
        this.id = id;
    }

    public Pair( int id,double distance) {
        this.distance = distance;
        this.id = id;
    }

    public double getDistance() {
        return distance;
    }

    public int getId() {
        return id;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setId(int id) {
        this.id = id;
    }


    @Override
    public int compareTo(Pair other) {
       return Double.compare(this.distance, other.distance);
    }
}
