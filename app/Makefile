.PHONY: build test checkstyle clean run-dist

build:
	./gradlew build

test:
	./gradlew test

checkstyle:
	./gradlew checkstyleMain checkstyleTest

jacoco:
	./gradlew jacocoTestReport

clean:
	./gradlew clean

run-dist:
	./build/install/app/bin/app