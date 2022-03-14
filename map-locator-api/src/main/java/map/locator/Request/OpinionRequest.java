package map.locator.Request;

import map.locator.model.Opinion;

public class OpinionRequest {

    int id;

    double opinion;

    int beautifingServiceId;

    public OpinionRequest() {
    }

    public OpinionRequest(Opinion opinion) {
        this.id = opinion.getId();
        this.opinion = opinion.getOpinion();
        this.beautifingServiceId = opinion.getBeautifyingService().getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getOpinion() {
        return opinion;
    }

    public void setOpinion(double opinion) {
        this.opinion = opinion;
    }

    public int getBeautifingServiceId() {
        return beautifingServiceId;
    }

    public void setBeautifingServiceId(int beautifingServiceId) {
        this.beautifingServiceId = beautifingServiceId;
    }
}
