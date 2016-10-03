FROM java:8-alpine
MAINTAINER Your Name <you@example.com>

ADD target/uberjar/music_shop.jar /music_shop/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/music_shop/app.jar"]
