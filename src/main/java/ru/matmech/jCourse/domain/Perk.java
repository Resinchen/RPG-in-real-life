package ru.matmech.jCourse.domain;

import javax.persistence.*;

@Entity
@Table(name = "perks")
public class Perk {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "perk_id")
    private Long id;

    @Column(name = "perk_name")
    private String name;
    @Column(name = "perk_description")
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

}
