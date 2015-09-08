package com.jpmorgan.task;

import org.springframework.core.task.TaskExecutor;

public class StockMotorExecutor{
	
	public StockMotorExecutor(TaskExecutor taskExecutor, StockMotor motor) {
        taskExecutor.execute(motor);
    }

   
    
}
