package map.locator.adapter;


import map.locator.model.BeautifyingService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SqlBeautifingServiceRepository extends BeautifingServiceRepository, JpaRepository<BeautifyingService, Integer> {


}
