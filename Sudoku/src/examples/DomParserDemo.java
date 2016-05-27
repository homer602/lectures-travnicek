/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examples;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;

public class DomParserDemo {
   public static void main(String[] args){

      try {	
         //File inputFile = new File("input.txt");
         DocumentBuilderFactory dbFactory 
            = DocumentBuilderFactory.newInstance();
         DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
         //Document doc = dBuilder.parse("http://www.ceskatelevize.cz/services/programme/xml/schedule.php?user=test&date=21.04.2016&channel=ct24");
         Document doc = dBuilder.parse("http://www.ceskatelevize.cz/services/programme/xml/schedule.php?user=test&date=10.05.2016&channel=ct24");
         doc.getDocumentElement().normalize();
         System.out.println("Root element :" 
            + doc.getDocumentElement().getNodeName());
         NodeList nList = doc.getElementsByTagName("porad");
         System.out.println("----------------------------");
         for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            System.out.println("\nCurrent Element :" 
               + nNode.getNodeName());
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
               Element eElement = (Element) nNode;
              // System.out.println("Student roll no : " 
              //    + eElement.getAttribute("rollno"));
               
               System.out.println("Name : " 
               + eElement
                  .getElementsByTagName("nazvy")
                  .item(0)
                  .getTextContent().trim());
               System.out.println("Cas : " 
               + eElement
                  .getElementsByTagName("cas")
                  .item(0)
                  .getTextContent().trim());
              
              
            }
         }
      } catch (Exception e) {
         e.printStackTrace();
      }
   }
}