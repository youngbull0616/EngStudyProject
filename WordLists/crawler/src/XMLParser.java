import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;

public class XMLParser {
    final private static String[] dst_paths = {"TOEFL.sql", "CET6.sql", "GRE.sql"};
    final private static String[] src_paths = {"/Users/youngbull/Desktop/BS-Project-master/WordLists/TOEFL.xml", "/Users/youngbull/Desktop/BS-Project-master/WordLists/CET-6.xml", "/Users/youngbull/Desktop/BS-Project-master/WordLists/GRE.xml"};

    private static void toSQL(int index, File outFile) {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(src_paths[index]);
            NodeList wordList = document.getElementsByTagName("wordEntry");
            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(outFile)));
            System.out.println("length: " + wordList.getLength());
            String tableName = (index == 0) ? "TOEFLWordBook" : (index == 1) ? "CET6WordBook" : "GREWordBook";
            out.println("drop table " + tableName + ";\n");
            out.println("create table " + tableName + " (");
            out.println("\tid\tnumeric(4, 0),");
            out.println("\tword\tvarchar(20),");
            out.println("\tphonetic\tvarchar(45),");
            out.println("\tpron\tvarchar(60),");
            out.println("\tposes\tvarchar(1800),");
            out.println("\tsentences\tvarchar(800),");
            out.println("\tprimary key (id)");
            out.println(");\n");
            out.println("delete from " + tableName + ";\n");
            for (int i = 0; i < wordList.getLength(); i++) {
                Node entry = wordList.item(i);
                Node attr = entry.getAttributes().item(0);
                String id = attr.getNodeValue();
                NodeList childNodes = entry.getChildNodes();
                String word = "", phonetic = "", pron = "", poses = "", sentences = "";

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);
                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {
                        String nodeName = childNode.getNodeName();
                        String nodeValue = childNode.getTextContent();
                        switch (nodeName) {
                            case "word":
                                word = nodeValue;
                                break;
                            case "phonetic":
                                phonetic = nodeValue.replaceAll("'", "");
                                break;
                            case "pron":
                                pron = nodeValue;
                                break;
                            case "poses":
                                poses = nodeValue.replaceAll("'", "");
                                break;
                            case "sentences":
                                sentences = nodeValue.replaceAll("'", "@");
                                break;
                            default:
                                // Handle unknown node
                                break;
                        }
                    }
                }

                out.println("insert into " + tableName + " values " + "(\'" + id + "\', \'" + word + "\', \'" +
                        phonetic + "\', \'" + pron + "\', \'" + poses + "\', \'" + sentences + "\');");
            }
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < src_paths.length; i++) {
            File outFile = new File(dst_paths[i]);
            System.out.println("parsing " + src_paths[i] + "...");
            toSQL(i, outFile);
        }
    }
}
