package com.ppi.customer

import groovy.json.JsonSlurper
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.MvcResult
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static groovy.json.JsonOutput.toJson
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@RunWith(SpringRunner.class)
@SpringBootTest
class CustomerTest {
    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation();
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext context;

    private JsonSlurper jsonSlurper

    private RestDocumentationResultHandler document

    CustomerTest() {
        jsonSlurper = new JsonSlurper()

    }

    @Before
    public void setUp() {

        this.document = document(
                "{class-name}/{method-name}",
                preprocessResponse(prettyPrint())
        )

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .alwaysDo(document)
                .build();
    }


    @Test
    void test1() {
        def headers = new HttpHeaders()
        def testVo = parseJson(this.mockMvc.perform(post('/test').headers(headers)
                .content(toJson(testRequest())))
                .andDo(print())
                .andExpect(status().isCreated())
                .andDo(document("testrest"))
                .andReturn())

        def name = testVo.name

    }


    def parseJson(MvcResult result) {
        return jsonSlurper.parse(result.getResponse().getContentAsByteArray())
    }

    def testRequest() {
        return [
                name: "ankit"
        ]
    }


}
