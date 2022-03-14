package map.locator.adapter;

import com.sun.istack.NotNull;
import map.locator.model.Opinion;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface OpinionRepository {

    List<Opinion> findAll();

    Opinion save(Opinion beautifyingService);

    void deleteById(@NotNull int id);

    Optional<Opinion> findById(int id);


    @Query(value = "select avg(opinion) from opinion where beautifying_service_id = :id ;", nativeQuery = true)
    Optional<Double> getOpinion(int id);


}
