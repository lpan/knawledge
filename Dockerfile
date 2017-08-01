FROM java:8-alpine
MAINTAINER Lawrence Pan <lawrencefeipan@gmail.com>

ADD target/uberjar/knawledge.jar /knawledge/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/knawledge/app.jar"]
