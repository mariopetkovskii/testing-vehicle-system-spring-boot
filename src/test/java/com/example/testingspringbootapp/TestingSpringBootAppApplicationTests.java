package com.example.testingspringbootapp;

import com.example.testingspringbootapp.model.*;
import com.example.testingspringbootapp.repository.UserRepository;
import com.example.testingspringbootapp.service.UserService;
import com.example.testingspringbootapp.service.VehicleBrandService;
import com.example.testingspringbootapp.service.VehicleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class TestingSpringBootAppApplicationTests {

    MockMvc mockMvc;

    @Autowired
    VehicleService vehicleService;

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    VehicleBrandService vehicleBrandService;

    @Autowired
    UserDetailsService userDetailsService;

    private static boolean dataInitialized = false;

    private static VehicleBrand vehicleBrand;

    public static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    private void initData() {
        if (!dataInitialized) {
            User user = new User("admin1", passwordEncoder.encode("admin1"), "admin", "admin");
            user.setRole(Role.ROLE_ADMINISTRATOR);
            User user1 = new User("user1", passwordEncoder.encode("user1"), "user", "user");
            this.userRepository.save(user);
            this.userRepository.save(user1);
            vehicleBrand = new VehicleBrand("BMW");

        }
        dataInitialized = true;
    }

    @Test
    void contextLoads() {
    }

    @Test
    public void testGetVehicles() throws Exception {
        MockHttpServletRequestBuilder vehicleRequest = MockMvcRequestBuilders.get("/vehicles");
        this.mockMvc.perform(vehicleRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.model().attributeExists("vehicles"))
                .andExpect(MockMvcResultMatchers.model().attribute("bodyContent", "veh-page"))
                .andExpect(MockMvcResultMatchers.view().name("master-template"));
    }

    @Test
    public void testDeleteVehicle() throws Exception
    {
        Vehicle vehicle = this.vehicleService.add(vehicleBrand, "M8", VehicleType.CAR, 30000.0);
        MockHttpServletRequestBuilder vehicleRequest = MockMvcRequestBuilders.post("/vehicles/delete/" + vehicle.getId());
        this.mockMvc.perform(vehicleRequest)
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/vehicles"));
    }

    @Test
    public void testAddVehicle() throws Exception{
        Vehicle vehicle = this.vehicleService.add(vehicleBrand, "M8", VehicleType.CAR, 30000.0);
        this.mockMvc.perform(MockMvcRequestBuilders
                .post("/vehicles/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(vehicle))
                .accept(MediaType.APPLICATION_JSON));
    }

//    @Test
//    @WithMockUser(username = "test", roles = {"USER"})
//    public void addFavoriteVehicle() throws Exception{
//        Vehicle vehicle = this.vehicleService.add(vehicleBrand, "M8", VehicleType.CAR, 30000.0);
////        Authentication authentication = new TestingAuthenticationToken("test", "test", "ROLE_USER");
//        MockHttpServletRequestBuilder vehicleRequest = MockMvcRequestBuilders.post("/vehicles/favorite/add/" + vehicle.getId())
//                .with(SecurityMockMvcRequestPostProcessors.user("test").roles("USER"))
//                .with(SecurityMockMvcRequestPostProcessors.csrf());
//        this.mockMvc.perform(vehicleRequest)
//                .andDo(MockMvcResultHandlers.print())
//                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
//                .andExpect(MockMvcResultMatchers.redirectedUrl("/vehicles"));
//
//    }
}
