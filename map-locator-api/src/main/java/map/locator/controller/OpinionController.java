package map.locator.controller;

import map.locator.Request.OpinionRequest;
import map.locator.adapter.OpinionRepository;
import map.locator.model.Opinion;
import map.locator.service.BeautifyingServiceMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@Controller
@CrossOrigin
@RequestMapping("/opinion")
public class OpinionController {

    private final OpinionRepository opinionRepository;
    BeautifyingServiceMapper beautifyingServiceMapper;

    public OpinionController(OpinionRepository opinionRepository, BeautifyingServiceMapper beautifyingServiceMapper) {
        this.opinionRepository = opinionRepository;
        this.beautifyingServiceMapper = beautifyingServiceMapper;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    ResponseEntity<Opinion> createBeautifyingService(@RequestBody OpinionRequest opinion) {
        Opinion opinionToSave = beautifyingServiceMapper.toOpinion(opinion);
        Opinion result = opinionRepository.save(opinionToSave);
        return ResponseEntity.created(URI.create("/" + result.getId())).body(result);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> delete(@PathVariable Integer id) {
        opinionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
