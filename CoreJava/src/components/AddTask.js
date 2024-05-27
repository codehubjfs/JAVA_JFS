import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { useDispatch } from "react-redux";
import { addTaskToJson } from "../Slices/TaskSlice";

const AddTask = () => {
  const [name, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [error, setError] = useState('');

  const dispatch = useDispatch();

  const handleAddTask = (e) => {
    e.preventDefault();
    if (name.trim() && description.trim()) {
      dispatch(addTaskToJson({ name, description }));
      setTitle('');
      setDescription('');
      setError('');
    } else {
      setError('Please provide both a title and a description.');
    }
  };

  return (
    <section className="my-5">
      <Form onSubmit={handleAddTask}>
        <Form.Group className="mb-3" controlId="formTaskTitle">
          <Form.Label>Task Title</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter Task Title"
            value={name}
            onChange={(e) => setTitle(e.target.value)}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formTaskDescription">
          <Form.Label>Task Description</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter Task Description"
            value={description}
            onChange={(e) => setDescription(e.target.value)}
          />
        </Form.Group>
        {error && <p className="text-danger">{error}</p>} {/* Display error message if exists */}
        <div className="text-end">
          <Button variant="primary" type="submit">
            Add Task
          </Button>
        </div>
      </Form>
    </section>
  );
};

export default AddTask;
