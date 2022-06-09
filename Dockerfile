FROM docker-registry.checorod.com:1336/infra/img-javaruntime-11:latest

MAINTAINER Sergio Rodriguez (srodriguez@checorod.com)

# ------------------------
# Expose Ports for app communication
# ------------------------

EXPOSE 8080

# ------------------------
# Setup pipeline Automation vars
# ------------------------


ARG packageFileName

ENV TEMP="/tmp"
ENV packageFileNameEnv ${packageFileName}

# ------------------------
# Add application to image
# ------------------------
ADD ["./target/${packageFileName}", "/opt/${packageFileName}"]


# ------------------------
# Define container startup parameters
# ------------------------

#ENTRYPOINT java -Xms128m -Xmx1500m -Dfile.encoding=UTF-8 -jar /opt/$packageFileNameEnv server $configPathEnv/config.yml
#ENTRYPOINT java -Xms128m -Xmx1500m -Dfile.encoding=UTF-8 -jar /opt/$packageFileNameEnv --spring.config.location=$configPathEnv/config.yml
#ENTRYPOINT java -Xms128m -Xmx1500m -Dfile.encoding=UTF-8 -jar /opt/$packageFileNameEnv --spring.config.location=/opt/appconfigurationmanagement/config.yml
ENTRYPOINT java -Xms128m -Xmx1500m -Dfile.encoding=UTF-8 -jar /opt/$packageFileNameEnv