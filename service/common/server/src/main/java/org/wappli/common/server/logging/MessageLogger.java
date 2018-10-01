package org.wappli.common.server.logging;

import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
public class MessageLogger {
    public void log(Logger logger, LogMessage logMessage, Object... params) {
        if (logMessage.getWorkflowStepResult() == WorkflowStepResult.OK) {
            logger.info("workflowStep = {}, " + logMessage,
                    logMessage.getWorkflowStep(),
                    logMessage.getMsg(),
                    params);
        } else {
            logger.error("workflowStep = {}, " + logMessage,
                    logMessage.getWorkflowStep(),
                    logMessage.getMsg(),
                    params);
        }
    }
}
