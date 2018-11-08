package com.drkiettran.rabbitmq.rcvr;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

@Service
public class Stix2ProcessorService {
	private static final Logger logger = LoggerFactory.getLogger(Stix2ProcessorService.class);
	private List<Hashtable<String, Object>> parsedDocs = new ArrayList<Hashtable<String, Object>>();

	@Autowired
	private Runner runner;

	/**
	 * Parse the given json doc. then add it to the end of the list. Caller can call
	 * and remove the parsed doc later.
	 * 
	 * This method is called 'asynchronously'.
	 * 
	 * @param stix2Doc
	 */
	@Async
	public void parses(String stix2Doc) {
		logger.info("processing Json doc ...");

		JsonParser parser = new JsonParser();
		JsonObject jsonTree = parser.parse(stix2Doc).getAsJsonObject();
		Hashtable<String, Object> elements = new Hashtable<String, Object>();
		elements.put("original_doc", stix2Doc);

		getElements(jsonTree, elements, "$");

		synchronized (parsedDocs) {
			parsedDocs.add(elements);
		}

		StringBuilder sb = new StringBuilder();

		elements.keySet().stream().sorted().forEach(key -> {
			sb.append(key).append(": ").append(elements.get(key)).append('\n');
		});

		runner.publishes(sb.toString());

		logger.info("processing Json doc ... complete");
	}

	/**
	 * remove the the first parsed doc in the list and return to the caller
	 * 
	 * @return the first parsed doc is removed and return if the list is not empty.
	 *         Else, return null
	 */
	public Hashtable<String, Object> getParsedDoc() {
		Hashtable<String, Object> parsedDoc = null;
		synchronized (parsedDocs) {
			if (!parsedDocs.isEmpty()) {
				parsedDoc = parsedDocs.remove(0);
			}
		}
		return parsedDoc;
	}

	/**
	 * Returns the number of the parsed docs in queue.
	 * 
	 * @return number of parsed docs in queue.
	 */
	public int numberParsedDocs() {
		int numberParsedDocs = 0;
		synchronized (parsedDocs) {
			numberParsedDocs = parsedDocs.size();
		}
		return numberParsedDocs;
	}

	private void getElements(JsonObject jsonTree, Hashtable<String, Object> elements, String elName) {

		jsonTree.entrySet().stream().forEach((entry) -> {
			String curElName;
			curElName = elName + "." + entry.getKey();
			JsonElement value = entry.getValue();

			logger.info(curElName + ":" + value);

			if (value instanceof JsonNull || value instanceof JsonPrimitive) {
				elements.put(curElName, value);
			}

			if (jsonTree.get(entry.getKey()) != null) {
				try {
					if (jsonTree.get(entry.getKey()).getAsJsonArray() instanceof JsonArray) {
						JsonArray array = jsonTree.get(entry.getKey()).getAsJsonArray();
						for (int i = 0; i < array.size(); i++) {
							getElements(array.get(i).getAsJsonObject(), elements, curElName + "[" + i + "]");
						}
					}
				} catch (Exception e) {
				}
				try {
					if (jsonTree.get(entry.getKey()).getAsJsonObject() instanceof JsonObject) {
						getElements(jsonTree.get(entry.getKey()).getAsJsonObject(), elements, curElName);
					}
				} catch (Exception e) {
				}
			}
		});
	}

}
