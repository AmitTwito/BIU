#208445866
#twitoam 

compile: bin
	find src -name "*.java" > sources.txt 
	javac -cp biuoop-1.4.jar:src -d bin @sources.txt 

run:
	java -cp biuoop-1.4.jar:bin Ass6Game

jar:
	jar cfm ass6game.jar Manifest.mf -C bin .
	
bin:
	mkdir bin
