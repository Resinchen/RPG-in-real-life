package ru.matmech.jCourse.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "QUESTS")
public class Quest {
    public static Quest NullQuest  = new Quest(-1L, "=-=", "---");

    @Id
    @Column(name = "quest_id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "type")
    private String type;
    @Column(name = "answer")
    private String answer;
    @Column(name = "min_level")
    private Integer minLevel;
    @Column(name = "experience")
    private Integer experience;

    @OneToMany(mappedBy = "quest", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<User> users;

    protected Quest() {}

    public Quest(long id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.description = desc;
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Integer getMinLevel() {
        return minLevel;
    }
    public void setMinLevel(Integer minLevel) {
        this.minLevel = minLevel;
    }

    public Integer getExperience() {
        return experience;
    }
    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public List<User> getUsers() {
        return users;
    }
    public void setUsers(List<User> users) {
        this.users = users;
    }

}
