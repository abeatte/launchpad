# Launchpad
A personal API. 

# Start
1. Compile your project
    - run mvn package
2. Update/Init your db
    - run java -jar target/launchpad-{version}.jar serve {config-file}.yml
3. Check the status of your DB
    - run java -jar target/launchpad-{version}.jar db status {config-file}.yml
4. Run your project
    - run java -jar target/launchpad-{version}.jar server {config-file}.yml

# References
http://www.dropwizard.io/
https://github.com/remmelt/dropwizard-oauth2-provider
