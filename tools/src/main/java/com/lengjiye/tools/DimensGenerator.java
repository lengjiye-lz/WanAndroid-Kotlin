package com.lengjiye.tools;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 直接运行，用于生成对应比例的dimens文件
 * <p>
 * 用于屏幕适配
 */
public class DimensGenerator {

    private String mainDirectory = "app/src/main/res/";

    private final static String DIMENS_FILE = "values-sw{0}dp/";

    private static final int[] SUPPORT_SW = {600, 960};

    private File fileDir;

    private int sw;

    private DimensGenerator(int sw) {
        this.sw = sw;
        String dir = mainDirectory + DIMENS_FILE.replace("{0}", String.valueOf(sw));
        fileDir = new File(dir);
        if (fileDir.exists()) {
            fileDir.delete();
        }
        fileDir.mkdirs();
        System.out.println(fileDir.getAbsoluteFile());
    }

    public void generate() {
        if (sw == 600) {
            generateXmlFile(1.5f);
        } else if (sw == 960) {
            generateXmlFile(2f);
        }
    }

    private void generateXmlFile(float factor) {

        StringBuffer sb = new StringBuffer();
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
        sb.append("<resources>\n");

        parseXml(sb, factor);

        sb.append("</resources>");

        System.out.println(sb.toString());

        try {
            PrintWriter pw = new PrintWriter(new FileOutputStream(new File(fileDir, "dimens.xml")));
            pw.print(sb.toString());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void parseXml(StringBuffer sb, float factor) {

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder db = dbf.newDocumentBuilder();
            File file = new File(mainDirectory + "values/dimens.xml");
            System.out.println(file.getAbsolutePath());
            Document document = db.parse(file);
            NodeList nodeList = document.getElementsByTagName("dimen");
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node book = nodeList.item(i);
                NamedNodeMap attrs = book.getAttributes();
                Node attr = attrs.item(0);
                StringBuilder elementStrBuilder = new StringBuilder();
                elementStrBuilder.append("<dimen " + attr.getNodeName() + "=\"" + attr.getNodeValue() + "\">");
                NodeList childNodes = book.getChildNodes();
                String nodeValue = childNodes.item(0).getNodeValue();
                if (nodeValue.contains("dp")) {
                    float value = Float.valueOf(nodeValue.replace("dp", ""));
                    elementStrBuilder.append(value * factor + "dp");
                } else if (nodeValue.contains("sp")) {
                    float value = Float.valueOf(nodeValue.replace("sp", ""));
                    elementStrBuilder.append(value * factor + "sp");
                }

                elementStrBuilder.append("</dimen>");
                sb.append(elementStrBuilder.toString() + "\n");
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        for (int i = 0; i < SUPPORT_SW.length; i++) {
            new DimensGenerator(SUPPORT_SW[i]).generate();
        }
    }
}
