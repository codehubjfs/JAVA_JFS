import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";

const initialState = {
    taskList: [],
    selectedTask: {},
    error: "",
};

export const getTasksFromJson = createAsyncThunk(
    "tasks/getTasksFromJson",
    async (_, { rejectWithValue }) => {
        try {
            const response = await fetch("http://localhost:8086/topics",{
                method:"GET",
                mode:"cors",
                credentials:"same-origin"
            });
            if (!response.ok) {
                throw new Error("No Tasks found");
            }
            const data = await response.json();
            return data;
        } catch (error) {
            return rejectWithValue({ error: error.message });
        }
    }
);

export const addTaskToJson = createAsyncThunk(
    "tasks/addTaskToJson",
    async (task, { rejectWithValue }) => {
        try {
            const response = await fetch("http://localhost:8086/topics", {
                method: "POST",
                mode: "cors",
                credentials: "same-origin",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(task),
            });
            if (!response.ok) {
                throw new Error("Something went wrong in task addition");
            }
            const data = await response.json();
            return data;
        } catch (error) {
            return rejectWithValue({ error: error.message });
        }
    }
);

export const updateTaskToJson = createAsyncThunk(
    "tasks/updateTaskToJson",
    async (task, { rejectWithValue }) => {
        try {
            const response = await fetch(`http://localhost:8086/topics/${task.id}`, {
                method: "PUT",
                mode: "cors",
                credentials: "same-origin",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(task),
            });
            if (!response.ok) {
                throw new Error("Something went wrong in task update");
            }
            const data = await response.json();
            return data;
        } catch (error) {
            return rejectWithValue({ error: error.message });
        }
    }
);

export const deleteTaskFromJson = createAsyncThunk(
    "tasks/deleteTaskFromJson",
    async (taskId, { rejectWithValue }) => {
        try {
            const response = await fetch(`http://localhost:8086/topics/${taskId}`, {
                method: "DELETE",
                mode: "cors",
                credentials: "same-origin",
            });
            if (!response.ok) {
                throw new Error("Something went wrong in task deletion");
            }
            return taskId;
        } catch (error) {
            return rejectWithValue({ error: error.message });
        }
    }
);

export const TaskSlice = createSlice({
    name: "taskSlice",
    initialState,
    reducers: {
        setSelectedTask: (state, action) => {
            state.selectedTask = action.payload;
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(getTasksFromJson.fulfilled, (state, action) => {
                state.taskList = action.payload;
                state.error = "";
            })
            .addCase(getTasksFromJson.rejected, (state, action) => {
                state.error = action.payload.error;
                state.taskList = [];
            })
            .addCase(addTaskToJson.fulfilled, (state, action) => {
                state.taskList.push(action.payload);
                state.error = "";
            })
            .addCase(addTaskToJson.rejected, (state, action) => {
                state.error = action.payload.error;
            })
            .addCase(updateTaskToJson.fulfilled, (state, action) => {
                state.taskList = state.taskList.map(task => 
                    task.id === action.payload.id ? action.payload : task
                );
                state.error = "";
            })
            .addCase(updateTaskToJson.rejected, (state, action) => {
                state.error = action.payload.error;
            })
            .addCase(deleteTaskFromJson.fulfilled, (state, action) => {
                state.taskList = state.taskList.filter(task => task.id !== action.payload);
                state.error = "";
            })
            .addCase(deleteTaskFromJson.rejected, (state, action) => {
                state.error = action.payload.error;
            });
    },
});

export const { setSelectedTask } = TaskSlice.actions;
export default TaskSlice.reducer;
