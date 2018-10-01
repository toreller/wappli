package org.wappli.common.server.logging;

public interface LogMessage {
    WorkflowStep getWorkflowStep();
    WorkflowStepResult getWorkflowStepResult();
    String getMsg();
}
