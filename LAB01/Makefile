# Directories
SRC_DIR = Code
BIN_DIR = Classes

# Source files
SRC_FILES = $(SRC_DIR)/*.java

# Class files
CLASS_FILES = $(BIN_DIR)/*.class

# Main target
Main: $(CLASS_FILES)
	@echo "compilation complete run \"java -cp Classes LAB01.$(SRC_DIR).Main\""

$(CLASS_FILES): $(SRC_FILES)
	javac -d $(BIN_DIR) $(SRC_FILES)

# Clean target
clean:
	rm -f $(CLASS_FILES)