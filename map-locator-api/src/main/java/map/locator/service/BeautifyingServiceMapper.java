package map.locator.service;

import map.locator.Request.BeautifyingServiceRequest;
import map.locator.Request.BeautifyingServiceTypeRequest;
import map.locator.Request.OpinionRequest;
import map.locator.adapter.BeautifingServiceRepository;
import map.locator.adapter.BeautifingServiceTypeRepository;
import map.locator.adapter.OpinionRepository;
import map.locator.model.BeautifyingService;
import map.locator.model.BeautifyingServiceType;
import map.locator.model.Opinion;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.springframework.stereotype.Component;

@Component
public class BeautifyingServiceMapper {

    BeautifingServiceRepository beautifingServiceRepository;
    BeautifingServiceTypeRepository beautifingServiceTypeRepository;
    OpinionRepository opinionRepository;

    public BeautifyingServiceMapper(BeautifingServiceRepository beautifingServiceRepository, BeautifingServiceTypeRepository beautifingServiceTypeRepository, OpinionRepository opinionRepository) {
        this.beautifingServiceRepository = beautifingServiceRepository;
        this.beautifingServiceTypeRepository = beautifingServiceTypeRepository;
        this.opinionRepository = opinionRepository;
    }

    public BeautifyingService toBeautifyingService(BeautifyingServiceRequest beautifyingServiceRequest){

        return new BeautifyingService(

                beautifyingServiceRequest.getId(),
                beautifyingServiceRequest.getTypeOfService(),
                beautifyingServiceRequest.getNameOfSalon(),
                beautifyingServiceRequest.getTown(),
                new GeometryFactory().createPoint(new Coordinate(beautifyingServiceRequest.getLon(), beautifyingServiceRequest.getLat()))

        );

    }

    public BeautifyingServiceType toBeautifyingServiceType(BeautifyingServiceTypeRequest beautifyingServiceTypeRequest){

        System.out.println(beautifyingServiceTypeRequest.getBeautifyingServiceId());
        BeautifyingService beautifyingService = beautifingServiceRepository.findById(beautifyingServiceTypeRequest.getBeautifyingServiceId()).get();

        return new BeautifyingServiceType(
            beautifyingServiceTypeRequest.getId(),
            beautifyingServiceTypeRequest.getName(),
            beautifyingServiceTypeRequest.getPrice(),
            beautifyingService
        );

}

    public Opinion toOpinion(OpinionRequest opinionRequest){

        BeautifyingService beautifyingService = beautifingServiceRepository.findById(opinionRequest.getBeautifingServiceId()).get();

        return new Opinion(
                opinionRequest.getId(),
                opinionRequest.getOpinion(),
                beautifyingService
        );

    }

    public BeautifyingServiceRequest toBeautifyingServiceRequest(BeautifyingService beautifyingService){

        return new BeautifyingServiceRequest(beautifyingService);

    }

    public BeautifyingServiceTypeRequest toBeautifyingServiceTypeRequest(BeautifyingServiceType beautifyingServiceType){

        return new BeautifyingServiceTypeRequest(beautifyingServiceType);

    }

    public OpinionRequest toBeautifyingServiceTypeRequest(Opinion opinion){

        return new OpinionRequest(opinion);

    }


}
