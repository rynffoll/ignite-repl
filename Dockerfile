FROM adoptopenjdk:11-jdk-hotspot

COPY target/ignite-repl.jar /opt/ignite-repl/

WORKDIR /opt/ignite-repl

ENV JAVA_OPTS="--add-exports=java.base/jdk.internal.misc=ALL-UNNAMED \
        --illegal-access=permit \
        --add-exports=java.base/sun.nio.ch=ALL-UNNAMED \
        --add-exports=java.management/com.sun.jmx.mbeanserver=ALL-UNNAMED \
        --add-exports=jdk.internal.jvmstat/sun.jvmstat.monitor=ALL-UNNAMED \
        --add-exports=java.base/sun.reflect.generics.reflectiveObjects=ALL-UNNAMED"

CMD ["java", "-cp", "ignite-repl.jar", "clojure.main", "-m", "ignite_repl.core"]
