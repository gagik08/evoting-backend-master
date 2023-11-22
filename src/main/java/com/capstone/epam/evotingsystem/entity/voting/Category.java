package com.capstone.epam.evotingsystem.entity.voting;


import com.capstone.epam.evotingsystem.entity.voting.referendum.Referendum;
import com.capstone.epam.evotingsystem.entity.voting.survey.SociologicalSurvey;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "category_title")
    private String categoryTitle;

    @Column(name = "category_description", length = 300)
    private String categoryDescription;

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<SociologicalSurvey> sociologicalSurveys = new HashSet<>();

    @OneToMany(mappedBy = "category", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private Set<Referendum> referendums = new HashSet<>();



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId) && Objects.equals(categoryTitle, category.categoryTitle) && Objects.equals(categoryDescription, category.categoryDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(categoryId, categoryTitle, categoryDescription);
    }

    public Category() {
    }


    public Category(String categoryTitle, String categoryDescription) {
        this.categoryTitle = categoryTitle;
        this.categoryDescription = categoryDescription;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryTitle() {
        return categoryTitle;
    }

    public void setCategoryTitle(String title) {
        this.categoryTitle = title;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String description) {
        this.categoryDescription = description;
    }

    public Set<SociologicalSurvey> getSociologicalSurveys() {
        return sociologicalSurveys;
    }

    public void setSociologicalSurveys(Set<SociologicalSurvey> sociologicalSurveys) {
        this.sociologicalSurveys = sociologicalSurveys;
    }

    public Set<Referendum> getReferendums() {
        return referendums;
    }

    public void setReferendums(Set<Referendum> referendums) {
        this.referendums = referendums;
    }

}
