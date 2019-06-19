/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yeeter.bean;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author alec
 */
@Named(value = "languageBean")
@SessionScoped
public class YeeterLanguageBean implements Serializable {
    
    private String localeCode;

    /**
     * Creates a new instance of YeeterLanguageBean
     */
    public YeeterLanguageBean() {
    }
    
    private static final Map<String, Object> COUNTRIES;
    static{
        COUNTRIES = new LinkedHashMap<>();
        COUNTRIES.put("Spanish", new Locale("es", "ES"));
        COUNTRIES.put("English", Locale.ENGLISH);
        COUNTRIES.put("Catalan", new Locale("ca", "ES"));
    }
    
    
    public Map<String, Object> getCountriesInMap() {
        return COUNTRIES;
    }


    public String getLocaleCode() {
        return localeCode;
    }


    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public void countryLocaleCodeChanged(ValueChangeEvent e){
		
        String newLocaleValue = e.getNewValue().toString();
		
        //loop country map to compare the locale code
        COUNTRIES.entrySet().stream().filter((entry) ->
                (entry.getValue().toString().equals(newLocaleValue))).forEachOrdered((entry) -> {
            FacesContext.getCurrentInstance()
                    .getViewRoot().setLocale((Locale)entry.getValue());
        });
    }
    
}
