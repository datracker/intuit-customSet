package com.intuit.customSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomSetController.class)
class CustomSetControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CustomSet customSet;

    @BeforeEach
    void setUp() {
        // Reset the mock before each test
        Mockito.reset(customSet);
    }

    @Test
    void testAddItem_Success() throws Exception {
        // Mocking that the item was added successfully
        setUp();
        when(customSet.add(55555)).thenReturn(true);


        mockMvc.perform(post("/api/set/add")
                .content("55555")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Item added successfully"));
    }

    @Test
    void testAddItem_AlreadyExists() throws Exception {
        setUp();
        when(customSet.add(100)).thenReturn(false);

        mockMvc.perform(post("/api/set/add")
                .content("100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Item already exists"));
    }

    @Test
    void testAddItem_NullValue() throws Exception {
        mockMvc.perform(post("/api/set/add")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testAddItem_MaxInteger() throws Exception {
        mockMvc.perform(post("/api/set/add")
                .content("2147483647")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Item added successfully"));
    }

    @Test
    void testAddItem_MinInteger() throws Exception {
        mockMvc.perform(post("/api/set/add")
                .content("-2147483648")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Item added successfully"));
    }

    @Test
    void testAddItem_OutOfRangeMax() throws Exception {
        mockMvc.perform(post("/api/set/add")
                .content("2147483648")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testAddItem_OutOfRangeMin() throws Exception {
        mockMvc.perform(post("/api/set/add")
                .content("-2147483649")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testHasItem_Exists_AfterAdding() throws Exception {
        // First, add the item
        mockMvc.perform(post("/api/set/add")
                .content("100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Item added successfully"));

        // Then, check if the item exists
        mockMvc.perform(get("/api/set/has")
                .param("item", "100"))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }


    @Test
    void testHasItem_DoesNotExist() throws Exception {
        mockMvc.perform(get("/api/set/has")
                .param("item", "200"))
                .andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    void testHasItem_NullItem() throws Exception {
        // This test checks how the controller handles a null item (optional, as it depends on the controller's design)
        mockMvc.perform(get("/api/set/has")
                .param("item", (String) null))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testHasItem_IllegalArgument_MAX() throws Exception {
        mockMvc.perform(get("/api/set/has")
                .param("item", "2147483648"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testHasItem_IllegalArgument_MIN() throws Exception {
        mockMvc.perform(get("/api/set/has")
                .param("item", "-2147483649"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testRemoveItem_Success() throws Exception {
        mockMvc.perform(post("/api/set/add")
                .content("10000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().string("Item added successfully"));

        // No exception should be thrown, indicating successful removal
        mockMvc.perform(delete("/api/set/remove")
                .content("10000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Item removed successfully"));
    }

    @Test
    void testRemoveItem_NotExists() throws Exception {

        mockMvc.perform(delete("/api/set/remove")
                .content("200")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Item does not exist"));
    }

    @Test
    void testRemoveItem_NullItem() throws Exception {
        // This test checks how the controller handles a null item
        mockMvc.perform(delete("/api/set/remove")
                .content("")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

    @Test
    void testRemoveItem_OutOfRange() throws Exception {
        // Simulate that removing an out-of-range item throws an IllegalArgumentException

        mockMvc.perform(delete("/api/set/remove")
                .content("2147483648")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(""));
    }

}