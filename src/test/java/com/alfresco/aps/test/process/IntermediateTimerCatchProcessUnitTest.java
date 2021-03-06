package com.alfresco.aps.test.process;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alfresco.aps.testutils.AbstractBpmnTest;
import com.alfresco.aps.testutils.assertions.ProcessInstanceAssert;

import org.activiti.engine.runtime.ProcessInstance;
import static com.alfresco.aps.testutils.TestUtilsConstants.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:activiti.cfg.xml", "classpath:common-beans-and-mocks.xml" })
public class IntermediateTimerCatchProcessUnitTest extends AbstractBpmnTest {
	
	static {
		appName = "Test App";
		processDefinitionKey = "IntermediateTimerCatchProcess";
	}

	@Test
	public void testProcessExecution() throws Exception {

		ProcessInstance processInstance = activitiRule.getRuntimeService()
				.startProcessInstanceByKey(processDefinitionKey);

		assertNotNull(processInstance);
		
		ProcessInstanceAssert.assertThat(processInstance).timerJobCountIs(2)
				.timerJobsWithDueDateFromNow(5, TIME_UNIT_MINUTE).timerJobsWithDueDateFromNow(1, TIME_UNIT_DAY)
				.executeTimerJobs(5, TIME_UNIT_MINUTE).executeTimerJobs(1, TIME_UNIT_DAY).isComplete();
	}

}