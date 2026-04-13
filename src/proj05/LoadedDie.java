/********************************************************************
* Name: Trevon Collins
* Course: CSC 123
* Lab: proj05
* Date: 04/13/2026
* File: LoadedDie.java
*
* Description: A loaded die that extends Die and can weight a side
* based on probability.
********************************************************************/

package proj05;

import java.util.Random;

public class LoadedDie extends Die{
    protected int loadedSide;
    protected double bias;

    public LoadedDie() {
        super();
        setLoadedSide(0);
        setBias(0.0);
    }

    public LoadedDie(int _numSides) {
        super(_numSides);
        setBias(0.0);
        setLoadedSide(0);
    }

    public LoadedDie(int _numSides, int _loadedSide, double _bias) {
        super(_numSides);
        setBias(_bias);
        setLoadedSide(_loadedSide);
    }
    
    public LoadedDie(int _numSides, Random _random, int _loadedSide, double _bias) {
        super(_numSides, _random);
        setBias(_bias);
        setLoadedSide(_loadedSide);
    }

    public void setBias(double _bias) {
        bias = biasValidator(_bias);
    }
    
    public double getBias() {
        return bias;
    }
    
    public void setLoadedSide(int _loadedSide) {
        loadedSide = loadedSideValidator(_loadedSide);
    }
    
    public int getLoadedSide() {
        return loadedSide;
    }

    @Override
    public int roll() {
        if (loadedSide == 0 || bias == 0.0) {
            return super.roll();
        }
        
        if (new Random().nextDouble() < bias) {
            return loadedSide;
        }

        return super.roll();
    }

    @Override
    public String toString() {
        return String.format("LoadedDie[sides=%d, loadedSide=%d, bias=%.1f]"
                                , getNumSides(), loadedSide, bias);
    }

    private int loadedSideValidator(int _loadedSide) {
        int validLoadedSide;
        if (_loadedSide < 0 || _loadedSide > getNumSides()) {
            validLoadedSide = 0;
        } else {
            validLoadedSide = _loadedSide;
        }

        return validLoadedSide;
    }

    private double biasValidator(double _bias) {
        double validBias;
        if (_bias < 0.0 || _bias > 1.0) {
            validBias = 0.0;
        } else {
            validBias = _bias;
        }

        return validBias;
    }

    

    
}
