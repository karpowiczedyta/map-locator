package map.locator.Request;


import map.locator.model.BeautifyingServiceType;

public class BeautifyingServiceTypeRequest {

    int id;

    String name;

    int price;

    int beautifyingServiceId;

    public BeautifyingServiceTypeRequest() {
    }

    public BeautifyingServiceTypeRequest(BeautifyingServiceType beautifyingServiceType) {
        this.id = beautifyingServiceType.getId();
        this.name = beautifyingServiceType.getName();
        this.price = beautifyingServiceType.getPrice();
        this.beautifyingServiceId = beautifyingServiceType.getBeautifyingService().getId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getBeautifyingServiceId() {
        return beautifyingServiceId;
    }

    public void setBeautifyingServiceId(int beautifyingServiceId) {
        this.beautifyingServiceId = beautifyingServiceId;
    }
}
