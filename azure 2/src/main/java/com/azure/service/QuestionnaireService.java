package com.azure.service;

import com.azure.entity.*;
import com.azure.payload.*;
import com.azure.repository.QuestionnaireRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionnaireService {
    @Autowired
    private QuestionnaireRepository questionnaireRepository;

    public void saveQuestionnaire(MultipartFile file) throws IOException {
        // Read JSON data from the uploaded file
        ObjectMapper objectMapper = new ObjectMapper();
        QuestionnaireDTO questionnaireDTO = objectMapper.readValue(file.getInputStream(), QuestionnaireDTO.class);

        Questionnaire questionnaire = convertToEntity(questionnaireDTO);
        questionnaireRepository.save(questionnaire);
    }


    private Section convertToSection(SectionDTO sectionDTO) {
        Section section = new Section();
        section.setType(sectionDTO.getType());
        section.setText(sectionDTO.getText());
        section.setName(sectionDTO.getName());

        // Convert SubSectionDTO to SubSection and set it in Section entity
        List<SubSection> subSections = sectionDTO.getSubSectionList() != null ?
                sectionDTO.getSubSectionList().stream()
                .map(this::convertToSubSection)
                .collect(Collectors.toList()) :
                Collections.emptyList();

        section.setSubSectionList(subSections);

        return section;
    }

    private SubSection convertToSubSection(SubSectionDTO subSectionDTO) {
        SubSection subSection = new SubSection();
        subSection.setSection(subSectionDTO.getSection());
        subSection.setType(subSectionDTO.getType());
        subSection.setText(subSectionDTO.getText());
        subSection.setName(subSectionDTO.getName());

        // Convert QuestionSectionUserMappingDTO to QuestionSectionUserMapping and set it in SubSection entity
        List<QuestionSectionUserMapping> mappings = subSectionDTO.getQuestionSectionUserMapping() !=null ?
        subSectionDTO.getQuestionSectionUserMapping().stream()
                .map(this::convertToQuestionSectionUserMapping)
                .collect(Collectors.toList()) :
                Collections.emptyList();

        subSection.setQuestionSectionUserMapping(mappings);
        List<Conditions> mappings2 = subSectionDTO.getConditions()!= null ?
                subSectionDTO.getConditions()
                .stream()
                .map(this::convertToConditions)
                .collect(Collectors.toList()) :
                Collections.emptyList();
                ;
        subSection.setConditions(mappings2);

        return subSection;
    }

    private QuestionSectionUserMapping convertToQuestionSectionUserMapping(QuestionSectionUserMappingDTO mappingDTO) {
        QuestionSectionUserMapping mapping = new QuestionSectionUserMapping();
        mapping.setQuestionCode(mappingDTO.getQuestionCode());
        mapping.setIndex(mappingDTO.getIndex());

        return mapping;
    }
    private Conditions convertToConditions(ConditionsDTO conditionsDTO){
        Conditions conditions = new Conditions();
        conditions.setConditions(conditionsDTO.getConditions());
        conditions.setConditionsType(conditionsDTO.getConditionsType());
        conditions.setConditionsAnswer(conditionsDTO.getConditionsAnswer());
        return conditions;
    }
    private Questionnaire convertToEntity(QuestionnaireDTO questionnaireDTO) {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setName(questionnaireDTO.getName());
        questionnaire.setType(questionnaireDTO.getType());
        questionnaire.setText(questionnaireDTO.getText());
        // Convert SectionDTO to Section and set it in Questionnaire entity
        List<Section> sections = questionnaireDTO.getSectionQList().stream()
                .map(this::convertToSection)
                .collect(Collectors.toList());

        questionnaire.setSectionQList(sections);

        return questionnaire;
    }
}
