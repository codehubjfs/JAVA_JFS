// import { configureStore } from '@reduxjs/toolkit';
// import taskReducer from './Slices/TaskSlice';

// const store = configureStore({
//   reducer: {
//     tasks: taskReducer,
//   },
// });

// export default store;

import { configureStore } from '@reduxjs/toolkit';
import customerReducer from '../src/Slices/CustomerSlice'; // Adjust the path as necessary

const store = configureStore({
  reducer: {
    customers: customerReducer,
  },
});

export default store;

