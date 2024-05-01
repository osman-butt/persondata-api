package dk.persondata;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {
    @GetMapping
    public ResponseEntity<HomeResponse> home() {
        return ResponseEntity.ok(new HomeResponse("Welcome to the Person Data API", List.of(
                new HomeResponseEndpoints(
                        "GET /api/person?firstName={firstName}&middleName={middleName}&lastName={lastName}",
                        "Returns the estimated age, gender and nationality based on first or last name.",
                        "firstName and/or lastName."))));
    }

    private record HomeResponse(String message, List<HomeResponseEndpoints> endpoints) {}
    private record HomeResponseEndpoints(String endpoint, String description, String requiredParams) { }
}
