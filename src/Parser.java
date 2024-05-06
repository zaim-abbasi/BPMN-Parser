import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.w3c.dom.Element;

import java.util.Random;
import java.util.Scanner;

public class Parser {

  public static void main(String[] args) {
    String filename;
    System.out.println("");
    System.out.print("Enter file name: ");
    Scanner sc = new Scanner(System.in);
    filename = sc.nextLine();

    String bpmnFilePath = "resource/" + filename;
    Parser obj = new Parser();

    obj.countBPMNElements(bpmnFilePath);
    sc.close();

    obj.addActivityTime(bpmnFilePath);

    bpmnFilePath = "resource/updated_" + filename;
    double cycleTime = obj.calculateCT(bpmnFilePath);
    System.out.println("Cycle Time: " + cycleTime + " minutes");
  }

  public void countBPMNElements(String bpmnFilePath) {
    File bpmnFile = new File(bpmnFilePath);
    if (!bpmnFile.exists()) {
      System.out.println("File not found: " + bpmnFilePath);
      return;
    }
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      Document doc = dbFactory.newDocumentBuilder().parse(bpmnFile);

      XPathFactory xPathfactory = XPathFactory.newInstance();
      XPath xpath = xPathfactory.newXPath();

      // XPath expressions for counting BPMN elements
      XPathExpression eventExpr = xpath.compile(
          "count(//*[local-name()='startEvent' or local-name()='intermediateCatchEvent' or local-name()='intermediateThrowEvent' or local-name()='endEvent'])");
      XPathExpression startEventExpr = xpath.compile("count(//*[local-name()='startEvent'])");
      XPathExpression intermediateEventExpr = xpath
          .compile("count(//*[local-name()='intermediateCatchEvent' or local-name()='intermediateThrowEvent'])");
      XPathExpression endEventExpr = xpath.compile("count(//*[local-name()='endEvent'])");
      XPathExpression taskExpr = xpath.compile("count(//*[local-name()='task'])");
      XPathExpression userTaskExpr = xpath.compile("count(//*[local-name()='userTask'])");
      XPathExpression serviceTaskExpr = xpath.compile("count(//*[local-name()='serviceTask'])");
      XPathExpression scriptTaskExpr = xpath.compile("count(//*[local-name()='scriptTask'])");
      XPathExpression manualTaskExpr = xpath.compile("count(//*[local-name()='manualTask'])");
      XPathExpression subProcessExpr = xpath.compile("count(//*[local-name()='subProcess'])");
      XPathExpression gatewayExpr = xpath.compile(
          "count(//*[local-name()='exclusiveGateway' or local-name()='parallelGateway' or local-name()='inclusiveGateway'])");
      XPathExpression exclusiveGatewayExpr = xpath.compile("count(//*[local-name()='exclusiveGateway'])");
      XPathExpression parallelGatewayExpr = xpath.compile("count(//*[local-name()='parallelGateway'])");
      XPathExpression inclusiveGatewayExpr = xpath.compile("count(//*[local-name()='inclusiveGateway'])");
      XPathExpression artifactExpr = xpath
          .compile("count(//*[local-name()='dataObject' or local-name()='group' or local-name()='textAnnotation'])");
      XPathExpression dataObjectExpr = xpath.compile("count(//*[local-name()='dataObject'])");
      XPathExpression groupExpr = xpath.compile("count(//*[local-name()='group'])");
      XPathExpression annotationExpr = xpath.compile("count(//*[local-name()='textAnnotation'])");
      XPathExpression connectingObjectsExpr = xpath.compile(
          "count(//*[local-name()='sequenceFlow' or local-name()='messageFlow' or local-name()='association'])");
      XPathExpression sequenceFlowExpr = xpath.compile("count(//*[local-name()='sequenceFlow'])");
      XPathExpression messageFlowExpr = xpath.compile("count(//*[local-name()='messageFlow'])");
      XPathExpression associationExpr = xpath.compile("count(//*[local-name()='association'])");
      XPathExpression swimlaneExpr = xpath.compile("count(//*[local-name()='lane' or local-name()='pool'])");
      XPathExpression poolExpr = xpath.compile("count(//*[local-name()='participant'])");

      XPathExpression laneExpr = xpath.compile("count(//*[local-name()='lane'])");

      // Execute XPath expressions
      double totalEvents = (double) eventExpr.evaluate(doc, XPathConstants.NUMBER);
      double startEvents = (double) startEventExpr.evaluate(doc, XPathConstants.NUMBER);
      double intermediateEvents = (double) intermediateEventExpr.evaluate(doc, XPathConstants.NUMBER);
      double endEvents = (double) endEventExpr.evaluate(doc, XPathConstants.NUMBER);
      double userTasks = (double) userTaskExpr.evaluate(doc, XPathConstants.NUMBER);
      double serviceTasks = (double) serviceTaskExpr.evaluate(doc, XPathConstants.NUMBER);
      double scriptTasks = (double) scriptTaskExpr.evaluate(doc, XPathConstants.NUMBER);
      double manualTasks = (double) manualTaskExpr.evaluate(doc, XPathConstants.NUMBER);
      double tasks = userTasks + serviceTasks + scriptTasks + manualTasks; // Total tasks
      double subProcesses = (double) subProcessExpr.evaluate(doc, XPathConstants.NUMBER);
      double totalActivities = tasks + subProcesses; // Total activities
      double totalGateways = (double) gatewayExpr.evaluate(doc, XPathConstants.NUMBER);
      double exclusiveGateways = (double) exclusiveGatewayExpr.evaluate(doc, XPathConstants.NUMBER);
      double parallelGateways = (double) parallelGatewayExpr.evaluate(doc, XPathConstants.NUMBER);
      double inclusiveGateways = (double) inclusiveGatewayExpr.evaluate(doc, XPathConstants.NUMBER);
      double totalArtifacts = (double) artifactExpr.evaluate(doc, XPathConstants.NUMBER);
      double dataObjects = (double) dataObjectExpr.evaluate(doc, XPathConstants.NUMBER);
      double groups = (double) groupExpr.evaluate(doc, XPathConstants.NUMBER);
      double annotations = (double) annotationExpr.evaluate(doc, XPathConstants.NUMBER);
      double totalConnectingObjects = (double) connectingObjectsExpr.evaluate(doc, XPathConstants.NUMBER);
      double sequenceFlows = (double) sequenceFlowExpr.evaluate(doc, XPathConstants.NUMBER);
      double messageFlows = (double) messageFlowExpr.evaluate(doc, XPathConstants.NUMBER);
      double associations = (double) associationExpr.evaluate(doc, XPathConstants.NUMBER);
      double totalSwimlanes = (double) swimlaneExpr.evaluate(doc, XPathConstants.NUMBER);
      double pools = (double) poolExpr.evaluate(doc, XPathConstants.NUMBER);
      double lanes = (double) laneExpr.evaluate(doc, XPathConstants.NUMBER);

      // display the results
      System.out.println();
      System.out.println("BPMN Model Elements:");
      System.out.println("--------------------------------");
      System.out.println("   Total_Events: " + (int) totalEvents);
      System.out.println("      Start_Events: " + (int) startEvents);
      System.out.println("      Intermediate_Events: " + (int) intermediateEvents);
      System.out.println("      End_Events: " + (int) endEvents);
      System.out.println("--------------------------------");
      System.out.println("   Total_Activities: " + (int) totalActivities);
      System.out.println("      Tasks: " + (int) tasks);
      System.out.println("        User_Tasks: " + (int) userTasks);
      System.out.println("        Service_Tasks: " + (int) serviceTasks);
      System.out.println("        Script_Tasks: " + (int) scriptTasks);
      System.out.println("        Manual_Tasks: " + (int) manualTasks);
      System.out.println("      Sub_Processes: " + (int) subProcesses);
      System.out.println("--------------------------------");
      System.out.println("   Total_Gateways: " + (int) totalGateways);
      System.out.println("      Exclusive_Gateways_XOR: " + (int) exclusiveGateways);
      System.out.println("      Parallel_Gateways_AND: " + (int) parallelGateways);
      System.out.println("      Inclusive_Gateways_OR: " + (int) inclusiveGateways);
      System.out.println("--------------------------------");
      System.out.println("   Total_Artifacts: " + (int) totalArtifacts);
      System.out.println("      Data_Objects: " + (int) dataObjects);
      System.out.println("      Groups: " + (int) groups);
      System.out.println("      Annotations: " + (int) annotations);
      System.out.println("--------------------------------");
      System.out.println("   Total_Connecting_Objects: " + (int) totalConnectingObjects);
      System.out.println("      Sequence_Flows: " + (int) sequenceFlows);
      System.out.println("      Message_Flows: " + (int) messageFlows);
      System.out.println("      Associations: " + (int) associations);
      System.out.println("--------------------------------");
      System.out.println("   Total_Swimlanes: " + (int) totalSwimlanes);
      System.out.println("      Pools: " + (int) pools);
      System.out.println("      Lanes: " + (int) lanes);
      System.out.println("--------------------------------");
      System.out.println();

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void addActivityTime(String bpmnFilePath) {
    try {
      File bpmnFile = new File(bpmnFilePath);
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      dbFactory.setNamespaceAware(true); // Enable namespace awareness
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = dBuilder.parse(bpmnFile);

      // Specify the namespace URI for BPMN elements
      String bpmnNamespaceURI = "http://www.omg.org/spec/BPMN/20100524/MODEL";

      NodeList taskList = doc.getElementsByTagNameNS(bpmnNamespaceURI, "userTask");
      if (taskList.getLength() == 0) {
        taskList = doc.getElementsByTagNameNS(bpmnNamespaceURI, "task");
      }

      Random rand = new Random();

      for (int i = 0; i < taskList.getLength(); i++) {
        Element taskElement = (Element) taskList.item(i);
        int duration = rand.nextInt(11) + 5; // Random duration between 5 to 15 minutes
        taskElement.setAttribute("duration", String.valueOf(duration));
      }

      // Write the updated document to a new file
      String updatedFilePath = "resource/updated_" + bpmnFile.getName();
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new FileWriter(updatedFilePath));
      transformer.transform(source, result);

      System.out.println("Activity times added and file saved: " + updatedFilePath);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }


    public double calculateCT(String bpmnFilePath) {
      File bpmnFile = new File(bpmnFilePath);
      if (!bpmnFile.exists()) {
        System.out.println("File not found: " + bpmnFilePath);
        return 0.0;
      }

      double cycleTime = 0.0;
      try {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        Document doc = dbFactory.newDocumentBuilder().parse(bpmnFile);

        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        // XPath expression to select all tasks and their durations
        XPathExpression taskExpr = xpath.compile("//userTask[@duration]");
        NodeList taskNodes = (NodeList) taskExpr.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < taskNodes.getLength(); i++) {
          Node taskNode = taskNodes.item(i);
          double duration = Double.parseDouble(taskNode.getAttributes().getNamedItem("duration").getNodeValue());
          cycleTime += duration;
        }

        // Consider parallel paths by checking for parallel gateways
        XPathExpression parallelGatewayExpr = xpath.compile("//parallelGateway");
        NodeList parallelGatewayNodes = (NodeList) parallelGatewayExpr.evaluate(doc, XPathConstants.NODESET);

        for (int i = 0; i < parallelGatewayNodes.getLength(); i++) {
          Node parallelGatewayNode = parallelGatewayNodes.item(i);
          NodeList outgoingFlows = parallelGatewayNode.getParentNode().getChildNodes();
          double maxPathTime = 0.0;

          // Find the maximum time among outgoing flows from the parallel gateway
          for (int j = 0; j < outgoingFlows.getLength(); j++) {
            Node flowNode = outgoingFlows.item(j);
            if (flowNode.getNodeType() == Node.ELEMENT_NODE && flowNode.getNodeName().equals("sequenceFlow")) {
              String targetRef = ((Element) flowNode).getAttribute("targetRef");
              double pathTime = calculatePathTime(doc, targetRef);
              maxPathTime = Math.max(maxPathTime, pathTime);
            }
          }

          cycleTime += maxPathTime;
        }

        // Handle other gateway types similarly if needed (exclusive, inclusive)

      } catch (Exception e) {
        e.printStackTrace();
      }

      return cycleTime;
    }

    private double calculatePathTime(Document doc, String targetRef) {
      double pathTime = 0.0;
      try {
        XPathFactory xPathfactory = XPathFactory.newInstance();
        XPath xpath = xPathfactory.newXPath();

        // XPath expression to select tasks in the path
        XPathExpression pathExpr = xpath.compile("//userTask[@id='" + targetRef + "']");
        NodeList pathNodes = (NodeList) pathExpr.evaluate(doc, XPathConstants.NODESET);

        for (int k = 0; k < pathNodes.getLength(); k++) {
          Node pathNode = pathNodes.item(k);
          double duration = Double.parseDouble(pathNode.getAttributes().getNamedItem("duration").getNodeValue());
          pathTime += duration;
        }
      } catch (Exception e) {
        e.printStackTrace();
      }
      return pathTime;
    }
}