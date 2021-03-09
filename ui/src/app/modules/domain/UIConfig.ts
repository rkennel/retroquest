export class UIConfig {

  static CLASSIC: UIConfig = new UIConfig(
    '#2ecc71',
    '#3498db',
    '#e74c3c',
    '#f1c40f',
    '#9965F4',
    '#27ae60',
    '#2980b9',
    '#c0392b',
    '#f39c12',
    '#7E3FF2'
  );


  static FORD: UIConfig = new UIConfig(
    '#092A46',
    '#0276b3',
    '#555555',
    '#b0b0b0',
    '#242424',
    '#6a86a0',
    '#51c1ee',
    '#b0b0b0',
    '#d4d4d4',
    '#e3e3e3'
  );

  static ANN_ARBOR: UIConfig = new UIConfig(
    '#FFCB05',
    '#00274C',
    '#FFCB05',
    '#00274C',
    '#FFCB05',
    '#ffd844',
    '#1c5284',
    '#ffd844',
    '#1c5284',
    '#ffd844'
  );

  static LANSING: UIConfig = new UIConfig(
    '#18453B',
    '#0DB14B',
    '#97A2A2',
    '#F08521',
    '#008183',
    '#55afa6',
    '#6cc982',
    '#b8bcbc',
    '#faf27c',
    '#4dced2'
  );

  static USA: UIConfig = new UIConfig(
    '#b22234',
    '#3c3b6e',
    '#b22234',
    '#3c3b6e',
    '#b22234',
    '#de7581',
    '#8389af',
    '#de7581',
    '#8389af',
    '#de7581'
  );

  static UK: UIConfig = new UIConfig(
    '#012169',
    '#c8102e',
    '#012169',
    '#c8102e',
    '#012169',
    '#6e80ba',
    '#e56d77',
    '#6e80ba',
    '#e56d77',
    '#6e80ba'
  );

  static GERMANY: UIConfig = new UIConfig(
    '#000000',
    '#dd0000',
    '#ffce00',
    '#000000',
    '#dd0000',
    '#c4c4c4',
    '#ff8364',
    '#ffe17c',
    '#c4c4c4',
    '#ff8364'
  );

  static SPAIN: UIConfig = new UIConfig(
    '#aa151b',
    '#f1bf00',
    '#aa151b',
    '#f1bf00',
    '#aa151b',
    '#e34d4c',
    '#f6da79',
    '#e34d4c',
    '#f6da79',
    '#e34d4c'
  );

  static CHINA: UIConfig = new UIConfig(
    '#df2407',
    '#ffdf00',
    '#df2407',
    '#ffdf00',
    '#df2407',
    '#ff7e5e',
    '#fcf267',
    '#ff7e5e',
    '#fcf267',
    '#ff7e5e'
  );

  static BRAZIL: UIConfig = new UIConfig(
    '#009c3b',
    '#ffdf00',
    '#002776',
    '#ffdf00',
    '#009c3b',
    '#6bc47d',
    '#fcf267',
    '#7287c4',
    '#fcf267',
    '#6bc47d'
  );

  static ARGENTINA: UIConfig = new UIConfig(
    '#75aadb',
    '#f6b40e',
    '#843511',
    '#75aadb',
    '#f6b40e',
    '#98c2e6',
    '#f8d653',
    '#d07f5c',
    '#98c2e6',
    '#f8d653'
  );

  static GREECE: UIConfig = new UIConfig(
    '#0d5eaf',
    '#c2c2c2',
    '#0d5eaf',
    '#c2c2c2',
    '#0d5eaf',
    '#62aee8',
    '#c2c2c2',
    '#62aee8',
    '#c2c2c2',
    '#62aee8'
  );

  static DEFAULT: UIConfig = UIConfig.GREECE;

  column1Color = '';
  column2Color = '';
  column3Color = '';
  column4Color = '';
  column5Color = '';
  column1ColorDark = '';
  column2ColorDark = '';
  column3ColorDark = '';
  column4ColorDark = '';
  column5ColorDark = '';


  constructor(column1Color?: string, column2Color?: string, column3Color?: string, column4Color?: string, column5Color?: string,
              column1ColorDark?: string, column2ColorDark?: string, column3ColorDark?: string, column4ColorDark?: string, column5ColorDark?: string) {
    this.column1Color = column1Color;
    this.column2Color = column2Color;
    this.column3Color = column3Color;
    this.column4Color = column4Color;
    this.column5Color = column5Color;
    this.column1ColorDark = column1ColorDark;
    this.column2ColorDark = column2ColorDark;
    this.column3ColorDark = column3ColorDark;
    this.column4ColorDark = column4ColorDark;
    this.column5ColorDark = column5ColorDark;
  }


}
