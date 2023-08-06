package repository;


import dto.EndpointHitDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<EndpointHitDto, Long> {
    @Query("select COUNT(e) " +
            "from EndpointHitDto as e " +
            "where e.timestamp >= :start " +
            "and e.timestamp <= :end " +
            "and e.uri in :uri " +
            "group by e.ip")
    Long countAllGroup(LocalDateTime start, LocalDateTime end, String uri);

    @Query("select COUNT(e) " +
            "from EndpointHitDto as e " +
            "where e.timestamp >= :start " +
            "and e.timestamp <= :end " +
            "and e.uri in :uri")
    Long countAll(LocalDateTime start, LocalDateTime end, String uri);

    @Query("select e " +
            "from EndpointHitDto as e " +
            "where e.timestamp >= :start " +
            "and e.timestamp <= :end " +
            "and e.uri in :uris " +
            "group by e.ip")
    List<EndpointHitDto> getAllByTimeGroup(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select e " +
            "from EndpointHitDto as e " +
            "where e.timestamp >= :start " +
            "and e.timestamp <= :end " +
            "and e.uri in :uris")
    List<EndpointHitDto> getAllByTime(LocalDateTime start, LocalDateTime end, List<String> uris);
}
