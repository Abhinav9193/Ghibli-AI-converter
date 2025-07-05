package com.abhinav.GhibliAI.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;


public class TextToImageRequest {

    @JsonProperty("text_prompts")
    private List<TextPrompt> textPrompts;
    private double cfg_scale = 7;
    private int height = 512;
    private int width = 768;
    private int sample = 1;
    private int steps = 30;
    private String style_preset;


    //Inner Class for TextPrompts
    public static class TextPrompt {
        private String text;

        public TextPrompt(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    //For Main Class
    public TextToImageRequest(String text, String style)
    {
        this.textPrompts = List.of(new TextPrompt(text));
        this.style_preset = style;
    }

    public List<TextPrompt> getTextPrompts() {
        return textPrompts;
    }

    public double getCfg_scale() {
        return cfg_scale;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getSample() {
        return sample;
    }

    public int getSteps() {
        return steps;
    }

    public String getStyle_preset() {
        return style_preset;
    }
}
