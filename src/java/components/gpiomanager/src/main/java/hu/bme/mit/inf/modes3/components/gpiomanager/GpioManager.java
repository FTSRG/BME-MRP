/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.bme.mit.inf.modes3.components.gpiomanager;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.HashMap;

import com.google.gson.Gson;

/**
 *
 * @author zsoltmazlo
 */
public class GpioManager {
    
    private static final String TAG = "MANAGER";
    
    private static final GpioManager INSTANCE  = new GpioManager();
    
    private HashMap<String, Integer> _gpioMapping = new HashMap<>();
    
    private final HashMap<String, Gpio> _usedGpios = new HashMap<>();
    
    private GpioManager() {
        
    }
    
    private class GpioMarkupObject {
        public String name;
        public int port;
    }
    
    public static void loadGpioMappingFromFile(String resourceName) throws FileNotFoundException {
    	
    	String fileName = GpioManager.class.getClassLoader().getResource(resourceName).getPath();
        Logger.info(TAG, "mapping GPIOs from file: %s", fileName);
        
        // reading from json file
        Gson gson = new Gson();
        GpioMarkupObject[] data = gson.fromJson(new InputStreamReader(GpioManager.class.getClassLoader().getResourceAsStream(resourceName)), GpioMarkupObject[].class);
        
        // adding every pin to the hashmap
        for(GpioMarkupObject gpio: data ) {
            INSTANCE._gpioMapping.put(gpio.name, gpio.port);
            Logger.info(TAG, "GPIO \"%s\" added.", gpio.name);
        }
    }
    
    public static boolean hasGpio(String port) {
    	return INSTANCE._usedGpios.containsKey(port);
    }
    
    public static Gpio setGpio(String port, Gpio.Direction direction) throws Exception {
        
        // first, find gpio pin in hashmap - if there is any, then return with the
        // Gpio instance
        if( INSTANCE._gpioMapping.containsKey(port) ) {
            int pin = INSTANCE._gpioMapping.get(port);
            
            Gpio instance = new Gpio(pin, direction);
            INSTANCE._usedGpios.put(port, instance);
            Logger.info(TAG, "Pin \"%s\" setup succeeded with direction of \"%s\".", port, direction);
            return instance;
        }
        
        Logger.error(TAG, "There is no pin with the given name: %s.", port);
        
        // otherwise throw an Exception that there is no pin with the given name
        throw new GpioNotConfiguratedException("There is no pin with this name!");
    }
    
    public static Gpio getGpio(String port, Gpio.Direction direction) throws Exception {
    	if( hasGpio(port) ) {
    		return INSTANCE._usedGpios.get(port);
    	} else {
    		return setGpio(port, direction);
    	}
    }
    
    public static void cleanup() {
        INSTANCE._usedGpios.values().stream().forEach((g) -> {
            try {
                g.cleanup();
            } catch (Exception ex) {
                // catching gpio exception to not to break for cycle
            }
        });
    }
    

}
