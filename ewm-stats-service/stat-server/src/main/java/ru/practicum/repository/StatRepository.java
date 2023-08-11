package ru.practicum.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.model.EndpointHit;
import ru.practicum.model.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

public interface StatRepository extends JpaRepository<EndpointHit, Long> {

    @Query("select new ru.practicum.model.ViewStats(e.app, e.uri, COUNT(distinct e.ip)) " +
            "from EndpointHit as e " +
            "where e.timestamp between :start and :end " +
            "and (e.uri in :uris or :uris is null) " +
            "group by e.uri, e.app " +
            "order by COUNT(distinct e.ip)DESC ")
    List<ViewStats> getAllByTimeGroup(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.model.ViewStats(e.uri,e.app,COUNT(e.ip)) " +
            "from EndpointHit as e " +
            "where e.timestamp between :start and :end " +
            "and (e.uri in :uris or :uris is null) " +
            "group by e.uri, e.app " +
            "order by COUNT(e.ip) DESC ")
    List<ViewStats> getAllByTime(LocalDateTime start, LocalDateTime end, List<String> uris);
}
