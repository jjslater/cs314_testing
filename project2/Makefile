JAVAC = javac
JAVA = java
SRC_DIR = src
BIN_DIR = bin
SENDER_CLASS = SenderController
RECEIVER_CLASS = FileServer

JAVA_FX_FLAGS = --module-path $(PATH_TO_FX) --add-modules javafx.controls
JAVA_RUNTIME_FLAGS = $(JAVA_FX_FLAGS) --enable-native-access=javafx.graphics

IP_ADDRESS ?= localhost
PORT ?= 2345
COUNT ?= 100

SOURCES = $(wildcard $(SRC_DIR)/*.java)
#TESTS = $(wildcard $(SRC_DIR)/$(TEST_DIR)/*.java)

all:
	mkdir -p $(BIN_DIR)
	$(JAVAC) $(JAVA_FX_FLAGS) -d $(BIN_DIR) $(SOURCES)

sender: all
	$(JAVA) -cp $(BIN_DIR) $(JAVA_RUNTIME_FLAGS) $(SENDER_CLASS) 

receiver: all
	$(JAVA) -cp $(BIN_DIR) $(JAVA_RUNTIME_FLAGS) $(RECEIVER_CLASS) 

clean:
	rm -rf $(BIN_DIR)
