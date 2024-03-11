package com.dkit.oop.sd2.DTOs;

public class Player
{
    private int id;
    private String name;
    private int age;
    private String team;
    private String position;

    public Player(int playerId, String playerName, int playerAge, String playerTeam, String playerPosition)
    {
        this.id = playerId;
        this.name = playerName;
        this.age = playerAge;
        this.team = playerTeam;
        this.position = playerPosition;
    }

    public Player(String playerName, int playerAge, String playerTeam, String playerPosition)
    {
        this.id = 0;
        this.name = playerName;
        this.age = playerAge;
        this.team = playerTeam;
        this.position = playerPosition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", team='" + team + '\'' +
                ", position='" + position + '\'' +
                '}';
    }
}
