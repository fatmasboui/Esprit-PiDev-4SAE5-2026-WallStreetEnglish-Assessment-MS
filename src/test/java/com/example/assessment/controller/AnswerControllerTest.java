@WebMvcTest(AnswerController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AnswerService service;

    @Autowired
    private ObjectMapper objectMapper;

    private Answer answer;

    @BeforeEach
    void setUp() {
        answer = new Answer();
        answer.setId(1L);
        answer.setContent("Test Answer");
    }

    @Test
    void testGetAll() throws Exception {
        when(service.getAll()).thenReturn(Arrays.asList(answer));
        mockMvc.perform(get("/answers"))
                .andExpect(status().isOk());
    }

    @Test
    void testGetById() throws Exception {
        when(service.getById(1L)).thenReturn(answer);   // Make sure service returns Optional or object as per controller
        mockMvc.perform(get("/answers/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testCreate() throws Exception {
        when(service.save(any(Answer.class))).thenReturn(answer);
        mockMvc.perform(post("/answers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(answer)))
                .andExpect(status().isOk());   // or .isCreated() if you use 201
    }
}
