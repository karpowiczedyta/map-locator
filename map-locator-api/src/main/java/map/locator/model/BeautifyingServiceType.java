package map.locator.model;


import map.locator.Request.BeautifyingServiceTypeRequest;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;

@Entity
@CrossOrigin
public class BeautifyingServiceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int price;

    public BeautifyingServiceType() {
    }

    public BeautifyingServiceType(int id, String name, int price, BeautifyingService beautifyingService) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.beautifyingService = beautifyingService;
    }

    @ManyToOne
    @JoinColumn(name = "beautifying_service_id")
    private BeautifyingService beautifyingService;

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

    public BeautifyingService getBeautifyingService() {
        return beautifyingService;
    }

    public void setBeautifyingService(BeautifyingService beautifyingService) {
        this.beautifyingService = beautifyingService;
    }

    public void updateFrom(BeautifyingServiceTypeRequest toUpdate) {

        this.name = toUpdate.getName();

        this.price = toUpdate.getPrice();

    }
}
