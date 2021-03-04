## Setting Up Google Cloud Instance
### Setup Google Project
We recommend using the project ID "retroquest".  
![create project](./images/google_create_project.png)

### App Engine - Create New Project
- Java
- Standard

![create app](./images/google_create_app_1.png)

![create app](./images/google_create_app_2.png)

### Create New SQL Instance
- MySQL 2nd Gen 5.7
- Create Database

![create sql](./images/google_create_sql.png)

![create database](./images/google_create_database.png)

### Enable Cloud SQL Admin API
In order to connect directly to MySQL database from app engine, you need to [Enable the API](https://console.cloud.google.com/flows/enableapi?apiid=sqladmin&redirect=https://console.cloud.google.com&_ga=2.76411670.-2090376866.1552752988)
## Setting up build scripts

### app.yaml
To deploy to google app engine, we will need to create an app.yaml file.  Rename sample.app.yaml in the github repository to app.yaml.

Next, we will need to configure the environment variables for connecting to the database.
```
env_variables:
  GCP_INSTANCE: <GCP_INSTANCE>
  SPRING_DATASOURCE_USERNAME: <DB USER NAME>
  SPRING_DATASOURCE_PASSWORD: <DB PASSWORD>
  SPRING_DATASOURCE_URL: jdbc:mysql://<GOOGLE DB INSTANCE NAME>
  SPRING_DATASOURCE_NAME: retroquest
  SPRING_DATASOURCE_DRIVER_CLASS_NAME: com.google.cloud.sql.mysql.SocketFactory
```

Note, in the example above:
- database name: retroquest
- project id: ann arbor
- region: us-central1
- sql instance name: ascot
- The database username has DDL create permissions.  This is necessary for Flyway scripts to create the database tables.
## Deploying
### Authenticate to Google Cloud
`gcloud auth application-default login`
### Deploy Application
`./gradlew withGoogleMySql appEngineDeploy -Dgprojectid=retroquest -Dgversion=<version>`

Note:
- This assumes you are using Google Cloud SQL MySQL for database.  Consult [withDb](https://github.com/rkennel/withDb) for use of alternate databases
- This assumes your project name is retroquest.  If not, change accordingly
- You will need to provide version number for the deploy e.g. 2-0-0.
