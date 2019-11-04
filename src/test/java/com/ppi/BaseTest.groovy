//package com.ppi
//
//import capital.scalable.restdocs.AutoDocumentation
//import capital.scalable.restdocs.SnippetRegistry
//import capital.scalable.restdocs.jackson.JacksonResultHandlers
//import capital.scalable.restdocs.payload.JacksonResponseFieldSnippet
//import com.fasterxml.jackson.databind.ObjectMapper
//import groovy.json.JsonSlurper
//import org.hamcrest.BaseMatcher
//import org.hamcrest.Description
//import org.hamcrest.Matcher
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.test.context.SpringBootTest
//import org.springframework.boot.test.mock.mockito.MockitoTestExecutionListener
//import org.springframework.http.MediaType
//import org.springframework.restdocs.ManualRestDocumentation
//import org.springframework.restdocs.http.HttpDocumentation
//import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation
//import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler
//import org.springframework.restdocs.payload.AbstractFieldsSnippet
//import org.springframework.restdocs.payload.FieldDescriptor
//import org.springframework.restdocs.snippet.Snippet
//import org.springframework.security.web.FilterChainProxy
//import org.springframework.test.annotation.DirtiesContext
//import org.springframework.test.context.TestExecutionListeners
//import org.springframework.test.context.testng.AbstractTestNGSpringContextTests
//import org.springframework.test.web.servlet.MockMvc
//import org.springframework.test.web.servlet.MvcResult
//import org.springframework.test.web.servlet.setup.MockMvcBuilders
//import org.springframework.web.context.WebApplicationContext
//import org.testng.annotations.AfterMethod
//import org.testng.annotations.BeforeClass
//import org.testng.annotations.BeforeMethod
//
//import java.lang.reflect.Method
//
//̥
//
//import java.lang.reflect.Type
//
//import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
//import static org.springframework.restdocs.operation.preprocess.Preprocessors.*
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
//
//@SpringBootTest
//@DirtiesContext
//@TestExecutionListeners([MockitoTestExecutionListener.class])
//abstract class BaseTest extends AbstractTestNGSpringContextTests {
//    @Autowired
//    private WebApplicationContext context
//    @Autowired
//    private FilterChainProxy securityFilterChain
//    @Autowired
//    private ObjectMapper objectMapper
//
//    private MockMvc mvc
//    private JsonSlurper jsonSlurper
//    private ManualRestDocumentation documentation
//    public bearerToken
//
//    List<String> kafkaListenTopics() {
//        []
//    }
//
//    BaseTest() {
//        jsonSlurper = new JsonSlurper()
//    }
//
//    MockMvc mvc() {
//        return mvc
//    }
//
//    def parseJson(MvcResult result) {
//        return jsonSlurper.parse(result.getResponse().getContentAsByteArray())
//    }
//
//    def parseJson(String json) {
//        return jsonSlurper.parse(json.getBytes())
//    }
//
//    static RestDocumentationResultHandler document(String identifier, Snippet... snippets) {
//        return MockMvcRestDocumentation.document(identifier, preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint()), snippets)
//    }
//
//    static Matcher<Object> isUuid() {
//        return new UuidMatcher()
//    }
//
//    private static class UuidMatcher extends BaseMatcher<Object> {
//        static final def REGEX = /(?i)^[0-9A-F]{8}-([0-9A-F]{4}-){3}[0-9A-F]{12}$/
//
//        @Override
//        boolean matches(Object o) {
//            return ((String) o).matches(REGEX)
//        }
//
//        @Override
//        void describeTo(Description description) {
//            description.appendText("matches ${REGEX}")
//        }
//    }
//
//    @BeforeClass
//    void setup() {
//        setupRestDocs()
//
//        setupMockMvc()
//
//        setupEmbeddedKafkaListener()
//    }
//
//    @BeforeMethod
//    void setup(Method method) {
//        messageListener.clearRecords()
//        documentation.beforeTest(getClass(), method.getName())
//    }
//
//    @AfterMethod
//    void tearDown(Method method) {
//        documentation.afterTest()
//    }
//
//    private void setupRestDocs() {
//        String restDocsOutput = System.getProperty('org.springframework.restdocs.outputDir')
//        if (restDocsOutput == null) {
//            restDocsOutput = 'build/generated-snippets'
//        }
//        this.documentation = new ManualRestDocumentation(restDocsOutput)
//    }
//
//    private void setupMockMvc() {
//        this.mvc = MockMvcBuilders.webAppContextSetup(context)
//                .defaultRequest(get("/")
//                        .accept(MediaType.APPLICATION_JSON_UTF8)
//                        .contentType(MediaType.APPLICATION_JSON_UTF8)
//                        .characterEncoding('UTF-8'))
//                .apply(springSecurity())
//                .alwaysDo(JacksonResultHandlers.prepareJackson(objectMapper))
//                .apply(documentationConfiguration(documentation)
//                        .uris()
//                        .withScheme('http')
//                        .withHost('localhost')
//                        .withPort(8080)
//                        .and().snippets()
//                        .withDefaults(AutoDocumentation.methodAndPath(),
//                                AutoDocumentation.description(),
//                                AutoDocumentation.pathParameters(),
//                                AutoDocumentation.requestParameters(),
//                                /* AutoDocumentation.requestHeaders(),*/
//                                new JacksonRequestFieldSnippetFix(), //fix the problem with @JsonValue in request fields
//                                AutoDocumentation.responseFields(),
//                                HttpDocumentation.httpRequest(),
//                                HttpDocumentation.httpResponse(),
//                                AutoDocumentation.sectionBuilder()
//                                        .skipEmpty(true)
//                                        .snippetNames(
//                                                SnippetRegistry.PATH_PARAMETERS,
//                                                SnippetRegistry.REQUEST_PARAMETERS,
//                                                /*SnippetRegistry.REQUEST_HEADERS,*/
//                                                SnippetRegistry.REQUEST_FIELDS,
//                                                SnippetRegistry.RESPONSE_FIELDS,
//                                                SnippetRegistry.HTTP_REQUEST,
//                                                SnippetRegistry.HTTP_RESPONSE)
//                                        .build()
//
//                        ))
//                .build()
//    }
//
//    public static AbstractFieldsSnippet autoRequestFields(FieldDescriptor... descriptors) {
//        return new RequestFieldSnippetAutoPrefixName(descriptors)
//    }
//
//    public static JacksonResponseFieldSnippet responseBodyAsType(Type type) {
//        new JacksonResponseFieldSnippet(type, false)
//    }
//
//    public String getBearerToken() {
//        def clientId = 'AUTOMATED_TESTING'
//        def clientSecret = 't3st@llth3th1ngs'
//        def username = 'MrTest'
//        def password = 'Xxsuperd00persecurexX'
//        return parseJson(mvc().perform(post('/token')
//                .param('client_id', clientId)
//                .param('grant_type', 'password')
//                .param('username', username)
//                .param('password', password)
//                .with(httpBasic(clientId, clientSecret)))
//                .andDo(print())
//                .andExpect(status().isOk()).andReturn()).access_token
//    }
//}
//
