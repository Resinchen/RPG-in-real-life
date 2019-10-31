package ru.matmech.jCourse.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "players")
public class Player {
    //TODO добавить навыки
    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", level=" + level +
                ", experience=" + experience +
                ", strength=" + strength +
                ", endurance=" + endurance +
                ", charisma=" + charisma +
                ", intelligence=" + intelligence +
                ", lucky=" + lucky +
                "\n--------------------------\n" +
                "free points: " + freePoints +
                '}';
    }

    @Id
    @Column(name = "player_id")
    private Long id;

    @Column(name = "player_name")
    private String name;

    @Column(name = "level")
    private Integer level;

    @Column(name = "experience")
    private Integer experience;

    @Column(name = "free_points")
    private Integer freePoints;

    //stats
    @Column(name = "strength")
    private Integer strength;
    @Column(name = "endurance")
    private Integer endurance;
    @Column(name = "charisma")
    private Integer charisma;
    @Column(name = "intelligence")
    private Integer intelligence;
    @Column(name = "lucky")
    private  Integer lucky;

    protected Player() {}

    public Player(Long id, String name) {
        this.id = id;
        this.name = name;
        this.experience = 0;
        this.freePoints = 5;
        this.level = 1;
        this.strength = 5;
        this.endurance = 5;
        this.charisma = 5;
        this.intelligence = 5;
        this.lucky = 5;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
    public void addExperience(Integer deltaExperience) {
        this.experience = deltaExperience;
    }

    public Integer getFreePoints() {
        return freePoints;
    }

    public void setFreePoints(Integer freePoints) {
        this.freePoints = freePoints;
    }
    public void addFreePoints(Integer deltaFreePoints) {
        this.freePoints += deltaFreePoints;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }
    public void addStrength(Integer deltaStrength) {
        this.strength += deltaStrength;
    }

    public Integer getEndurance() {
        return endurance;
    }

    public void setEndurance(Integer endurance) {
        this.endurance = endurance;
    }
    public void addEndurance(Integer deltaEndurance) {
        this.endurance += deltaEndurance;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
    }
    public void addCharisma(Integer deltaCharisma) {
        this.charisma += deltaCharisma;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }
    public void addIntelligence(Integer deltaIntelligence) {
        this.intelligence += deltaIntelligence;
    }

    public Integer getLucky() {
        return lucky;
    }

    public void setLucky(Integer lucky) {
        this.lucky = lucky;
    }
    public void addLucky(Integer deltaLucky) {
        this.lucky += deltaLucky;
    }
}
