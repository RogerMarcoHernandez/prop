javac --release 11 Dominio/*.java 
javac --release 11 ControladoresDominio/*.java 
javac --release 11 ControladoresPersistencia/*.java 
javac --release 11 ControladoresPresentacion/*.java 
javac --release 11 Excepciones/*.java
javac --release 11 Vistas/*.java
javac --release 11 Main/*.java
move Dominio\*.class ..\EXE\CLASS\Dominio
move ControladoresDominio\*.class ..\EXE\CLASS\ControladoresDominio
move ControladoresPersistencia\*.class ..\EXE\CLASS\ControladoresPersistencia
move ControladoresPresentacion\*.class ..\EXE\CLASS\ControladoresPresentacion
move Excepciones\*.class ..\EXE\CLASS\Excepciones
move Vistas\*.class ..\EXE\CLASS\Vistas
move Main\*.class ..\EXE\CLASS\Main