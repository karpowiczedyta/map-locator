package map.locator.adapter;


import map.locator.model.Opinion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SqlOpinionRepository extends OpinionRepository, JpaRepository<Opinion, Integer> {


}
