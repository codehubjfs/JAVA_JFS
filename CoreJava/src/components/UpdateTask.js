import React, { useState, useEffect } from "react";
import { Modal, Button } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { updateTaskToJson } from "../Slices/TaskSlice";

const MyVerticallyCenteredModal = ({ show, onHide }) => {
    const dispatch = useDispatch();
    const selectedTask = useSelector((state) => state.tasks.selectedTask);
    const [name, setName] = useState(""); // Changed setTitle to setName
    const [description, setDescription] = useState("");

    useEffect(() => {
        if (selectedTask) {
            setName(selectedTask.name); // Changed setTitle to setName
            setDescription(selectedTask.description);
        }
    }, [selectedTask]);

    const handleUpdate = () => {
        dispatch(updateTaskToJson({ ...selectedTask, name, description })); // Changed selectedTask.name to name
        onHide();
    };

    return (
        <Modal
            show={show}
            onHide={onHide}
            size="lg"
            aria-labelledby="contained-modal-title-vcenter"
            centered
        >
            <Modal.Header closeButton>
                <Modal.Title id="contained-modal-title-vcenter">
                    Update Task
                </Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <input
                    type="text"
                    value={name}
                    onChange={(e) => setName(e.target.value)} // Changed setTitle to setName
                    placeholder="Title"
                    className="form-control mb-3"
                />
                <textarea
                    value={description}
                    onChange={(e) => setDescription(e.target.value)}
                    placeholder="Description"
                    className="form-control"
                />
            </Modal.Body>
            <Modal.Footer>
                <Button onClick={onHide}>Close</Button>
                <Button onClick={handleUpdate}>Save changes</Button>
            </Modal.Footer>
        </Modal>
    );
};

export default MyVerticallyCenteredModal;
