FROM eclipse-temurin:21-jdk AS build
WORKDIR /app
COPY src/ src/
COPY lib/ lib/
RUN javac -cp lib/jansi-2.4.1.jar src/*.java -d out

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/out/ out/
COPY --from=build /app/lib/ lib/
ENV TERM=xterm-256color
ENTRYPOINT ["java", "-cp", "out:lib/jansi-2.4.1.jar", "Main"]

