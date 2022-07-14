package com.example.testingspringbootapp;

import com.example.testingspringbootapp.model.User;
import com.example.testingspringbootapp.model.Vehicle;
import com.example.testingspringbootapp.model.VehicleBrand;
import com.example.testingspringbootapp.model.VehicleType;
import com.example.testingspringbootapp.model.exceptions.InvalidUsernameOrPasswordException;
import com.example.testingspringbootapp.model.exceptions.NotGoodException;
import com.example.testingspringbootapp.model.exceptions.PasswordsDoNotMatchException;
import com.example.testingspringbootapp.model.exceptions.UsernameAlreadyExistsException;
import com.example.testingspringbootapp.repository.UserRepository;
import com.example.testingspringbootapp.repository.VehicleBrandRepository;
import com.example.testingspringbootapp.repository.VehicleRepository;
import com.example.testingspringbootapp.service.VehicleBrandService;
import com.example.testingspringbootapp.service.impl.UserServiceImpl;
import org.apache.xpath.operations.String;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class JUnitTests {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private VehicleRepository vehicleRepository;
    @Mock
    private VehicleBrandRepository vehicleBrandRepository;
    @Mock
    private VehicleBrandService vehicleBrandService;
    private UserServiceImpl userService;
    @Captor
    private ArgumentCaptor<VehicleBrand> vehicleBrandArgumentCaptor;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        User user = new User("username", "password", "name", "surname");
        when(this.userRepository.save(Mockito.any(User.class))).thenReturn(user);
        when(this.passwordEncoder.encode(Mockito.anyString())).thenReturn("password");
        this.userService = Mockito.spy(new UserServiceImpl(this.userRepository, this.passwordEncoder, this.vehicleRepository));
        this.vehicleBrandService.add("test");
        this.vehicleBrandService.add("vehicleBrand1");
        this.vehicleBrandService.add("vehicleBrand2");
        this.vehicleBrandService.add("vehicleBrand3");
    }

    @Test
    public void testSuccessRegister() {
        User user = this.userService.register("username", "password", "password", "name", "surname");

        verify(this.userService).register("username", "password", "password", "name", "surname");

        Assert.assertNotNull("User is null", user);
        Assert.assertEquals("name do not mach", "name", user.getName());
        Assert.assertEquals("surname do not mach", "surname", user.getSurname());
        Assert.assertEquals("password do not mach", "password", user.getPassword());
        Assert.assertEquals("username do not mach", "username", user.getUsername());
    }

    @Test
    public void testNullUsername() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.userService.register(null, "password", "password", "name", "surname"));
        verify(this.userService).register(null, "password", "password", "name", "surname");
    }

    @Test
    public void testEmptyUsername() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.userService.register("", "password", "password", "name", "surname"));
        verify(this.userService).register("", "password", "password", "name", "surname");
    }


    @Test
    public void testEmptyPassword() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.userService.register("username", "", "", "name", "surname"));
        verify(this.userService).register("username", "", "", "name", "surname");
    }


    @Test
    public void testNullPassword() {
        Assert.assertThrows("InvalidArgumentException expected",
                InvalidUsernameOrPasswordException.class,
                () -> this.userService.register("username", "", "password", "name", "surname"));
        verify(this.userService).register("username", "", "password", "name", "surname");
    }


    @Test
    public void testPasswordMismatch() {
        Assert.assertThrows("PasswordsDoNotMatchException expected",
                PasswordsDoNotMatchException.class,
                () -> this.userService.register("username", "password", "confirmPassword", "name", "surname"));
        verify(this.userService).register("username", "password", "confirmPassword", "name", "surname");
    }

    @Test
    public void testDuplicateUsername() {
        User user = new User("username", "password", "name", "surname");
        when(this.userRepository.findByUsername(Mockito.anyString())).thenReturn(Optional.of(user));
        Assert.assertThrows("UsernameAlreadyExistsException expected",
                NotGoodException.class,
                () -> this.userService.register("username", "password", "password", "name", "surname"));
        verify(this.userService).register("username", "password", "password", "name", "surname");
    }

    @Test
    public void getVehicleTest()
    {
        List<Vehicle> list = new ArrayList<Vehicle>();
        VehicleBrand vehicleBrand=new VehicleBrand("BMW");
        Vehicle vehicle1 = new Vehicle(vehicleBrand, "B", VehicleType.CAR, 2000.00);
        Vehicle vehicle2 = new Vehicle(vehicleBrand, "Q7", VehicleType.CAR, 2000.00);
        Vehicle vehicle3 = new Vehicle(vehicleBrand, "B", VehicleType.CAR, 2000.00);
        list.add(vehicle1);
        list.add(vehicle2);
        list.add(vehicle3);
        when(vehicleRepository.findAll()).thenReturn(list);
        List<Vehicle> vehicles = vehicleRepository.findAll();
        assertEquals(3, list.size());
        verify(vehicleRepository, times(1)).findAll();
    }
    @Test
    public void getVehicleBrandTest()
    {
        List<VehicleBrand> list = new ArrayList<VehicleBrand>();
        VehicleBrand vehicleBrand1=new VehicleBrand("BMW");
        VehicleBrand vehicleBrand2=new VehicleBrand("Audi");
        VehicleBrand vehicleBrand3=new VehicleBrand("P");
        list.add(vehicleBrand1);
        list.add(vehicleBrand2);
        list.add(vehicleBrand3);
        when(vehicleBrandRepository.findAll()).thenReturn(list);
        List<VehicleBrand> vehiclesBrand = vehicleBrandRepository.findAll();
        assertEquals(3, list.size());
        verify(vehicleBrandRepository, times(1)).findAll();
    }


}