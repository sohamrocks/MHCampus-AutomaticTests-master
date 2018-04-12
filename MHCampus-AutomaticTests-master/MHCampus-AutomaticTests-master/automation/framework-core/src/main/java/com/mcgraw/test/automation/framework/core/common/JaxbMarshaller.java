package com.mcgraw.test.automation.framework.core.common;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.ObjectPool;
import org.apache.commons.pool.impl.StackObjectPool;

import com.mcgraw.test.automation.framework.core.exception.MarshallingException;
import com.mcgraw.test.automation.framework.core.results.logger.Logger;

/**
 * Marshaller/Unmarshaller<br>
 * One JaxbMarshaller instance exists for one JAXBContext<br>
 * Uses pooling for Marshaller/Unmarshaller instances
 * 
 * 
 * @author Andrei Varabyeu
 * 
 */
public final class JaxbMarshaller {
	/**
	 * Cache for JAXBContext instances
	 */
	private static Map<String, JaxbMarshaller> instances = Collections
			.synchronizedMap(new HashMap<String, JaxbMarshaller>());

	/**
	 * Marshaller's pool
	 */
	private ObjectPool<Marshaller> marshallerPool;

	/**
	 * Unmarshaller's pool
	 */
	private ObjectPool<Unmarshaller> unmarshallerPool;

	/**
	 * JAXBContext for
	 */
	private JAXBContext jaxbContext;

	private JaxbMarshaller(String contextPath) {
		this(initContext(contextPath));
	}

	private JaxbMarshaller(Class<?> clazz) {
		this(initContext(clazz));
	}

	private static JAXBContext initContext(String contextPath) {
		try {
			return JAXBContext.newInstance(contextPath);
		} catch (JAXBException e) {
			String message = "Error on creating JAXB Context";
			Logger.error(message + e.getLocalizedMessage());
			throw new MarshallingException(message, e);
		}
	}

	private static JAXBContext initContext(Class<?> clazz) {
		try {
			return JAXBContext.newInstance(clazz);
		} catch (JAXBException e) {
			String message = "Error on creating JAXB Context";
			Logger.error(message + e.getLocalizedMessage());
			throw new MarshallingException(message, e);
		}
	}

	private JaxbMarshaller(JAXBContext context) {
		jaxbContext = context;
		marshallerPool = new StackObjectPool<Marshaller>(
				new BasePoolableObjectFactory<Marshaller>() {
					@Override
					public Marshaller makeObject() throws Exception {
						return jaxbContext.createMarshaller();
					}

				});

		unmarshallerPool = new StackObjectPool<Unmarshaller>(
				new BasePoolableObjectFactory<Unmarshaller>() {
					@Override
					public Unmarshaller makeObject() throws Exception {
						return jaxbContext.createUnmarshaller();
					}
				});

	}

	public static JaxbMarshaller getInstanceForContext(String packageName) {
		if (instances.containsKey(packageName)) {
			return instances.get(packageName);
		} else {
			JaxbMarshaller jaxbMarshaller = new JaxbMarshaller(packageName);
			instances.put(packageName, jaxbMarshaller);
			return jaxbMarshaller;
		}
	}

	public static JaxbMarshaller getInstanceForContext(Class<?> clazz) {
		if (instances.containsKey(clazz.getCanonicalName())) {
			return instances.get(clazz.getCanonicalName());
		} else {
			JaxbMarshaller jaxbMarshaller = new JaxbMarshaller(clazz);
			instances.put(clazz.getCanonicalName(), jaxbMarshaller);
			return jaxbMarshaller;
		}
	}

	public void marshall(Object jaxbElement, Result result) {
		Marshaller marshaller = null;
		try {
			marshaller = borrowObject(marshallerPool);
			marshaller.marshal(jaxbElement, result);
		} catch (JAXBException e) {
			String message = "Error on marshalling JAXB Element "
					+ jaxbElement.getClass() + " to " + result.getClass();
			Logger.error(message + e.getLocalizedMessage());
			throw new MarshallingException(message, e);
		} finally {
			if (null != marshaller) {
				returnObject(marshallerPool, marshaller);
			}
		}
	}

	public void marshall(Object jaxbElement, File file) {
		marshall(jaxbElement, new StreamResult(file));
	}

	public <T> T unmarshall(Source source, Class<T> clazz) {
		Unmarshaller unmarshaller = borrowObject(unmarshallerPool);
		try {
			JAXBElement<T> jaxbElement = unmarshaller.unmarshal(source, clazz);
			return jaxbElement.getValue();
		} catch (JAXBException e) {
			String message = "Error on unmarshalling source " + source + " to "
					+ clazz;
			Logger.error(message + e.getLocalizedMessage());
			throw new MarshallingException(message, e);
		} finally {
			returnObject(unmarshallerPool, unmarshaller);
		}
	}

	public <T> T unmarshall(File file, Class<T> clazz) {
		return unmarshall(new StreamSource(file), clazz);
	}

	private <T> T borrowObject(ObjectPool<T> pool) {
		try {
			return pool.borrowObject();
		} catch (Exception e) {
			String message = "Cannot create or obtain from pool object with type '"
					+ pool + "'";
			Logger.error(message + e.getLocalizedMessage());
			throw new MarshallingException(message, e);
		}
	}

	private <T> void returnObject(ObjectPool<T> pool, T object) {
		try {
			pool.returnObject(object);
		} catch (Exception e) {
			String message = "Cannot return object with type '" + pool
					+ "'to pool";
			Logger.error(message + e.getLocalizedMessage());
			throw new MarshallingException(message, e);
		}
	}

}
