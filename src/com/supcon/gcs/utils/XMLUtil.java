/**
 * Copyright (C) 2011 ZHEJIANG SUPCON TECHNOLOGY CO.,LTD. 
 * All rights reserved.
 */
package com.supcon.gcs.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import oracle.net.aso.e;

import org.apache.derby.impl.sql.compile.GetCurrentConnectionNode;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.org.apache.bcel.internal.generic.DCONST;
import com.sun.org.apache.xml.internal.security.utils.XMLUtils;
import com.supcon.gcs.entities.Device;
import com.supcon.gcs.servlet.CheckServlet;

/**
 * XML工具包
 * 
 * @author Administrator
 * @version 1.0
 */
public class XMLUtil {
	public static Logger logger = LoggerFactory.getLogger(XMLUtil.class);
	/**
	 * 将xml文件转换为相应的java对象
	 * 
	 * @param file
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static List<Object> parse(File file, Class clazz) throws Exception {
		if (null != file && file.exists()) {
			SAXReader reader = new SAXReader();
			Document document = reader.read(file);
			return parseObjectList(document, clazz);
		}
		return null;
	}

	/**
	 * 将xml字符串转换为对象
	 * 
	 * @param xml
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static List<Object> parse(String xml, Class clazz) throws Exception {
		Document document = DocumentHelper.parseText(xml);
		return parseObjectList(document, clazz);
	}

	/**
	 * 将xml文件转换为xml字符串
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public static String format(String filePath) throws Exception {
		if (null != filePath && filePath.length() > 0) {
			return null;
		}
		File file = new File(filePath);
		if (!file.exists()) {
			return null;
		}
		SAXReader reader = new SAXReader();
		Document document = reader.read(file);
		return document.asXML();
	}

	/**
	 * 更新xml文件 如xml文件不存在 则创建
	 * 
	 * @param objs
	 * @param filePath
	 * @param clazz
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void write(List<Object> objs, String filePath, Class clazz) throws Exception {
		if (null != objs && !objs.isEmpty() && null != filePath && filePath.trim().length() > 0) {
			SAXReader reader = new SAXReader();
			File file = new File(filePath);
			if (!file.exists()) {
				if (!file.getParentFile().exists()) {
					file.getParentFile().mkdirs();
				}
				createXML(objs, filePath, clazz);
			} else {
				Document doc = reader.read(file);
				Element root = doc.getRootElement();
				Iterator iterator = root.elementIterator(clazz.getSimpleName());
				Field[] fields = clazz.getDeclaredFields();
				for (Object obj : objs) {
					while (iterator.hasNext()) {
						Element element = (Element) iterator.next();
						String name = element.elementText("name");
						Method method = clazz.getDeclaredMethod("getName");
						Object name1 = method.invoke(obj);
						if (null != name1 && name1.toString().equals(name)) {
							for (Field field : fields) {
								if (field.getName().equals("name")) {
									continue;
								}
								String methodName = "get"
										+ field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase());
								Method method1 = clazz.getDeclaredMethod(methodName);
								Object value = method1.invoke(obj);
								if (null == value) {
									continue;
								}
								if (null == element.element(field.getName())) {
									element.addElement(field.getName()).setText(value.toString());
								} else {
									element.element(field.getName()).setText(value.toString());
								}
							}
						}
					}
				}
				// 输出全部原始数据，并用它生成新的我们需要的XML文件
				XMLWriter writer = new XMLWriter(new FileWriter(new File(filePath)));
				writer.write(doc); // 输出到文件
				writer.close();
			}

		}
	}

	/**
	 * 创建新的XML文件
	 * 
	 * @param objs
	 * @param filePath
	 * @param clazz
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static void createXML(List<Object> objs, String filePath, Class clazz) throws Exception {
		if (null != objs && !objs.isEmpty() && null != filePath && filePath.trim().length() > 0) {
			Document doc = DocumentHelper.createDocument();
			Element root = doc.addElement("Data");
			Field[] fields = clazz.getDeclaredFields();
			for (Object obj : objs) {
				Element element = root.addElement(clazz.getSimpleName());
				for (Field field : fields) {
					String methodName = "get" + field.getName().replaceFirst(field.getName().substring(0, 1), field.getName().substring(0, 1).toUpperCase());
					Method method = clazz.getDeclaredMethod(methodName);
					Object value = method.invoke(obj);
					if (null != value && value.toString().length() > 0) {
						Element fieldElement = element.addElement(field.getName());
						fieldElement.setText(value.toString());
					}
				}
			}
			// 输出全部原始数据，并用它生成新的我们需要的XML文件
			XMLWriter writer = new XMLWriter(new FileWriter(new File(filePath)));
			writer.write(doc); // 输出到文件
			writer.close();
		} else {
			throw new NullPointerException("文件路径为空或对象为空");
		}
	}

	/**
	 * 将xml的Document转换为指定对象的List
	 * 
	 * @param document
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Object> parseObjectList(Document document, Class clazz) throws Exception {
		List<Object> list = new ArrayList<Object>();
		Element root = document.getRootElement();// 获取根节点
		Iterator iterator = root.elementIterator(clazz.getSimpleName());// 找到指定类名的节点 进行迭代
		while (iterator.hasNext()) {
			Element element = (Element) iterator.next();// 当前节点 二级节点
			Object object = clazz.newInstance();
			Iterator<Element> fieldIterator = element.elementIterator();// 当前节点的下级节点
			while (fieldIterator.hasNext()) {// 开始迭代
				Element fieldElement = fieldIterator.next();
				String ename = fieldElement.getName();// 三级节点名称 ---- 指定类的属性
				String evalue = fieldElement.getText();// 属性值
				String methodName = "set" + ename.replaceFirst(ename.substring(0, 1), ename.substring(0, 1).toUpperCase());
				Field field = clazz.getDeclaredField(ename);
				Class fieldType = field.getType();
				Method method = clazz.getMethod(methodName, fieldType);
				if (null != method) {
					if (fieldType.getSimpleName().equals(Integer.class.getSimpleName())) {
						method.invoke(object, Integer.parseInt(evalue));
					} else if (fieldType.getSimpleName().equals(Boolean.class.getSimpleName())) {
						method.invoke(object, Boolean.parseBoolean(evalue));
					} else {
						method.invoke(object, evalue);
					}
				}
			}
			list.add(object);
		}
		return list;
	}

	/**
	 * 将指定对象转为xml字符串 发送到微信
	 * 
	 * @param object
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String format(Object object) throws Exception {
		Class clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		Document document = DocumentHelper.createDocument();
		Element root = document.addElement("xml");
		for (Field field : fields) {
			String fieldName = field.getName();
			String methodName = "get" + fieldName.replaceFirst(fieldName.substring(0, 1), fieldName.substring(0, 1).toUpperCase());
			Method method = clazz.getDeclaredMethod(methodName);
			if (null != method) {
				Object value = method.invoke(object);
				if (value != null) {
					Element element = root.addElement(fieldName);
					element.setText(null == value ? "" : value.toString());
				}
			}
		}
		logger.info("发送消息到微信xml:" + document.asXML());
		return document.asXML();
	}

	/**
	 * 将微信接收的消息转换为指定对象 一般为<code>AcceptMessage</code>
	 * 
	 * @param xml
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings({ "rawtypes" })
	public static Object parseObject(String xml, Class clazz) throws Exception {
		if (null != xml && xml.length() > 0) {
			Document document = DocumentHelper.parseText(xml);
			return parseObject(document, clazz);
		}
		return null;
	}

	/**
	 * 将微信接收的消息流转换为指定对象 一般为<code>AcceptMessage</code>
	 * @param stream
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static Object parseObject(InputStream stream,Class clazz) throws Exception{
		SAXReader reader = new SAXReader();
		Document document = reader.read(stream);
		if(null != document){
			return parseObject(document, clazz);
		}
		return null;
	}
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object parseObject(Document document, Class clazz) throws Exception {
		Object object = clazz.newInstance();
		Element root = document.getRootElement();
		logger.info("接收到的消息xml:" + document.asXML());
		if ("xml".equals(root.getName())) {// 判断是否为微信发送的消息
			Iterator<Element> iterator = root.elementIterator();
			while (iterator.hasNext()) {
				Element element = iterator.next();
				String ename = element.getName();
				String evalue = element.getText();
				String methodName = "set" + ename.replaceFirst(ename.substring(0, 1), ename.substring(0, 1).toUpperCase());
				Field field = clazz.getDeclaredField(ename);
				Class fieldType = field.getType();
				Method method = clazz.getMethod(methodName, fieldType);
				if (null != method) {
					if (fieldType.getSimpleName().equals(Integer.class.getSimpleName())) {
						method.invoke(object, Integer.parseInt(evalue));
					} else if (fieldType.getSimpleName().equals(Boolean.class.getSimpleName())) {
						method.invoke(object, Boolean.parseBoolean(evalue));
					} else {
						method.invoke(object, evalue);
					}
				}
			}
		}
		return object;
	}
}
