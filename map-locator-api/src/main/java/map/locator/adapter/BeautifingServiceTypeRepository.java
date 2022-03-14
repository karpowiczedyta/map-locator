package map.locator.adapter;

import com.sun.istack.NotNull;
import map.locator.model.BeautifyingServiceType;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BeautifingServiceTypeRepository {

    List<BeautifyingServiceType> findAll();

    BeautifyingServiceType save(BeautifyingServiceType beautifyingService);

    void deleteById(@NotNull int id);

    Optional<BeautifyingServiceType> findById(int id);

    boolean existsById(Integer id);

    @Query(value = "SELECT * FROM beautifying_service_type WHERE beautifying_service_id = :id ;", nativeQuery = true)
    List<BeautifyingServiceType> getTypeOfBeautifingService(int id);


}
