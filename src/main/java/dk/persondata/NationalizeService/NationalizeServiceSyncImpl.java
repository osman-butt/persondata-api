package dk.persondata.NationalizeService;

import dk.persondata.exception.TooManyRequests;
import dk.persondata.exception.UnprocessableContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Service
@ConditionalOnProperty(value="service.fetch.type",havingValue = "sync")
public class NationalizeServiceSyncImpl implements NationalizeService {
    private final RestClient restClient;

    public NationalizeServiceSyncImpl(@Value("${nationalize.service.base-url}") String baseUrl) {
        this.restClient = RestClient.create(baseUrl);
    }

    public Mono<NationalizeDTO> fetch(String name) {
        return Mono.just(Objects.requireNonNull(restClient.get()
                .uri(uriBuilder -> uriBuilder.queryParam("name", name).build())
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.value() == 422, (request, response) -> {
                    throw new UnprocessableContent("Invalid og missing name parameter");
                })
                .onStatus(status -> status.value() == 429, (request, response) -> {
                    throw new TooManyRequests("Request limit reached");
                })
                .body(NationalizeDTO.class)));
    }
}
