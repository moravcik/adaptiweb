package com.adaptiweb.gwt.preload;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.ServletContext;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.io.filefilter.AbstractFileFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.adaptiweb.gwt.preload.SerializationPolicyProvider;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.SerializationPolicyLoader;


/**
 * This implementation provides {@link SerializationPolicy} for services from multiple GWT modules.
 * <p>
 * <b>Requirements:</b>
 * <li>All modules must be placed in own folder with same name as module has.</li>
 * <li>These folders must be located directly in <b>docBase</b> (root of WAR file).</li>
 * <li>Each module must contains {@code rpcPolicyManifest/manifest.txt} which was generated in {@code gwt-gen} folder during GWT compilation.</li>
 * </p>
 * <p><b>Caution:</b><br>
 * This implementation doesn't work if war file isn't unpacked in servlet container.
 * <i>(Element {@code <Context>} in context.xml or server.xml can't have attribute {@code unpackWARs="false"})</i>
 * </p>
 */
@Service
public class MultiModuleSerializationPolicyProvider implements SerializationPolicyProvider {

	public static final Log log = LogFactory.getLog(MultiModuleSerializationPolicyProvider.class);

	private final Map<String, File> serializationPolicyFiles = new HashMap<String, File>();

	private final ConcurrentMap<String, SerializationPolicy> serializationPolicyCache = new ConcurrentHashMap<String, SerializationPolicy>();

	public MultiModuleSerializationPolicyProvider() {}
	
	/**
	 * All files containing serialization policy are located during construction of this object.
	 * Serialization policies are loaded from them (and cached) as needed.
	 * 
	 * @param servletContext
	 * @throws IOException
	 */
	@Autowired(required=false)
	public MultiModuleSerializationPolicyProvider(ServletContext servletContext) throws IOException {
		for (File rpcPolicyManifest : listRpcPolicyManifestFiles(servletContext.getRealPath("/"))) {
			File moduleDir = rpcPolicyManifest.getParentFile().getParentFile();
			LineIterator entries = FileUtils.lineIterator(rpcPolicyManifest);

			while (entries.hasNext()) {
				String line = entries.nextLine();
				if (line.startsWith("#") || line.trim().length() == 0)
					continue;

				String[] entry = line.split(",");
				assert entry.length == 2 : "Invalid format of file: " + rpcPolicyManifest.getAbsolutePath();
				String rpcServiceInterfaceName = entry[0].trim();
				String rpcPolicyStrongFileName = entry[1].trim();

				if (serializationPolicyFiles.containsKey(rpcServiceInterfaceName)) {
					assert serializationPolicyFiles.get(rpcServiceInterfaceName).getName().equals(rpcPolicyStrongFileName);
				} else {
					File serializationPolicyFile = new File(moduleDir, rpcPolicyStrongFileName);
					assert serializationPolicyFile.exists();
					serializationPolicyFiles.put(rpcServiceInterfaceName, serializationPolicyFile);
				}
			}
			
			LineIterator.closeQuietly(entries);
		}
	}

	@SuppressWarnings("unchecked")
	private static Iterable<File> listRpcPolicyManifestFiles(String scanDirName) {
		return FileUtils.listFiles(new File(scanDirName), new AbstractFileFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return "rpcPolicyManifest".equals(dir.getName()) && "manifest.txt".equals(name);
			}

		}, new AbstractFileFilter() {
			@Override
			public boolean accept(File file) {
				return "rpcPolicyManifest".equals(file.getName()) || new File(file, "rpcPolicyManifest").exists();
			}
		});
	}

	public SerializationPolicy getPolicyFor(Class<?> serviceInterfaceClass) {
		String className = serviceInterfaceClass.getName();
		
		SerializationPolicy policy = serializationPolicyCache.get(className);
		if (policy == null) {
			policy = loadPolicy(className);
			SerializationPolicy existingPolicy = serializationPolicyCache.putIfAbsent(className, policy);
			if (existingPolicy != null) policy = existingPolicy;
		}
		return policy;
	}

	private SerializationPolicy loadPolicy(String className) {
		File policyFile = serializationPolicyFiles.get(className);
		if (policyFile == null) {
			log.warn("No RPC serialization policy for service " + className);
			return RPC.getDefaultSerializationPolicy();
		}
		
		InputStream in = null;
		try {
			in = FileUtils.openInputStream(policyFile);
			SerializationPolicy result = SerializationPolicyLoader.loadFromStream(in, null);
			log.info("Loaded serrialization policy for " + className + " from file " + policyFile.getAbsolutePath());
			return result;
		} catch (IOException e) {
			log.error("IOException: Unable to load serialization policy for " + className + " from file " + policyFile.getAbsolutePath(), e);
			return RPC.getDefaultSerializationPolicy();
		} catch (ParseException e) {
			log.error("ParseException: Unable to load serialization policy for " + className + " from file " + policyFile.getAbsolutePath(), e);
			return RPC.getDefaultSerializationPolicy();
		} finally {
			IOUtils.closeQuietly(in);
		}
	}
}
