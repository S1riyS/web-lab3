plugins {
    java
    war
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    implementation("com.google.guava:guava:32.1.1-jre")

    implementation("jakarta.enterprise:jakarta.enterprise.cdi-api:3.0.0")
    implementation("jakarta.persistence:jakarta.persistence-api:3.0.0")
    implementation("jakarta.faces:jakarta.faces-api:4.0.1")
    implementation("jakarta.servlet:jakarta.servlet-api:5.0.0")
    implementation("org.glassfish:jakarta.faces:3.0.3")
    
    // https://mvnrepository.com/artifact/io.freefair.gradle/lombok-plugin
    compileOnly("org.projectlombok:lombok:1.18.22")
    annotationProcessor("org.projectlombok:lombok:1.18.22")

    // https://mavenlibs.com/maven/dependency/org.primefaces/primefaces
    implementation("org.primefaces:primefaces:13.0.0")

    implementation("org.hibernate.orm:hibernate-core:6.6.1.Final")
    implementation("org.postgresql:postgresql:42.7.4")
}