/*
 * Copyright (c) 2020 Ford Motor Company
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

package com.ford.labs.retroquest.feedback;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/feedback")
@Api(tags = {"Feedback Controller"}, description = "The controller that manages feedback for Retroquest")
public class FeedbackController {

    private FeedbackRepository feedbackRepository;

    public FeedbackController(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @PostMapping
    @ApiOperation(value = "Creates a feedback entry", notes = "saveFeedback")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Created", response = ResponseEntity.class)})
    public ResponseEntity saveFeedback(@RequestBody Feedback feedback) throws URISyntaxException {
        feedback.setDateCreated(LocalDateTime.now());
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return ResponseEntity.created(new URI("/api/feedback/" + savedFeedback.getId())).build();
    }
}
