package com.azure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "question_mapping")
public class QuestionSectionUserMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String questionCode;
    @Column(name = "`index`")
    private int index;
    @ManyToOne
    @JoinColumn(name = "sub_section_id")
    private SubSection subSection;

}