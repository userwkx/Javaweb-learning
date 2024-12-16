package gh.com.oasystem.mapper;

import gh.com.oasystem.model.entity.ProcessFlow;

import java.util.List;

public interface ProcessFlowMapper {
    int insertFlow(ProcessFlow flow);
    List<ProcessFlow>selectFlow(Long formId);
    void updateFlow(ProcessFlow processFlow);
}
