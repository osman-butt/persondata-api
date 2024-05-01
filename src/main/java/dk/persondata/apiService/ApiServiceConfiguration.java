package dk.persondata.apiService;

import dk.persondata.dto.AgifyDTO;
import dk.persondata.dto.GenderizeDTO;
import dk.persondata.dto.NationalizeDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiServiceConfiguration {

    @Bean
    @ConditionalOnProperty(name = "async.enabled", havingValue = "true", matchIfMissing = true)
    public ApiServiceAsyncImpl<AgifyDTO> agifyAsyncService(@Value("${agify.service.base-url}") String baseUrl) {
        return new ApiServiceAsyncImpl<>(baseUrl, AgifyDTO.class);
    }

    @Bean
    @ConditionalOnProperty(name = "async.enabled", havingValue = "false")
    public ApiServiceSyncImpl<AgifyDTO> agifySyncService(@Value("${agify.service.base-url}") String baseUrl) {
        return new ApiServiceSyncImpl<>(baseUrl, AgifyDTO.class);
    }

    @Bean
    @ConditionalOnProperty(name = "async.enabled", havingValue = "true", matchIfMissing = true)
    public ApiServiceAsyncImpl<GenderizeDTO> genderizeAsyncService(@Value("${genderize.service.base-url}") String baseUrl) {
        return new ApiServiceAsyncImpl<>(baseUrl, GenderizeDTO.class);
    }

    @Bean
    @ConditionalOnProperty(name = "async.enabled", havingValue = "false")
    public ApiServiceSyncImpl<GenderizeDTO> genderizeSyncService(@Value("${genderize.service.base-url}") String baseUrl) {
        return new ApiServiceSyncImpl<>(baseUrl, GenderizeDTO.class);
    }

    @Bean
    @ConditionalOnProperty(name = "async.enabled", havingValue = "true", matchIfMissing = true)
    public ApiServiceAsyncImpl<NationalizeDTO> nationalizeAsyncService(@Value("${nationalize.service.base-url}") String baseUrl) {
        return new ApiServiceAsyncImpl<>(baseUrl, NationalizeDTO.class);
    }

    @Bean
    @ConditionalOnProperty(name = "async.enabled", havingValue = "false")
    public ApiServiceSyncImpl<NationalizeDTO> nationalizeSyncService(@Value("${nationalize.service.base-url}") String baseUrl) {
        return new ApiServiceSyncImpl<>(baseUrl, NationalizeDTO.class);
    }
}
