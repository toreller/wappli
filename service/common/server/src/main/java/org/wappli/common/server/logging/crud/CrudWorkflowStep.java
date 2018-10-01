package org.wappli.common.server.logging.crud;


import org.wappli.common.server.logging.WorkflowStep;

public enum CrudWorkflowStep implements WorkflowStep {
    CREATE(CrudWorkflow.CRUD_OPERAION),
    READ(CrudWorkflow.CRUD_OPERAION),
    UPDATE(CrudWorkflow.CRUD_OPERAION),
    DELETE(CrudWorkflow.CRUD_OPERAION);

    private CrudWorkflow workflow;

    CrudWorkflowStep(CrudWorkflow workflow) {
        this.workflow = workflow;
    }

    public String getProcType() {
        return workflow.name();
    }

    public String getProcStep() {
        return name();
    }
}
