/*
 * Copyright © 2018 Ford Motor Company
 * All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ford.labs.retroquest.team;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ford.labs.retroquest.actionitem.ActionItem;
import com.ford.labs.retroquest.actionitem.ActionItemRepository;
import com.ford.labs.retroquest.columntitle.ColumnTitle;
import com.ford.labs.retroquest.columntitle.ColumnTitleRepository;
import com.ford.labs.retroquest.exception.BoardDoesNotExistException;
import com.ford.labs.retroquest.exception.PasswordInvalidException;
import com.ford.labs.retroquest.thought.Thought;
import com.ford.labs.retroquest.thought.ThoughtRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TeamService {
    private final ThoughtRepository thoughtRepository;
    private final ActionItemRepository actionItemRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;
    private final ColumnTitleRepository columnTitleRepository;

    private static final TeamUIConfig DEFAULT_UI_CONFIG = TeamUIConfig.builder()
            .title1Color("#2ecc71")
            .title1ColorDark("#27ae60")
            .title2Color("#3498db")
            .title2ColorDark("#2980b9")
            .title3Color("#e74c3c")
            .title3ColorDark("#f39c12")
            .title4Color("#f1c40f")
            .title4ColorDark("#c0392b")
            .title5Color("#9965F4")
            .title5ColorDark("#D4BFF9")
            .teamLogoUrl("http://retroquest.ford.com/assets/icons/icon-72x72.png")
            .headerHeight("64px")
            .footerImageUrl(null)
            .build();

    public TeamService(
            ThoughtRepository thoughtRepository,
            ActionItemRepository actionItemRepository,
            TeamRepository teamRepository,
            PasswordEncoder passwordEncoder,
            ColumnTitleRepository columnTitleRepository
    ) {
        this.thoughtRepository = thoughtRepository;
        this.actionItemRepository = actionItemRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
        this.columnTitleRepository = columnTitleRepository;
    }

    public String convertTeamNameToURI(String teamName) {
        return teamName.toLowerCase().replace(" ", "-");
    }

    public CsvFile buildCsvFileFromTeam(String team) {
        List<Thought> thoughts = thoughtRepository.findAllByTeamIdAndBoardIdIsNullOrderByTopic(team);
        List<ActionItem> actionItems = actionItemRepository.findAllByTeamIdAndArchivedIsFalse(team);
        return new CsvFile(team, thoughts, actionItems);
    }

    public Team createNewTeam(CreateTeamRequest createTeamRequest) {
        String encryptedPassword = passwordEncoder.encode(createTeamRequest.getPassword());

        return createTeamEntity(createTeamRequest.getName(), encryptedPassword);
    }

    public Team createNewTeam(String name) {
        return createTeamEntity(name, null);
    }

    private Team createTeamEntity(String name, String password) {
        String trimmedName = name.trim();
        String uri = convertTeamNameToURI(trimmedName);
        teamRepository
                .findTeamByUri(uri)
                .ifPresent(s -> {
                    throw new DataIntegrityViolationException(s.getUri());
                });

        Team teamEntity = new Team(uri, trimmedName, password);
        teamEntity.setDateCreated(LocalDate.now());
        try {
            teamEntity.setUiConfig(new ObjectMapper().writeValueAsString(DEFAULT_UI_CONFIG));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        teamEntity = teamRepository.save(teamEntity);
        generateColumns(teamEntity);

        return teamEntity;
    }

    public List<ColumnTitle> generateColumns(Team teamEntity) {

        List<ColumnTitle> columns = new ArrayList<>();

        for(int i=0;i<5;i++){
            ColumnTitle columnTitle = new ColumnTitle();
            columnTitle.setTeamId(teamEntity.getUri());
            columnTitle.setTopic("title"+(i+1));
            columnTitle.setTitle("Title "+(i+1));

            columns.add(columnTitleRepository.save(columnTitle));
        }

        return columns;
    }

    public Team login(LoginRequest loginRequest) {
        Team savedTeam = getTeamByName(loginRequest.getName());

        if (loginRequest.getPassword() == null || !passwordEncoder.matches(loginRequest.getPassword(), savedTeam.getPassword())) {
            Integer failedAttempts = savedTeam.getFailedAttempts() != null ? savedTeam.getFailedAttempts() : 0;
            updateFailedAttempts(savedTeam, failedAttempts + 1);
            throw new PasswordInvalidException();
        }

        savedTeam.setLastLoginDate(LocalDate.now());
        teamRepository.save(savedTeam);
        updateFailedAttempts(savedTeam, 0);
        return savedTeam;
    }

    public Team updatePassword(UpdatePasswordRequest updatePasswordRequest) {
        Team savedTeam = getTeamByUri(updatePasswordRequest.getTeamId());
        if (passwordEncoder.matches(updatePasswordRequest.getPreviousPassword(), savedTeam.getPassword())) {
            String encryptedPassword = passwordEncoder.encode(updatePasswordRequest.getNewPassword());
            savedTeam.setPassword(encryptedPassword);
            return teamRepository.save(savedTeam);
        } else {
            throw new PasswordInvalidException();
        }
    }

    private void updateFailedAttempts(Team savedTeam, int failedAttempts) {
        savedTeam.setFailedAttempts(failedAttempts);
        teamRepository.save(savedTeam);
    }

    public Team getTeamByName(String teamName) {
        Optional<Team> team = teamRepository.findTeamByNameIgnoreCase(teamName.trim());
        if (team.isPresent()) {
            return team.get();
        }
        throw new BoardDoesNotExistException();
    }

    public Team getTeamByUri(String teamUri) {
        Optional<Team> team = teamRepository.findTeamByUri(teamUri.toLowerCase());
        if (team.isPresent()) {
            return team.get();
        }
        throw new BoardDoesNotExistException();
    }

    public int trimAllTeamNames() {
        return teamRepository.trimAllTeamNames();
    }
}
