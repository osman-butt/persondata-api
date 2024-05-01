package dk.persondata.apiService;

import dk.persondata.dto.GenderizeDTO;
import dk.persondata.exception.TooManyRequests;
import dk.persondata.exception.UnprocessableContent;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import reactor.core.publisher.Mono;

import java.util.Objects;

public class ApiServiceSyncImpl<T> implements ApiService<T>{

    private final RestClient restClient;
    private final Class<T> responseType;

    public ApiServiceSyncImpl(String baseUrl, Class<T> responseType) {
        this.restClient = RestClient.create(baseUrl);
        this.responseType = responseType;
    }

    @Override
    public Mono<T> fetch(String name) {
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
                .body(responseType)));
    }
}
