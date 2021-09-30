FROM alpine:3.14 as builder
RUN apk --no-cache add openjdk8 maven
COPY . .
RUN mvn package

FROM alpine:3.14
WORKDIR /swissre
RUN apk --no-cache add openjdk8
COPY --from=builder /src/main/resources/*.csv .
COPY --from=builder /target/Llamaland-*-jar-with-dependencies.jar .
CMD ["sh", "-c" ,"java -jar Llamaland-*-jar-with-dependencies.jar -c citizens.csv -u unsubscribed_citizens.csv"]