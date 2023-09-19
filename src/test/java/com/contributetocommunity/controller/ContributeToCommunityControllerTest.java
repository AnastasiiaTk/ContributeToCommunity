package com.contributetocommunity.controller;

import com.contributetocommunity.VolunteerTestDataBuilder;
import com.contributetocommunity.bom.Volunteer;
import com.contributetocommunity.service.VolunteerService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ContributeToCommunityController.class)
@AutoConfigureMockMvc
public class ContributeToCommunityControllerTest {

    @MockBean
    private VolunteerService volunteerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnVolunteersByJobId() throws Exception {
        List<Volunteer> volunteer = VolunteerTestDataBuilder.buildVolunteers();
        when(volunteerService.getVolunteersByJobId(anyInt())).thenReturn(volunteer);
        this.mockMvc.perform(get("/api/volunteers/{jobId}", 1))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andExpect(content().string(VolunteerTestDataBuilder.getVolunteersAsJson()));
    }

    @Test
    void shouldReturnEmptyByNotExistingJobId() throws Exception {
        List<Volunteer> volunteer = new ArrayList<>();

        when(volunteerService.getVolunteersByJobId(anyInt())).thenReturn(volunteer);
        this.mockMvc.perform(get("/api/volunteers/{jobId}", 1))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

    @Test
    void shouldReturnPajeVolunteersByJobId() throws Exception {
        List<Volunteer> volunteer = VolunteerTestDataBuilder.buildVolunteers();
        when(volunteerService.findVolunteers(anyInt(), anyInt())).thenReturn(new PageImpl<>(volunteer));
        this.mockMvc.perform(get("/api/volunteersPage")
                        .param("pageIndex", "1")
                        .param("pageSize", "2"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andExpect(content()
                        .string(Matchers.containsString(VolunteerTestDataBuilder.getVolunteersAsJson())));
    }

    @Test
    void shouldReturnEmptyPajeByNotExistingJobId() throws Exception {
        when(volunteerService.findVolunteers(anyInt(), anyInt())).thenReturn(new PageImpl<>(new ArrayList<>()));
        this.mockMvc.perform(get("/api/volunteersPage")
                        .param("pageIndex", "2")
                        .param("pageSize", "2"))
                .andDo(MockMvcResultHandlers.print()).andExpect(status().isOk())
                .andExpect(content().string(containsString("[]")));
    }

}
