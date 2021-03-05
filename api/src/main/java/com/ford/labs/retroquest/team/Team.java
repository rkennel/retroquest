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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Team implements Persistable<String> {

    @Id
    @JsonIgnore
    private String uri;

    private String name;

    @JsonIgnore
    private String password;

    @JsonIgnore
    private LocalDate dateCreated;

    private Integer failedAttempts;

    @JsonIgnore
    private LocalDate lastLoginDate;

    private String uiConfig;

    public Team(String uri, String name, String password) {
        this.uri = uri;
        this.name = name;
        this.password = password;
        this.failedAttempts = 0;
//        this.users = new HashSet<>();
    }

    @Override
    public String getId() {
        return this.uri;
    }

    @Override
    public boolean isNew() {
        return uri == null;
    }

    public TeamUIConfig getTeamUIConfig() {
        try {
            ObjectMapper om = new ObjectMapper();
            return om.readValue(uiConfig.getBytes(StandardCharsets.UTF_8),TeamUIConfig.class);
        } catch (IOException e) {
            throw new RuntimeException("Error converting ui config to json");
        }
    }
}
