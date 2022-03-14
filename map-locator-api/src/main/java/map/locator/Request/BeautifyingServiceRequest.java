package map.locator.Request;


import map.locator.model.BeautifyingService;

public class BeautifyingServiceRequest {

    private int id;

    private String typeOfService;

    private String nameOfSalon;

    private String town;

    private double lat;

    private double lon;

    public BeautifyingServiceRequest() {
    }

    public BeautifyingServiceRequest(BeautifyingService beautifyingService) {

        this.id = beautifyingService.getId();
        this.typeOfService = beautifyingService.getTypeOfService();
        this.nameOfSalon = beautifyingService.getNameOfSalon();
        this.town = beautifyingService.getTown();
        this.lat = beautifyingService.getPoint().getX();
        this.lon = beautifyingService.getPoint().getY();

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTypeOfService() {
        return typeOfService;
    }

    public void setTypeOfService(String typeOfService) {
        this.typeOfService = typeOfService;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getNameOfSalon() {
        return nameOfSalon;
    }

    public void setNameOfSalon(String nameOfSalon) {
        this.nameOfSalon = nameOfSalon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }
}
