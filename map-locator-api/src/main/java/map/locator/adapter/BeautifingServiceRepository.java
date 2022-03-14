package map.locator.adapter;

import com.sun.istack.NotNull;
import map.locator.model.BeautifyingService;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BeautifingServiceRepository {

    List<BeautifyingService> findAll();

    BeautifyingService save(BeautifyingService beautifyingService);

    void deleteById(@NotNull int id);

    boolean existsById(Integer id);

    @Query(value = "SELECT * FROM beautifying_service WHERE type_of_service = :name" +
            " and ST_Intersects(point, ST_MakeEnvelope(:lonTop, :latTop, :lonBtm, :latBtm, 4326))", nativeQuery = true)
    List<BeautifyingService> getBeautifingService(String name, double latBtm, double lonBtm, double latTop, double lonTop);

    @Query(value = "SELECT * FROM beautifying_service WHERE type_of_service = :name and st_intersects(ST_GeomFromText(:polygon, 4326), point) = true",
            nativeQuery = true)
    List<BeautifyingService> getBeautifingService(String name, String polygon);

    @Query(value = "SELECT * FROM beautifying_service WHERE type_of_service = :name and st_intersects(ST_GeomFromText(:polygon, 4326), point) = true", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceFromBounds(String name, String polygon);

    Optional<BeautifyingService> findById(int id);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and ST_Intersects(point, ST_MakeEnvelope(:lonTop, :latTop, :lonBtm, :latBtm, 4326)) and name = :treatment", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByTreatment(String name, double latBtm, double lonBtm,
                                                             double latTop, double lonTop, String treatment);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and ST_Intersects(point, ST_MakeEnvelope(:lonTop, :latTop, :lonBtm, :latBtm, 4326))" +
            " and bst.price >= :priceFrom group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByPriceFrom(String name, double latBtm, double lonBtm,
                                                             double latTop, double lonTop, int priceFrom);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and ST_Intersects(point, ST_MakeEnvelope(:lonTop, :latTop, :lonBtm, :latBtm, 4326)) and bst.price <= :priceTo group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByPriceTo(String name, double latBtm, double lonBtm,
                                                           double latTop, double lonTop, int priceTo);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and ST_Intersects(point, ST_MakeEnvelope(:lonTop, :latTop, :lonBtm, :latBtm, 4326)) " +
            "and name = :treatment and bst.price >= :priceFrom and bst.price <= :priceTo group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByFullOption(String name, double latBtm, double lonBtm,
                                                              double latTop, double lonTop, String treatment, int priceFrom, int priceTo);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and ST_Intersects(point, ST_MakeEnvelope(:lonTop, :latTop, :lonBtm, :latBtm, 4326))" +
            "and name = :treatment and bst.price <= :priceTo group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByTreatmentAndPriceTo(String name, double latBtm, double lonBtm,
                                                                       double latTop, double lonTop, String treatment, int priceTo);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and ST_Intersects(point, ST_MakeEnvelope(:lonTop, :latTop, :lonBtm, :latBtm, 4326)) " +
            "and name = :treatment and bst.price >= :priceFrom group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByTreatmentAndPriceFrom(String name, double latBtm, double lonBtm,
                                                                         double latTop, double lonTop, String treatment, int priceFrom);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and ST_Intersects(point, ST_MakeEnvelope(:lonTop, :latTop, :lonBtm, :latBtm, 4326))" +
            " and bst.price >= :priceFrom and bst.price <= :priceTo group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByPriceFromAndPriceTo(String name, double latBtm, double lonBtm,
                                                                       double latTop, double lonTop, int priceFrom, int priceTo);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and ST_Intersects(point, ST_MakeEnvelope(:lonTop, :latTop, :lonBtm, :latBtm, 4326))" +
            "and name = :treatment  and bst.price >= :priceFrom and bst.price <= :priceTo group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByTreatmentPriceFromAndPriceTo(String name, double latBtm, double lonBtm,
                                                                                double latTop, double lonTop, String treatment, int priceFrom, int priceTo);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name   and st_intersects(ST_GeomFromText(:polygon, 4326), point) = true and name = :treatment", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByTreatmentAndUserLocalization(String name, String polygon, String treatment);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and st_intersects(ST_GeomFromText(:polygon, 4326), point) = true and bst.price >= :priceFrom group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByPriceFromAndUserLocalization(String name, String polygon, int priceFrom);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and st_intersects(ST_GeomFromText(:polygon, 4326), point) = true and bst.price <= :priceTo group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByPriceToAndUserLocalization(String name, String polygon, int priceTo);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and st_intersects(ST_GeomFromText(:polygon, 4326), point) = true " +
            "and name = :treatment and bst.price >= :priceFrom and bst.price <= :priceTo group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByFullOptionAndUserLocalization(String name, String polygon,
                                                                                 String treatment, int priceFrom, int priceTo);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and st_intersects(ST_GeomFromText(:polygon, 4326), point) = true " +
            " and name = :treatment and bst.price <= :priceTo group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByTreatmentAndPriceToAndUserLocalization(String name, String polygon, String treatment, int priceTo);


    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and st_intersects(ST_GeomFromText(:polygon, 4326), point) = true and name = :treatment and bst.price >= :priceFrom group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByTreatmentAndPriceFromAndUserLocalization(String name, String polygon, String treatment, int priceFrom);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and st_intersects(ST_GeomFromText(:polygon, 4326), point) = true " +
            " and bst.price >= :priceFrom and bst.price <= :priceTo group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByPriceFromAndPriceToAndUserLocalization(String name, String polygon, int priceFrom, int priceTo);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and st_intersects(ST_GeomFromText(:polygon, 4326), point) = true" +
            "and name = :treatment  and bst.price >= :priceFrom and bst.price <= :priceTo group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByTreatmentPriceFromAndPriceToAndUserLocalization(String name, String polygon,
                                                                                                   String treatment, int priceFrom, int priceTo);

    @Query(value = "select bs.*  from beautifying_service bs \n" +
            "inner join beautifying_service_type bst on bs.id = bst.beautifying_service_id\n" +
            "where type_of_service = :name and st_intersects(ST_GeomFromText(:polygon, 4326), point) = true and name = :treatment and bst.price >= :priceFrom and bst.price <= :priceTo group by bs.id", nativeQuery = true)
    List<BeautifyingService> getBeautifingServiceByTreatmentAndPriceFromAndPriceToAndDistanceToAndUserLocalization(String name, String polygon,

                                                                                                                   String treatment, int priceFrom, int priceTo);

    @Query(value = "select distinct(name_of_salon) from beautifying_service", nativeQuery = true)
    Optional<List<String>> getUniqueNameOfSalon();

    @Query(value = "select distinct(town) from beautifying_service", nativeQuery = true)
    Optional<List<String>> getUniqueTown();

    @Query(value = "select id from beautifying_service where name_of_salon = :name and town= :town ", nativeQuery = true)
    Optional<Integer> searchByNameAndTown(String name, String town);

}
