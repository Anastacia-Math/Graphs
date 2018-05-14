public class Coords {

    private double wigth, height;


    private static double bigEllipsoid= 6378137.0;

    private static double smallEllipsoid=6356752.3142;

    private static double excentricitet = Math.sqrt(1-(smallEllipsoid/bigEllipsoid)*(smallEllipsoid/bigEllipsoid));

    public void setWigth(double wigth) {
        this.wigth = wigth;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Coords (double _width, double _height){
        wigth = _width;
        height = _height;
    }

    private  double dekart_width, dekart_heigth;

    public void transfornCoordsToDekart() {
        dekart_width= bigEllipsoid*height*Math.PI/180.;
        dekart_heigth = bigEllipsoid*Math.log(Math.tan(Math.PI/4+wigth/2)*
                Math.pow(((1-excentricitet*Math.sin(wigth))/(1+excentricitet*Math.sin(wigth))),excentricitet/2))*Math.PI/180;
    }
    public double getWigth(){
        return wigth;
    }

    public double getHeight(){
        return height;
    }

    public double getDistance (Coords other) {
        return Math.sqrt((other.getDekart_heigth()-this.wigth)*(other.getDekart_heigth()-this.wigth)+(other.getDekart_width()-this.height)*(other.getDekart_width()-this.height));
    }

    public double getDistance1 (Coords other){
        return Math.sqrt((other.getWigth()-this.wigth)*(other.getWigth()-this.wigth)+(other.getHeight()-this.height)*(other.getHeight()-this.height));
    }


    public double getDekart_width(){

        return dekart_width;
    }
    public double getDekart_heigth(){
        return dekart_heigth;
    }

    public void setDekart_width(double x){
        dekart_width = x;
    }
    public void setDekart_heigth(double x){
        dekart_heigth = x;
    }

}
