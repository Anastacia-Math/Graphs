public class Coords {

    private double wigth, height;


    private static double bigEllipsoid= 6378137.0;

    private static double smallEllipsoid=6356752.3142;

    private static double excentricitet = Math.sqrt(1-(smallEllipsoid/bigEllipsoid)*(smallEllipsoid/bigEllipsoid));

    public Coords (double _width, double _height){
        wigth = _width;
        height = _height;
    }

    private static double dekart_width, dekart_heigth;

    public void transfornCoordsToDekart() {
        dekart_width= bigEllipsoid*height;
        dekart_heigth = bigEllipsoid*Math.log(Math.tan(Math.PI/4+wigth/2)*
                Math.pow(((1-excentricitet*Math.sin(wigth))/(1+excentricitet*Math.sin(wigth))),excentricitet/2));
    }
    public double getWigth(){
        return wigth;
    }

    public double getHeight(){
        return height;
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
