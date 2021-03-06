package me.kolganov.taskmanager.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@NoArgsConstructor
@Entity(name = "PROJECTS")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "NAME")
    @NotBlank(message = "Project name is required")
    private String name;

    @Column(name = "IDENTIFIER", updatable = false, unique = true)
    @NotBlank(message = "Project identifier is required")
    @Size(min = 4, max = 5, message = "Please use 4 to 5 characters")
    private String identifier;

    @Column(name = "DESCRIPTION")
    @NotBlank(message = "Project description is required")
    private String description;

    @Column(name = "START_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @Column(name = "END_DATE")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

    @Column(name = "CREATE_AT", updatable = false)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    @Column(name = "UPDATE_AT")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updateAt;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "project")
    @JsonIgnore
    private Backlog backlog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private AppUser user;

    @Column(name = "PROJECT_LEADER")
    private String projectLeader;

    @PrePersist
    protected void onCreate() {
        this.createAt = new Date();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updateAt = new Date();
    }
}
