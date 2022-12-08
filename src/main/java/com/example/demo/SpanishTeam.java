package com.example.demo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class SpanishTeam {
    @JsonProperty("team_key")
    public String teamKey;
    @JsonProperty("team_name")
    public String teamName;
    @JsonProperty("team_badge")
    public String teamBadge;
    @JsonProperty("players")
    public List players;
    @JsonProperty("coaches")
    public List coaches;
}
