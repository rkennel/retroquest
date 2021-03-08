/*
 *  Copyright (c) 2020 Ford Motor Company
 *  All rights reserved.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

import {AfterViewInit, Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {TeamService} from '../teams/services/team.service';
import {DataService} from '../data.service';
import {parseTheme, Themes} from '../domain/Theme';
import {Title} from '@angular/platform-browser';
import {UIConfig} from '../domain/UIConfig';

@Component({
  selector: 'rq-sub-app',
  templateUrl: './sub-app.component.html',
  styleUrls: ['./sub-app.component.scss'],
  host: {
    '[class.dark-theme]': 'darkThemeIsEnabled'
  }
})
export class SubAppComponent implements OnInit, AfterViewInit {

  teamId: string;
  teamName: string;
  teamUIConfig: UIConfig;

  constructor(private activatedRoute: ActivatedRoute,
              private teamService: TeamService,
              private dataService: DataService,
              private titleService: Title) {
  }

  ngOnInit() {

    this.activatedRoute.params.subscribe((params) => {
      this.teamId = params.teamId;
      this.dataService.team.id = this.teamId;

      this.setTeamName();
      this.setTeamUIConfig();
    });
  }

  ngAfterViewInit(): void {
    window.setTimeout(_ => this.loadTheme());
  }

  get theme(): Themes {
    return this.dataService.theme;
  }

  get darkThemeIsEnabled(): boolean {
    return this.dataService.theme === Themes.Dark;
  }

  private loadTheme(): void {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme) {
      this.emitThemeChanged(parseTheme(savedTheme));
    }
  }

  private setTeamName(): void {
    this.teamService.fetchTeamName(this.teamId).subscribe(
      (teamName) => {
        this.teamName = teamName;
        this.dataService.team.name = this.teamName;
        this.titleService.setTitle(this.teamName + ' | RetroQuest');
      }
    );
  }

  emitThemeChanged(theme: Themes) {
    this.dataService.theme = theme;
    this.dataService.themeChanged.emit(theme);
  }

  private setTeamUIConfig() {

    function protectAgainstEmptyColors(specified: string, def: string) {
      return (!specified) || (specified === '') ? def : specified;
    }

    function setGlobalCssColors(uiConfig: UIConfig) {
      document.documentElement.style.setProperty('--column1', protectAgainstEmptyColors(uiConfig.column1Color, UIConfig.DEFAULT.column1Color));
      document.documentElement.style.setProperty('--column2', protectAgainstEmptyColors(uiConfig.column1Color, UIConfig.DEFAULT.column2Color));
      document.documentElement.style.setProperty('--column3', protectAgainstEmptyColors(uiConfig.column3Color, UIConfig.DEFAULT.column3Color));
      document.documentElement.style.setProperty('--column4', protectAgainstEmptyColors(uiConfig.column4Color, UIConfig.DEFAULT.column4Color));
      document.documentElement.style.setProperty('--column5', protectAgainstEmptyColors(uiConfig.column5Color, UIConfig.DEFAULT.column5Color));
      document.documentElement.style.setProperty('--column1-dark', protectAgainstEmptyColors(uiConfig.column1ColorDark, UIConfig.DEFAULT.column1ColorDark));
      document.documentElement.style.setProperty('--column2-dark', protectAgainstEmptyColors(uiConfig.column1ColorDark, UIConfig.DEFAULT.column2ColorDark));
      document.documentElement.style.setProperty('--column3-dark', protectAgainstEmptyColors(uiConfig.column3ColorDark, UIConfig.DEFAULT.column3ColorDark));
      document.documentElement.style.setProperty('--column4-dark', protectAgainstEmptyColors(uiConfig.column4ColorDark, UIConfig.DEFAULT.column4ColorDark));
      document.documentElement.style.setProperty('--column5-dark', protectAgainstEmptyColors(uiConfig.column5ColorDark, UIConfig.DEFAULT.column5ColorDark));
    }

    this.teamService.fetchTeamUiConfig(this.teamId).subscribe(
      (uiConfig) => {

        this.teamUIConfig = uiConfig;
        setGlobalCssColors(uiConfig);
      }
    );
  }
}
