package net.opentrends.vue.simulator.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import net.opentrends.vue.simulator.dto.ConfigurationTO;

@Component
public class ConfigurationValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
        return ConfigurationTO.class.isAssignableFrom(clazz);
    }
 
	@Override
    public void validate(Object target, Errors errors) {
    	ConfigurationTO conf = (ConfigurationTO) target;
    	
    	if(conf.getTestType().equals("TIMED")) {
    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "processId", "error.processId", "Required.");
    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "previousProcessId", "error.previousProcessId", "Required.");
    	}
    	
    	if(conf.getCassetteTypeId() == 8) { // cPL
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanSingle.lotNumber", "error.lotNumber", "Required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanSingle.scaledResult", "error.scaledResult", "Required.");
		}
    	
    	if(conf.getSameCassette()) {
    		if(conf.getTestType().equals("TIMED")) {
        		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "processId2", "error.processId2", "Required.");
        		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "previousProcessId2", "error.previousProcessId2", "Required.");
        	}
    		if(conf.getCassetteTypeId() == 2) { 
    			// Felv Fiv
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanDouble.controlFelv2", "error.controlFelv2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanDouble.noiseFelv2", "error.noiseFelv2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanDouble.testLineValueFelv2", "error.testLineValueFelv2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanDouble.controlFiv2", "error.controlFiv2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanDouble.noiseFiv2", "error.noiseFiv2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanDouble.testLineValueFiv2", "error.testLineValueFiv2", "Required.");
    		} else if(conf.getCassetteTypeId() == 9) {
    			// Flex4 Lyme
    			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.controlLyme2", "error.controlLyme2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.noiseLyme2", "error.noiseLyme2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.testLineValueLyme2", "error.testLineValueLyme2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.controlLyme2", "error.controlLyme2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.noiseLyme2", "error.noiseLyme2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.testLineValueLyme2", "error.testLineValueLyme2", "Required.");
	    		// Flex4 Anaplasma
    			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.controlAnaplasma2", "error.controlAnaplasma2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.noiseAnaplasma2", "error.noiseAnaplasma2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.testLineValueAnaplasma2", "error.testLineValueAnaplasma2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.controlAnaplasma2", "error.controlAnaplasma2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.noiseAnaplasma2", "error.noiseAnaplasma2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.testLineValueAnaplasma2", "error.testLineValueAnaplasma2", "Required.");
	    		// Flex4 Heartworm
    			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.controlHeartworm2", "error.controlHeartworm2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.noiseHeartworm2", "error.noiseHeartworm2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.testLineValueHeartworm2", "error.testLineValueHeartworm2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.controlHeartworm2", "error.controlHeartworm2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.noiseHeartworm2", "error.noiseHeartworm2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.testLineValueHeartworm2", "error.testLineValueHeartworm2", "Required.");
	    		// Flex4 Ehrlichia
    			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.controlEhrlichia2", "error.controlEhrlichia2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.noiseEhrlichia2", "error.noiseEhrlichia2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.testLineValueEhrlichia2", "error.testLineValueEhrlichia2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.controlEhrlichia2", "error.controlEhrlichia2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.noiseEhrlichia2", "error.noiseEhrlichia2", "Required.");
	    		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanMultiple.testLineValueEhrlichia2", "error.testLineValueEhrlichia2", "Required.");
    		} else {
    			// Scan single
    			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanSingle.control2", "error.control2", "Required.");
    			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanSingle.noise2", "error.noise2", "Required.");
    			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanSingle.testLineValue2", "error.testLineValue2", "Required.");
    			if(conf.getCassetteTypeId() == 8) { // cPL
    				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanSingle.lotNumber2", "error.lotNumber2", "Required.");
    				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "scanSingle.scaledResult2", "error.scaledResult2", "Required.");
    			}
    		}
    	}
        
    }
}
