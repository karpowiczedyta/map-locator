package map.locator.controller;


import map.locator.Request.BeautifyingServiceRequest;
import map.locator.adapter.BeautifingServiceRepository;
import map.locator.model.BeautifyingService;
import map.locator.service.BeautifyingServiceMapper;
import org.locationtech.jts.geom.Coordinate;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;


@Controller
@CrossOrigin
@RequestMapping("/beautifying-service")
public class BeautifyingServiceController {

    private final BeautifingServiceRepository beautifingServiceRepository;
    private final BeautifyingServiceMapper beautifyingServiceMapper;

    public BeautifyingServiceController(BeautifingServiceRepository beautifingServiceRepository, BeautifyingServiceMapper beautifyingServiceMapper) {
        this.beautifingServiceRepository = beautifingServiceRepository;
        this.beautifyingServiceMapper = beautifyingServiceMapper;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    ResponseEntity<BeautifyingService> createBeautifyingService(@RequestBody BeautifyingServiceRequest beautifyingServiceRequest) {
        BeautifyingService beautifyingService = beautifyingServiceMapper.toBeautifyingService(beautifyingServiceRequest);
        BeautifyingService result = beautifingServiceRepository.save(beautifyingService);
        Coordinate[] coordinates = result.getPoint().getCoordinates();
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/search-by-name-and-town")
    public ResponseEntity<Optional<Integer>> searchByNameAndTown(@RequestBody BeautifyingServiceRequest beautifyingServiceRequest) {

        ResponseEntity<Integer> build = ResponseEntity.notFound().build();
        Optional<Integer> id = beautifingServiceRepository.searchByNameAndTown(beautifyingServiceRequest.getNameOfSalon(), beautifyingServiceRequest
                .getTown());

        if (id.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(id);

    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody BeautifyingServiceRequest toUpdate) {
        if (!beautifingServiceRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        beautifingServiceRepository.findById(id)
                .ifPresent(beautifyingService -> {
                    beautifyingService.updateFrom(toUpdate);
                    beautifingServiceRepository.save(beautifyingService);
                });
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id) {
        System.out.println("usuwam");
        beautifingServiceRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping
    public ResponseEntity<List<BeautifyingService>> getAllServices() {

        List<BeautifyingService> result = beautifingServiceRepository.findAll();

        return ResponseEntity.ok(result);
    }

    //    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/unique-salon")
    public ResponseEntity<List<String>> getUniqueNameOfSalon() {

        List<String> result = beautifingServiceRepository.getUniqueNameOfSalon().get();

        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(result);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/unique-town")
    public ResponseEntity<List<String>> getUniqueTown() {

        List<String> result = beautifingServiceRepository.getUniqueTown().get();

        if (result.size() == 0) {
            return ResponseEntity.notFound().build();
        }


        return ResponseEntity.ok(result);
    }


}
