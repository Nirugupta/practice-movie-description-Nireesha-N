package com.practicespring.movie.services;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    public String generateMovieDescription(String movieTitle) {
        try {
            Client client = new Client();
            String prompt = "Write a short, engaging, 2-sentence description for the movie: " + movieTitle;

            GenerateContentResponse response = client.models.generateContent(
                    "gemini-3.8-flash",
                    prompt,
                    null
            );

            return response.text();
        } catch (Exception e) {
            e.printStackTrace();
            return "Could not generate description due to an API error.";
        }
    }
}