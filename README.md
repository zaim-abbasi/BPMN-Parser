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
Enter file name: file.bpmn

BPMN Model Elements:
--------------------------------
   Total_Events: 4
      Start_Events: 2
      Intermediate_Events: 0    
      End_Events: 2
--------------------------------
   Total_Activities: 0
      Tasks: 0
        User_Tasks: 0
        Service_Tasks: 0        
        Script_Tasks: 0
        Manual_Tasks: 0
      Sub_Processes: 0
--------------------------------
   Total_Gateways: 4
      Exclusive_Gateways_XOR: 0 
      Parallel_Gateways_AND: 4
      Inclusive_Gateways_OR: 0
--------------------------------
   Total_Artifacts: 0
      Data_Objects: 0
      Groups: 0
      Annotations: 0
--------------------------------
   Total_Connecting_Objects: 23
      Sequence_Flows: 23
      Message_Flows: 0
      Associations: 0
--------------------------------
   Total_Swimlanes: 0
      Pools: 0
      Lanes: 0
--------------------------------

Activity times added and file saved: resource/updated_file.bpmn
Cycle Time: 142.0 minute
## Contributing
Contributions to the BPMN Parser are welcome. Please follow the contribution guidelines and open a pull request for any proposed changes.

## License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.
