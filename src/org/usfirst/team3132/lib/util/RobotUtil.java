package org.usfirst.team3132.lib.util;

import org.usfirst.team3132.lib.LogitechGamepadF310;

/**
 * Created by sailo on 5/9/2016.
 */
public class RobotUtil {

    /**
     *
     * @param  joyValue: the value of the joystick to scale
     * @return a squared joystick value with a deadzone of +- 0.1
     *
     * @apiNote
     * This method is designed to take a joystick value (-1 thru +1) and
     * create a scaled value with a deadzone of +-0.1 around 0
     */
    public double calcThrottleParabola(double joyValue){
        double adjustedValue;
        adjustedValue = (joyValue - 0.1f) * (joyValue - 0.1f) * 1.23456f;
        if(adjustedValue > 0)
            adjustedValue = adjustedValue * Math.signum(joyValue);
        else
            return 0;

        return MathUtil.limitValue(adjustedValue,1);
    }

    
    public double[] driveTankStandard(LogitechGamepadF310 gamepad){
    	double left,right;
    	switch(gamepad.getPOVVal()){
	    	case 0:
	    		left = 0.1;
	    		right = 0.1;
	    		break;
	    	case 2:
	    		left = 0.1;
	    		right = -0.1;
	    		break;
	    	case 4:
	    		left = -0.1;
	    		right = -0.1;
	    		break;
	    	case 6:
	    		left = -0.1;
	    		right = 0.1;
	    		break;
	    	default:
	    		left = gamepad.getLeftYScaled();
	    		right = gamepad.getRightYScaled();
	    		break;
    	}
    	
    	return new double[] {left,right};
    }
    
    public double[] driveWheel(double move, double turn,boolean quickTurn){
    	double left = 0;
        double right = 0;
        double leftOverpower = 0;
        double rightOverpower = 0;
        double overPowerGain = 0.1;
        boolean useOverpower = false;

        if(quickTurn){
            left = move + turn;
            right = move - turn;
        } else {
            left = move + Math.abs(move)*turn;
            right = move - Math.abs(move)*turn;
        }

        if(!useOverpower){
            return new double[] {left,right};
        }

        if(left > 1){
            leftOverpower = left - 1;
            left = 1;
        }else if(left < -1){
            leftOverpower = left + 1;
            left = -1;
        }

        if(right > 1){
            rightOverpower = right - 1;
            right = 1;
        } else if(right < -1){
            rightOverpower = right + 1;
            right = -1;
        }

        left = left - rightOverpower*overPowerGain;
        right = right - leftOverpower*overPowerGain;

        return new double[] {left,right};
    }

}
