/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bangla.dictionary;

import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Asus
 */
public class Eng {
    
    private final SimpleStringProperty serial;
    private final SimpleStringProperty word;

    public Eng(String serial,String word) {
        this.serial = new SimpleStringProperty(serial);
        this.word =new SimpleStringProperty(word);
    }
    Eng() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String getWord() {
        return word.get();
    }
    
    public void setWord(String v)
    {
        word.set(v);
    }

    public String getSerial() {
        return serial.get();
    }
    public void setSerial(String v)
    {
        serial.set(v);
    }
    
    
    
    
}
