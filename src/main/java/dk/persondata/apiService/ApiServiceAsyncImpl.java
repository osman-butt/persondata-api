package dk.persondata.apiService;

import dk.persondata.exception.TooManyRequests;
import dk.persondata.exception.UnprocessableContent;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.lang.reflect.Constructor;

public class ApiServiceAsyncImpl<T> implements ApiService<T>{
    private final WebClient webClient;
    private final Class<T> responseType;

    public ApiServiceAsyncImpl(String baseUrl, Class<T> responseType) {
        this.webClient = WebClient.create(baseUrl);
        this.responseType = responseType;
    }

    @Override
    public Mono<T> fetch(String name) {
        return webClient.get()
                .uri("?name=" + name)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .onStatus(status -> status.value() == 422, (response) -> {
                    throw new UnprocessableContent("Invalid or missing name parameter");
                })
                .onStatus(status -> status.value() == 429, (response) -> {
                    throw new TooManyRequests("Request limit reached");
                })
                .bodyToMono(responseType);
    }
}
