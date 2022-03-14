package map.locator.adapter;


import map.locator.model.BeautifyingServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface SqlBeautifingServiceTypeRepository extends BeautifingServiceTypeRepository, JpaRepository<BeautifyingServiceType, Integer> {


}
