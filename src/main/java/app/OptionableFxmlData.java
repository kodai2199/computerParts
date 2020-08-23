package main.java.app;

import java.util.HashMap;
import java.util.Map;

import javafx.stage.Stage;

public class OptionableFxmlData extends FxmlData implements Optionable {
	
	private static Map<String, String> options = new HashMap<>();
	
	public OptionableFxmlData(String resourceName, SceneName sceneName, Stage stage) {
		super(resourceName, sceneName, stage);
	}

	public Map<String, String> getOptions() {
		return options;
	}

	public void setOption(String key, String value) {
		OptionableFxmlData.options.put(key, value);
	}

}
