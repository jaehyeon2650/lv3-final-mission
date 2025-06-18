package finalmission.reservation.presentation;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import finalmission.config.RestDocsConfig;
import finalmission.login.util.CookieManager;
import finalmission.login.util.JwtProvider;
import finalmission.reservation.dto.request.CreateReservationRequest;
import finalmission.reservation.dto.response.CreateReservationResponse;
import finalmission.reservation.dto.response.ReservationResponse;
import finalmission.reservation.service.ReservationService;
import jakarta.servlet.http.Cookie;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Import({RestDocsConfig.class, CookieManager.class})
@WebMvcTest(ReservationController.class)
@ExtendWith(RestDocumentationExtension.class)
class ReservationControllerTest {

    @MockitoBean
    private JwtProvider jwtProvider;

    @MockitoBean
    private ReservationService reservationService;

    @Value("${application.ip}")
    private String ip;

    @Value("${application.port}")
    private int port;

    @Autowired
    private RestDocumentationResultHandler restDocs;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(
            WebApplicationContext webApplicationContext,
            RestDocumentationContextProvider provider
    ) {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                .apply(documentationConfiguration(provider)
                        .uris()
                        .withScheme("http")
                        .withHost(ip)
                        .withPort(port))
                .alwaysDo(restDocs)
                .build();
    }

    @Test
    void createReservation() throws Exception {
        LocalDate reservationDate = LocalDate.now().plusDays(1);
        CreateReservationRequest request = new CreateReservationRequest(reservationDate, 1L);
        CreateReservationResponse response = new CreateReservationResponse(1L, "cogi", reservationDate,
                LocalTime.of(10, 0));
        when(reservationService.createReservation(any(), anyLong()))
                .thenReturn(response);
        when(jwtProvider.extractIdFromAccessToken(anyString()))
                .thenReturn(1L);

        mockMvc.perform(RestDocumentationRequestBuilders.post("/reservations")
                        .cookie(new Cookie("token", "access_token"))
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        header().string(HttpHeaders.LOCATION, "/reservations/1"),
                        content().json(objectMapper.writeValueAsString(response))
                ).andDo(
                        restDocs.document(
                                responseHeaders(
                                        headerWithName(HttpHeaders.LOCATION).description("Created Reservation URI")
                                ),
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Reservation Id"),
                                        fieldWithPath("username").type(JsonFieldType.STRING).description("Username"),
                                        fieldWithPath("date").type(JsonFieldType.STRING).description("Reservation Date"),
                                        fieldWithPath("time").type(JsonFieldType.STRING).description("Reservation Time")
                                )
                        )
                );
    }

    @Test
    void getAllReservations() throws Exception {
        List<ReservationResponse> response = List.of(
                new ReservationResponse(1L, "cogi", LocalDate.now().plusDays(1), LocalTime.of(10, 0)),
                new ReservationResponse(2L, "cogi", LocalDate.now().plusDays(1), LocalTime.of(11, 0))
        );
        when(reservationService.findAllReservation())
                .thenReturn(response);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/reservations"))
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(response))
                ).andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("[].id").type(JsonFieldType.NUMBER).description("Reservation Id"),
                                        fieldWithPath("[].username").type(JsonFieldType.STRING).description("Username"),
                                        fieldWithPath("[].date").type(JsonFieldType.STRING).description("Reservation Date"),
                                        fieldWithPath("[].time").type(JsonFieldType.STRING).description("Reservation Time")
                                )
                        )
                );
    }

    @Test
    void getMyReservationInfo() throws Exception {
        ReservationResponse response = new ReservationResponse(1L, "cogi", LocalDate.now().plusDays(1),
                LocalTime.of(10, 0));
        when(reservationService.findMyReservation(anyLong(), anyLong()))
                .thenReturn(response);
        when(jwtProvider.extractIdFromAccessToken(anyString()))
                .thenReturn(1L);

        mockMvc.perform(RestDocumentationRequestBuilders.get("/reservations/{reservationId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "access_token")))
                .andExpectAll(
                        status().isOk(),
                        content().json(objectMapper.writeValueAsString(response))
                ).andDo(
                        restDocs.document(
                                responseFields(
                                        fieldWithPath("id").type(JsonFieldType.NUMBER).description("Reservation Id"),
                                        fieldWithPath("username").type(JsonFieldType.STRING).description("Username"),
                                        fieldWithPath("date").type(JsonFieldType.STRING).description("Reservation Date"),
                                        fieldWithPath("time").type(JsonFieldType.STRING).description("Reservation Time")
                                )
                        )
                );
    }

    @Test
    void deleteReservation() throws Exception {
        when(jwtProvider.extractIdFromAccessToken(anyString()))
                .thenReturn(1L);

        mockMvc.perform(RestDocumentationRequestBuilders.delete("/reservations/{reservationId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .cookie(new Cookie("token", "access_token")))
                .andExpectAll(
                        status().isNoContent()
                ).andDo(
                        restDocs.document()
                );
    }
}