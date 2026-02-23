#FROM amazoncorretto:21 AS builder
#
#WORKDIR /app
#
#RUN yum install -y wget tar gzip && \
#    wget https://archive.apache.org/dist/maven/maven-3/3.9.11/binaries/apache-maven-3.9.11-bin.tar.gz && \
#    tar -xzf apache-maven-3.9.11-bin.tar.gz -C /opt && \
#    ln -s /opt/apache-maven-3.9.11 /opt/maven
#ENV PATH="/opt/maven/bin:${PATH}"
#
#COPY pom.xml .
#RUN mvn dependency:go-offline
#
#COPY src ./src
#
#RUN mvn clean package spring-boot:repackage -DskipTests
#
#FROM amazoncorretto:21
#
#WORKDIR /app
#
#COPY --from=builder /app/target/*.jar app.jar
#
#ENTRYPOINT ["java", "-jar", "app.jar"]
