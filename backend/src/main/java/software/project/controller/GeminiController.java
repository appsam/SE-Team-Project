package software.project.controller;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import software.project.dto.GeminiRequest;
import software.project.dto.GeminiResponse;
import software.project.service.GeminiService;

@RestController
@RequestMapping("/api/gemini")
@CrossOrigin(origins = "http://localhost:5173")
public class GeminiController {
    @Autowired
    private final GeminiService geminiService;

    @Autowired
    public GeminiController(GeminiService geminiService) {
        this.geminiService = geminiService;
    }

    @PostMapping("/chat")
    public String generateContent(@RequestBody String text) {
        GeminiRequest.Content.Part part = new GeminiRequest.Content.Part(text);
        GeminiRequest.Content content = new GeminiRequest.Content(Collections.singletonList(part));
        GeminiRequest request = new GeminiRequest(Collections.singletonList(content));

        GeminiResponse response = geminiService.generateContent(request);
        return geminiService.getTextFromResponse(response);
    }

}
