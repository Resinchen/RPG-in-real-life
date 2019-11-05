package ru.matmech.jCourse.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PERKS")
public class Perk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perk_id")
    private Long id;

    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;

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

    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToMany(mappedBy = "perks", fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStrength() {
        return strength;
    }

    public void setStrength(Integer strength) {
        this.strength = strength;
    }

    public Integer getEndurance() {
        return endurance;
    }

    public void setEndurance(Integer endurance) {
        this.endurance = endurance;
    }

    public Integer getCharisma() {
        return charisma;
    }

    public void setCharisma(Integer charisma) {
        this.charisma = charisma;
    }

    public Integer getIntelligence() {
        return intelligence;
    }

    public void setIntelligence(Integer intelligence) {
        this.intelligence = intelligence;
    }

    public Integer getLucky() {
        return lucky;
    }

    public void setLucky(Integer lucky) {
        this.lucky = lucky;
    }
}
