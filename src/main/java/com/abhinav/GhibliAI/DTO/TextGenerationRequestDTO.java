package com.abhinav.GhibliAI.DTO;

import java.util.Objects;

public class TextGenerationRequestDTO {

    private String prompt;
    private String style;

    // Default constructor
    public TextGenerationRequestDTO() {
    }

    // Parameterized constructor
    public TextGenerationRequestDTO(String prompt, String style) {
        this.prompt = prompt;
        this.style = style;
    }

    // Getters and Setters
    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    // toString method
    @Override
    public String toString() {
        return "TextGenerationRequestDTO{" +
                "prompt='" + prompt + '\'' +
                ", style='" + style + '\'' +
                '}';
    }

    // equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TextGenerationRequestDTO)) return false;
        TextGenerationRequestDTO that = (TextGenerationRequestDTO) o;
        return Objects.equals(prompt, that.prompt) &&
                Objects.equals(style, that.style);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prompt, style);
    }
}
