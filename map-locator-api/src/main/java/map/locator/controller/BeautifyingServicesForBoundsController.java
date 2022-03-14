package map.locator.controller;

import map.locator.Request.BoundingBox;
import map.locator.adapter.BeautifingServiceRepository;
import map.locator.adapter.BeautifingServiceTypeRepository;
import map.locator.adapter.OpinionRepository;
import map.locator.model.BeautifyingService;
import map.locator.model.BeautifyingServiceType;
import org.geojson.Feature;
import org.geojson.Point;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Math.cos;


@Controller
@CrossOrigin
@PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
@RequestMapping("/bounds-services")
public class BeautifyingServicesForBoundsController {

    private final BeautifingServiceRepository beautifingServiceRepository;
    private final OpinionRepository opinionRepository;
    private final BeautifingServiceTypeRepository beautifingServiceTypeRepository;

    public BeautifyingServicesForBoundsController(BeautifingServiceRepository beautifingServiceRepository, OpinionRepository opinionRepository, BeautifingServiceTypeRepository beautifingServiceTypeRepository) {
        this.beautifingServiceRepository = beautifingServiceRepository;
        this.opinionRepository = opinionRepository;
        this.beautifingServiceTypeRepository = beautifingServiceTypeRepository;
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
    @PostMapping("/hairdressing-salons")
    public ResponseEntity<List<Feature>> getAllHairdressingSalons(@RequestBody BoundingBox boundingBox) {

        String polygon = getPolygon(boundingBox);
        System.out.println(polygon);

        return getAllSalonsFromFiltering(boundingBox, polygon);

    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
    @PostMapping("/beauty-salons")
    public ResponseEntity<List<Feature>> getAllBeautySalons(@RequestBody BoundingBox boundingBox) {
        String polygon = getPolygon(boundingBox);
        System.out.println(polygon);
        return getAllSalonsFromFiltering(boundingBox, polygon);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
    @PostMapping("/cosmetic-salons")
    public ResponseEntity<List<Feature>> getAllCosmeticSurgery(@RequestBody BoundingBox boundingBox) {
        String polygon = getPolygon(boundingBox);
        System.out.println(polygon);
        return getAllSalonsFromFiltering(boundingBox, polygon);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
    @PostMapping("/piercing-salons")
    public ResponseEntity<List<Feature>> getAllPiercingSalons(@RequestBody BoundingBox boundingBox) {
        String polygon = getPolygon(boundingBox);
        System.out.println(polygon);
        return getAllSalonsFromFiltering(boundingBox, polygon);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
    @PostMapping("/laser-therapy-salons")
    public ResponseEntity<List<Feature>> getAllLaserTherapySalons(@RequestBody BoundingBox boundingBox) {
        String polygon = getPolygon(boundingBox);
        System.out.println(polygon);
        return getAllSalonsFromFiltering(boundingBox, polygon);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
    @PostMapping("/aesthetic-medicine-salons")
    public ResponseEntity<List<Feature>> getAllAestheticMedicineSalons(@RequestBody BoundingBox boundingBox) {
        String polygon = getPolygon(boundingBox);
        System.out.println(polygon);
        return getAllSalonsFromFiltering(boundingBox, polygon);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN') and hasRole('ROLE_USER')")
    @PostMapping("/tattoo-studio")
    public ResponseEntity<List<Feature>> getAllTattooStudio(@RequestBody BoundingBox boundingBox) {
        String polygon = getPolygon(boundingBox);
        System.out.println(polygon);
        return getAllSalonsFromFiltering(boundingBox, polygon);
    }

    String getPolygon(BoundingBox boundingBox) {
        GeometryFactory geometryFactory = new GeometryFactory();
        var shapeFactory = new GeometricShapeFactory(geometryFactory);
        var diameter = boundingBox.getDistance() * 1000 * 2;
        shapeFactory.setNumPoints(20);
        shapeFactory.setCentre(new Coordinate(boundingBox.localizationLon, boundingBox.localizationLat));
        shapeFactory.setHeight(diameter / 111_320.0);
        shapeFactory.setWidth(diameter / (40_075_000 * cos(Math.toRadians(boundingBox.localizationLat)) / 360));
        Polygon circle = shapeFactory.createCircle();
        String polygon = shapeFactory.createCircle().toText();
        return polygon;
    }

    ResponseEntity<List<Feature>> getDateWithoutOptionSearchingByOpinion(List<BeautifyingService> beautifingService) {

        if (beautifingService.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {
            List<Feature> featureList =
                    beautifingService.stream()
                            .map(service -> {
                                Feature feature = new Feature();
                                feature.setGeometry(new Point(service.getPoint().getX(), service.getPoint().getY()));
                                feature.setProperty("typeOfService", service.getTypeOfService());
                                feature.setProperty("nameOfSalon", service.getNameOfSalon());
                                feature.setProperty("id", service.getId());
                                double opinion = opinionRepository.getOpinion(service.getId()).orElse(0.0);
                                feature.setProperty("opinion", opinion);
                                List<BeautifyingServiceType> typeOfBeautifingService = beautifingServiceTypeRepository.getTypeOfBeautifingService(service.getId());
                                feature.setProperty("typeOfBeautifingService", typeOfBeautifingService);
                                return feature;
                            })
                            .collect(Collectors.toList());
            return ResponseEntity.ok(featureList);
        }
    }

    ResponseEntity<List<Feature>> getDateWithOptionSearchingByOpinion(List<BeautifyingService> beautifingService, double opinion) {
        List<BeautifyingService> beautifingServiceWithSuitableOpinion = new ArrayList<>();
        double avgFromOpinions = 0.0;

        if (beautifingService.size() == 0) {
            return ResponseEntity.notFound().build();
        } else {

            for (int i = 0; i < beautifingService.size(); i++) {
                avgFromOpinions = opinionRepository.getOpinion(beautifingService.get(i).getId()).orElse(0.0);

                if (opinion == avgFromOpinions || opinion < avgFromOpinions) {
                    beautifingServiceWithSuitableOpinion.add(beautifingService.get(i));
                }
            }

            List<Feature> featureList =
                    beautifingServiceWithSuitableOpinion.stream()
                            .map(service -> {

                                Feature feature = new Feature();
                                feature.setGeometry(new Point(service.getPoint().getX(), service.getPoint().getY()));
                                feature.setProperty("typeOfService", service.getTypeOfService());
                                feature.setProperty("nameOfSalon", service.getNameOfSalon());
                                feature.setProperty("id", service.getId());
                                double avgFromOpinion = opinionRepository.getOpinion(service.getId()).orElse(0.0);
                                feature.setProperty("opinion", avgFromOpinion);
                                List<BeautifyingServiceType> typeOfBeautifingService = beautifingServiceTypeRepository.getTypeOfBeautifingService(service.getId());
                                feature.setProperty("typeOfBeautifingService", typeOfBeautifingService);
                                return feature;

                            })
                            .collect(Collectors.toList());
            return ResponseEntity.ok(featureList);
        }
    }

    ResponseEntity<List<Feature>> getAllSalonsFromFiltering(BoundingBox boundingBox, String polygon) {

        List<BeautifyingService> beautifingService;
        ResponseEntity<List<Feature>> dateWithoutOptionSearchingByOpinion = null;

        if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndUserLocalization(boundingBox.getServiceName(), polygon, boundingBox.getTreatment());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);

        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByPriceFromAndUserLocalization(boundingBox.getServiceName(), polygon, boundingBox.getPriceFrom());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);

        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() != 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByPriceToAndUserLocalization(boundingBox.getServiceName(), polygon, boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);

        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceFromBounds(boundingBox.getServiceName(), polygon);

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() == 0
                && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() != 0) {

            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndPriceFromAndUserLocalization(boundingBox.getServiceName(), polygon, boundingBox.getTreatment(), boundingBox.getPriceFrom());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);

        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() != 0
                && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndPriceFromAndUserLocalization(boundingBox.getServiceName(), polygon, boundingBox.getTreatment(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);

        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() == 0
                && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndUserLocalization(boundingBox.getServiceName(), polygon, boundingBox.getTreatment());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() != 0
                && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByPriceFromAndPriceToAndUserLocalization(boundingBox.getServiceName(), polygon, boundingBox.getPriceFrom(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);

        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() == 0
                && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByPriceFromAndUserLocalization(boundingBox.getServiceName(), polygon, boundingBox.getPriceFrom());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() != 0
                && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByPriceToAndUserLocalization(boundingBox.getServiceName(), polygon, boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() != 0
                && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() != 0) {

            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndPriceFromAndPriceToAndDistanceToAndUserLocalization(boundingBox.getServiceName(),
                            polygon, boundingBox.getTreatment(), boundingBox.getPriceFrom(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);

        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() != 0
                && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByPriceFromAndPriceToAndUserLocalization(boundingBox.getServiceName(),
                            polygon, boundingBox.getPriceFrom(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() != 0
                && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndPriceToAndUserLocalization(boundingBox.getServiceName(),
                            polygon, boundingBox.getTreatment(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() == 0
                && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndPriceFromAndUserLocalization(boundingBox.getServiceName(),
                            polygon, boundingBox.getTreatment(), boundingBox.getPriceFrom());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() != 0 && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndPriceFromAndPriceToAndDistanceToAndUserLocalization(boundingBox.getServiceName(), polygon, boundingBox.getTreatment(), boundingBox.getPriceFrom(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() != 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceFromBounds(boundingBox.getServiceName(), polygon);

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingService(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);

        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() != 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentPriceFromAndPriceTo(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getTreatment(), boundingBox.getPriceFrom(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);

        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndPriceFrom(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getTreatment(), boundingBox.getPriceFrom());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() != 0 && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndPriceTo(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getTreatment(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() != 0 && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByPriceFromAndPriceTo(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getPriceFrom(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());

        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() != 0 && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByFullOption(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getTreatment(), boundingBox.getPriceFrom(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());
        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() != 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndPriceTo(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getTreatment(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);
        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatmentAndPriceFrom(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getTreatment(), boundingBox.getPriceFrom());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);
        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByTreatment(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getTreatment());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());
        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() != 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByPriceFromAndPriceTo(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getPriceFrom(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingService);
        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByPriceFrom(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getPriceFrom());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());
        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() != 0 && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingServiceByPriceTo(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());
        } else if (boundingBox.getTreatment() != null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() == 0) {

            List<BeautifyingService> beautifingServiceByTreatment = beautifingServiceRepository
                    .getBeautifingServiceByTreatment(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getTreatment());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingServiceByTreatment);
        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() != 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() == 0) {

            List<BeautifyingService> beautifingServiceByTreatment = beautifingServiceRepository
                    .getBeautifingServiceByPriceFrom(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getPriceFrom());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingServiceByTreatment);
        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() != 0 && boundingBox.getOpinion() == 0.0 && boundingBox.getDistance() == 0) {

            List<BeautifyingService> beautifingServiceByTreatment = beautifingServiceRepository
                    .getBeautifingServiceByPriceTo(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop(), boundingBox.getPriceTo());

            dateWithoutOptionSearchingByOpinion = getDateWithoutOptionSearchingByOpinion(beautifingServiceByTreatment);

        } else if (boundingBox.getTreatment() == null && boundingBox.getPriceFrom() == 0 && boundingBox.getPriceTo() == 0 && boundingBox.getOpinion() != 0.0 && boundingBox.getDistance() == 0) {
            beautifingService = beautifingServiceRepository
                    .getBeautifingService(boundingBox.getServiceName(), boundingBox.getLatBtm(), boundingBox.getLonBtm(), boundingBox.getLatTop(), boundingBox.getLonTop());

            dateWithoutOptionSearchingByOpinion = getDateWithOptionSearchingByOpinion(beautifingService, boundingBox.getOpinion());
        }
        return dateWithoutOptionSearchingByOpinion;
    }

}



