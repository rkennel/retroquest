package com.ford.labs.retroquest.team;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class TeamUIConfig {

    private String title1Color;
    private String title1ColorDark;
    private String title2Color;
    private String title2ColorDark;
    private String title3Color;
    private String title3ColorDark;
    private String title4Color;
    private String title4ColorDark;
    private String title5Color;
    private String title5ColorDark;

    private String teamLogoUrl;
    private String footerImageUrl;

    private String headerHeight;
}
