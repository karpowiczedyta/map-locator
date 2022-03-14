package map.locator.Request;

public class BoundingBox {

    public double LatBtm;

    public double LonBtm;

    public double LatTop;

    public double LonTop;

    public String serviceName;

    public int distance;

    public String treatment;

    public int priceFrom;

    public int priceTo;

    public double opinion;

    public double localizationLat;

    public double localizationLon;

    public BoundingBox(double latBtm, double lonBtm, double latTop, double lonTop, String serviceName, int distance, String treatment, int priceFrom, int priceTo, double opinion, double localizationLat, double localizationLon) {
        LatBtm = latBtm;
        LonBtm = lonBtm;
        LatTop = latTop;
        LonTop = lonTop;
        this.serviceName = serviceName;
        this.distance = distance;
        this.treatment = treatment;
        this.priceFrom = priceFrom;
        this.priceTo = priceTo;
        this.opinion = opinion;
        this.localizationLat = localizationLat;
        this.localizationLon = localizationLon;
    }


    public double getLatBtm() {
        return this.LatBtm;
    }

    public void setLatBtm(double latBtm) {
        LatBtm = latBtm;
    }

    public double getLonBtm() {
        return this.LonBtm;
    }

    public void setLonBtm(double lonBtm) {
        LonBtm = lonBtm;
    }

    public double getLatTop() {
        return this.LatTop;
    }

    public void setLatTop(double latTop) {
        LatTop = latTop;
    }

    public double getLonTop() {
        return this.LonTop;
    }

    public void setLonTop(double lonTop) {
        LonTop = lonTop;
    }

    public String getServiceName() {
        return this.serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public int getPriceFrom() {
        return priceFrom;
    }

    public void setPriceFrom(int priceFrom) {
        this.priceFrom = priceFrom;
    }

    public int getPriceTo() {
        return priceTo;
    }

    public void setPriceTo(int priceTo) {
        this.priceTo = priceTo;
    }

    public double getOpinion() {
        return opinion;
    }

    public void setOpinion(double opinion) {
        this.opinion = opinion;
    }

    public double getLocalizationLat() {
        return localizationLat;
    }

    public void setLocalizationLat(double localizationLat) {
        this.localizationLat = localizationLat;
    }

    public double getLocalizationLon() {
        return localizationLon;
    }

    public void setLocalizationLon(double localizationLon) {
        this.localizationLon = localizationLon;
    }
}
