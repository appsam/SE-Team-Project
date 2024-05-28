package software.project.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import  software.project.dto.GeminiRequest;
import  software.project.dto.GeminiResponse;

@Service
public class GeminiService {

    // API URL과 API 키 직접 명시
    private final String apiUrl = "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent?key=AIzaSyAaUnKHvhczOnYcuBw5jyAytzaZvUxi1_E";

    @Autowired
    private final RestTemplate restTemplate;

    @Autowired
    public GeminiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GeminiResponse generateContent(GeminiRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<GeminiRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GeminiResponse> response = restTemplate.exchange(
                apiUrl, HttpMethod.POST, entity, GeminiResponse.class);

        return response.getBody();
    }
    
    public String getTextFromResponse(GeminiResponse response) {
        StringBuilder textBuilder = new StringBuilder();
        for (GeminiResponse.Candidate candidate : response.getCandidates()) {
            if (candidate.getContent() != null && candidate.getContent().getParts() != null) {
                for (GeminiResponse.Candidate.Content.Part part : candidate.getContent().getParts()) {
                    textBuilder.append(part.getText()).append(" ");
                }
            }
        }
        return textBuilder.toString().trim();
    }
}


