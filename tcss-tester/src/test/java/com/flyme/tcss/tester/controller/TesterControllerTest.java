package com.flyme.tcss.tester.controller;

import com.flyme.tcss.common.dto.SubmissionDTO;
import com.flyme.tcss.tester.TesterApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author xiaodao
 * @date 2023/1/4
 */
public class TesterControllerTest extends TesterApplicationTest {
    @Autowired
    private TesterController testerController;

    @Test
    public void testSubmitCase() {
        SubmissionDTO submission = new SubmissionDTO();
        submission.setInput("1");
        submission.setOutput("1");
        submission.setUrl("http://127.0.0.1/gtja/fund1/query");
        submission.setRecordId(1L);
        testerController.submitCase(submission);
    }
}
