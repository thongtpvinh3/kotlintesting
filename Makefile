start: 
	./mvnw spring-boot:run

package-run: 
	./mvnw clean package spring-boot:run

start-env:
	docker-compose up  -d 

repackage: 
	./mvnw clean package spring-boot:repackage

serve: 
	java -jar target/testingonline-1.0.war

build:
	docker build -t online-test  . 