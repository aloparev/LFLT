# LFLT

## deploy

```
switch db
mvn -Dmaven.test.skip=true package
heroku deploy:jar target/lflt-demo-0.0.5-SNAPSHOT.jar --app lflt
```