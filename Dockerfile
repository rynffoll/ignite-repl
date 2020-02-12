FROM adoptopenjdk:11-jre-hotspot

COPY target/ignite-repl.jar /opt/ignite-repl/

WORKDIR /opt/ignite-repl

CMD ["java", "-cp", "ignite-repl.jar", "clojure.main", "-m", "ignite_repl.core"]
