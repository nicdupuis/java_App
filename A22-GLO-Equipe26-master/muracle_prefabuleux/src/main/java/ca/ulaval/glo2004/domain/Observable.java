/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ca.ulaval.glo2004.domain;

/**
 *
 * @author equipe26
 */
public interface Observable {
    
    public void registerObserver(ControllerObserver newListener);
    
    public void unregisterObserver(ControllerObserver listener);
}
