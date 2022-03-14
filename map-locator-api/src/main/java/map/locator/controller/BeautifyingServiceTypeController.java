package map.locator.controller;


import map.locator.Request.BeautifyingServiceTypeRequest;
import map.locator.adapter.BeautifingServiceTypeRepository;
import map.locator.model.BeautifyingServiceType;
import map.locator.service.BeautifyingServiceMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@CrossOrigin
@RequestMapping("/beautifying-service-type")
public class BeautifyingServiceTypeController {

    private final BeautifingServiceTypeRepository beautifyingServiceTypeRepository;
    private final BeautifyingServiceMapper beautifyingServiceMapper;

    public BeautifyingServiceTypeController(BeautifingServiceTypeRepository beautifyingServiceTypeRepository, BeautifyingServiceMapper beautifyingServiceMapper) {
        this.beautifyingServiceTypeRepository = beautifyingServiceTypeRepository;
        this.beautifyingServiceMapper = beautifyingServiceMapper;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    ResponseEntity<BeautifyingServiceType> createBeautifyingService(@RequestBody BeautifyingServiceTypeRequest beautifyingServiceType) {
        System.out.println("jestem type");
        BeautifyingServiceType beautifyingServiceTypeToSave = beautifyingServiceMapper.toBeautifyingServiceType(beautifyingServiceType);
        BeautifyingServiceType result = beautifyingServiceTypeRepository.save(beautifyingServiceTypeToSave);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    ResponseEntity<?> updateTask(@PathVariable int id, @RequestBody BeautifyingServiceTypeRequest toUpdate) {
        if (!beautifyingServiceTypeRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        beautifyingServiceTypeRepository.findById(id)
                .ifPresent(beautifyingService -> {
                    beautifyingService.updateFrom(toUpdate);
                    beautifyingServiceTypeRepository.save(beautifyingService);
                });
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id) {
        beautifyingServiceTypeRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
