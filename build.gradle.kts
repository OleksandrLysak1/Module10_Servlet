plugins {
    id("java")
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    compileOnly("javax.servlet:javax.servlet-api:4.0.1")
    implementation("org.apache.tomcat.embed:tomcat-embed-core:9.0.78")
    implementation("org.apache.tomcat.embed:tomcat-embed-jasper:9.0.78")
    implementation("org.thymeleaf:thymeleaf:3.1.1.RELEASE")
    implementation("org.thymeleaf:thymeleaf-spring5:3.1.1.RELEASE")
}

tasks.test {
    useJUnitPlatform()
}
