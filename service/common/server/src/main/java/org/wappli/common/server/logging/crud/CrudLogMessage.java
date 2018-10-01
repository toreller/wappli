package org.wappli.common.server.logging.crud;

import org.wappli.common.server.logging.LogMessage;
import org.wappli.common.server.logging.WorkflowStepResult;

public enum CrudLogMessage implements LogMessage {
    OBJECT_CREATE_OK(CrudWorkflowStep.CREATE, WorkflowStepResult.OK, "object created"),
    OBJECT_CREATE_NOK(CrudWorkflowStep.CREATE, WorkflowStepResult.NOK, "object create failed"),
    OBJECT_READ_OK(CrudWorkflowStep.CREATE, WorkflowStepResult.OK, "object read"),
    OBJECT_READ_NOK(CrudWorkflowStep.CREATE, WorkflowStepResult.NOK, "object read failed"),
    OBJECT_UPDATE_OK(CrudWorkflowStep.UPDATE, WorkflowStepResult.OK, "object updated"),
    OBJECT_UPDATE_NOK(CrudWorkflowStep.UPDATE, WorkflowStepResult.NOK, "object update failed"),
    OBJECT_DELETE_OK(CrudWorkflowStep.DELETE, WorkflowStepResult.OK, "object deleted"),
    OBJECT_DELETE_NOK(CrudWorkflowStep.DELETE, WorkflowStepResult.NOK, "object delete failed");

    private CrudWorkflowStep workflowStep;
    private WorkflowStepResult workflowStepResult;
    private String msg;

    CrudLogMessage(CrudWorkflowStep workflowStep, WorkflowStepResult workflowStepResult, String msg) {
        this.workflowStep = workflowStep;
        this.workflowStepResult = workflowStepResult;
        this.msg = msg;
    }

    @Override
    public CrudWorkflowStep getWorkflowStep() {
        return workflowStep;
    }

    @Override
    public WorkflowStepResult getWorkflowStepResult() {
        return workflowStepResult;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
