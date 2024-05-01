package dk.persondata.AgifyService;

import dk.persondata.exception.TooManyRequests;
import dk.persondata.exception.UnprocessableContent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@ConditionalOnProperty(value="service.fetch.type",havingValue = "async")
public class AgifyServiceAsyncImpl implements AgifyService {
    private final WebClient webClient;

    public AgifyServiceAsyncImpl(@Value("${agify.service.base-url}") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public Mono<AgifyDTO> fetch(String name) {
        return webClient.get()
            .uri("?name=" + name).accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(status -> status.value() == 422, (response) -> {
                throw new UnprocessableContent("Invalid og missing name parameter");
            })
            .onStatus(status -> status.value() == 429, (response) -> {
                throw new TooManyRequests("Request limit reached");
            })
            .bodyToMono(AgifyDTO.class);
    }
}
