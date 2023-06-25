FROM maven:3.8.6-openjdk-8
RUN apt-get update; apt install -y \
    curl \
    jq
COPY pom.xml /app/
COPY src /app/src/
COPY healthcheck.sh /app/
WORKDIR /app/
ENTRYPOINT ["/bin/sh"]
CMD ["healthcheck.sh"]