package ru.practicum.client;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.dto.EndpointHitDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Service
public class StatClient extends Client {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Autowired
    public StatClient(@Value("${stat-server.url}") String serverUrl, RestTemplateBuilder builder) {
        super(builder.uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build());
    }

    public ResponseEntity<Object> getStats(LocalDateTime start, LocalDateTime end, @Nullable List<String> uris,
                                           @Nullable Boolean unique) {
        Map<String, Object> parameters = new java.util.HashMap<>(Map.of(
                "start", start.format(dateTimeFormatter),
                "end", end.format(dateTimeFormatter)));
        StringBuilder path = new StringBuilder("/stats?start={start}&end={end}");
        if (uris != null && !uris.isEmpty()) {
            String manyUris = String.join("&uris=", uris);
            parameters.put("uris", manyUris);
            path.append("&uris={uris}");
        }
        if (unique != null) {
            parameters.put("unique", unique);
            path.append("&unique={unique}");
        }
        return get(path.toString(), parameters);
    }

    public ResponseEntity<Object> saveStats(EndpointHitDto endpointHitDto) {
        return post("/hit", endpointHitDto);
    }

}
