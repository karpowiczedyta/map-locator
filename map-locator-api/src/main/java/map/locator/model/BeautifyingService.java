package map.locator.model;

import com.bedatadriven.jackson.datatype.jts.serialization.GeometryDeserializer;
import com.bedatadriven.jackson.datatype.jts.serialization.GeometrySerializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import map.locator.Request.BeautifyingServiceRequest;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.persistence.*;
import java.util.List;

@Entity
@CrossOrigin
public class BeautifyingService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String typeOfService;

    private String nameOfSalon;

    private String town;

    @JsonSerialize(using = GeometrySerializer.class)
    @JsonDeserialize(using = GeometryDeserializer.class)
    @Column(name = "point", columnDefinition = "Geometry")
    private Point point;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "beautifyingService")
    @JsonIgnore
    private List<BeautifyingServiceType> beautifyingServiceTypes;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "beautifyingService")
    @JsonIgnore
    private List<Opinion> opinion;

    public BeautifyingService() {
    }

    public BeautifyingService(int id, String typeOfService, String nameOfSalon, String town, Point point) {
        this.id = id;
        this.typeOfService = typeOfService;
        this.nameOfSalon = nameOfSalon;
        this.town = town;
        this.point = point;

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


    public List<BeautifyingServiceType> getBeautifyingServiceTypes() {
        return beautifyingServiceTypes;
    }

    public void setBeautifyingServiceTypes(List<BeautifyingServiceType> beautifyingServiceTypes) {
        this.beautifyingServiceTypes = beautifyingServiceTypes;
    }

    public List<Opinion> getOpinion() {
        return opinion;
    }

    public void setOpinion(List<Opinion> opinion) {
        this.opinion = opinion;
    }

    public String getNameOfSalon() {
        return nameOfSalon;
    }

    public void setNameOfSalon(String nameOfSalon) {
        this.nameOfSalon = nameOfSalon;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public void updateFrom(BeautifyingServiceRequest toUpdate) {

        this.typeOfService = toUpdate.getTypeOfService();

        this.nameOfSalon = toUpdate.getNameOfSalon();

        this.town = toUpdate.getTown();

        this.point = new GeometryFactory().createPoint(new Coordinate(toUpdate.getLon(), toUpdate.getLat()));
    }
}
