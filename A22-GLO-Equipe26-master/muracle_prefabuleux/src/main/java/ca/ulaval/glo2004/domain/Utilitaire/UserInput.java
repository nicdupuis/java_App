/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ca.ulaval.glo2004.domain.Utilitaire;


import java.awt.event.KeyEvent;
import ca.ulaval.glo2004.domain.MesureImperial;
public class UserInput {
    
    public UserInput()
    { 
    }
    
    public boolean verifyUserInputKeyPressedNumbers (java.awt.event.KeyEvent keyEvent)
    {
        boolean isOkay = false;
        if ((keyEvent.getKeyChar() >= '0' && keyEvent.getKeyChar() <= '9') || keyEvent.getKeyCode() == KeyEvent.VK_ENTER || keyEvent.getKeyCode() == KeyEvent.VK_BACK_SPACE || keyEvent.getKeyCode() == KeyEvent.VK_SPACE || keyEvent.getKeyChar() == '/')
        {
            isOkay = true;
        }
        else
        {
            isOkay = false;
        }
        return isOkay;
    }
    
    public static  MesureImperial StringtoMesureImperial(String toConvert){
        String pattern = "^\\d+\\s\\d+[/]\\d+|^\\d+";
        int partieEntier=0;
        int numerateur =0;
        int denominateur=1;
        if(toConvert.matches(pattern)){
            String[] splitString = toConvert.split("\\s+|/");
            partieEntier = Integer.parseInt(splitString[0]);
            if (splitString.length == 3) {
                numerateur = Integer.parseInt(splitString[1]);
                denominateur = Integer.parseInt(splitString[2]);
            }
        }
        return new MesureImperial(partieEntier,numerateur,denominateur); 
    } 
}
