cd ..
cd ..
cd FONTS  
javac --release 11 Vistas/*.java
javac --release 11 -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar JUnit/ManualTest.java
java -cp .;lib/junit-4.13.2.jar;lib/hamcrest-core-1.3.jar org.junit.runner.JUnitCore JUnit.ManualTest