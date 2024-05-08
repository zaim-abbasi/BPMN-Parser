# BPMN Parser

## Overview
The BPMN Parser Project is a Java-based tool designed to parse BPMN (Business Process Model and Notation) files, perform various operations such as counting BPMN elements, adding random activity times to tasks, and calculating the cycle time of a process.

## Features
1. **Count BPMN Elements:** The project provides functionality to count different BPMN elements within a BPMN file, including start events, end events, tasks, and gateways.
2. **Add Activity Time:** It allows users to add random activity times to user tasks or tasks in a BPMN file, enhancing the realism of process simulations.
3. **Calculate Cycle Time:** The project can calculate the cycle time of a process based on the durations of tasks and the maximum time among outgoing flows from parallel gateways.

## Getting Started and Usage

### Getting Started
To use the BPMN Parser, follow these steps:
1. Clone the repository to your local machine.
2. Navigate to the `src` directory.
3. Compile and run the `Parser.java` file.
4. Run the program using `java Parser`.
5. When prompted, enter the name of the BPMN file located in the `resource` folder.

### Usage
1. **Import BPMN Files:** Download your BPMN files (created in [BPMN.io](https://bpmn.io/)) and place them in the `resource` folder.
2. **Run the Program:** Execute `Parser.java` and enter the filename (with extension) when prompted.
3. **View Results:** The program will create a new file with the prefix "updated_" in the `resource` folder, containing the BPMN file with added activity times.
4. **Analyzing Results:** Check the terminal output for the count of BPMN elements and the calculated cycle time.

## Sample Output
![parser-output](https://github.com/zaim-abbasi/BPMN-Parser/assets/125147306/4ac27928-4840-4531-ae7f-f3e443cdb3dd)

## Contributing
Contributions to the BPMN Parser are welcome. Please follow the contribution guidelines and open a pull request for any proposed changes.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
