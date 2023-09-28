cd ..
cd ..
cd FONTS  
javac --release 11 Dominio/*.java 
javac --release 11 Excepciones/*.java
javac --release 11 -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar JUnit/ConfiguracionPerfilTest.java
java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore JUnit.ConfiguracionPerfilTest