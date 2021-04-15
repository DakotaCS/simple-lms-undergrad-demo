README

This file contains 3 sections: Running the Program, Usernames/passwords, and Test Files

Dakota Soares, COMP482, A3, Athabasca University

Running the Program: --------------------------------------------------

1st Option: 
The program can be run by double-clicking the included *.jar file. This has been pre-built due 
to the complexity of the project (and higher potential of error manually compiling code). 

2nd Option: 
If the JAR does not function correctly, simply opening up the Eclipse IDE, or NetBeans, making a new project
entitled libra_lms_main, and dragging the files in this order will allow the program to run: 
Under "/src", put libra_lms_main.java, mItem.java, pItem.java, and printDialog.java
Make a folder called "/lib" and put the pdfbox-app2.0.22.jar into it. 

3rd Option: (not recommended)
Run from the CMD. This is not recommended however due to the number of compiles and referencing needed. 
First, compile each *.java file into a *.jar (excluding the pdfbox JAR). Then, you can run: 
(replacing a, b, c, and myProgram with the respective *.java files from the project) 
java -cp .;a.jar;b.jar;c.jar myProgram.java
	java -cp .;a.jar;b.jar;c.jar myProgram
More info on loading programs with external Jars can be found here: 
https://deeplearning.lipingyang.org/2017/05/24/run-java-program-on-terminal-with-external-library-jar/

Authorization Dialog passwords: ---------------------------------------

Username: admin
Password: 1234

Test Files: -----------------------------------------------------------

Two test files are included. They are: 
patron_data.txt
materials_data.txt

They can be loaded into the program under File -> Open Dialogs. 

