package map.locator.model;

import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;

@Entity
@CrossOrigin
public class Opinion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double opinion;

    @ManyToOne
    @JoinColumn(name = "beautifying_service_id")
    private BeautifyingService beautifyingService;

    public Opinion() {
    }

    public Opinion(int id, double opinion, BeautifyingService beautifyingService) {
        this.id = id;
        this.opinion = opinion;
        this.beautifyingService = beautifyingService;
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

    public BeautifyingService getBeautifyingService() {
        return beautifyingService;
    }

    public void setBeautifyingService(BeautifyingService beautifyingService) {
        this.beautifyingService = beautifyingService;
    }
}
