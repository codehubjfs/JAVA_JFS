import React, { useState, useEffect } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Button } from "react-bootstrap";
import Table from "react-bootstrap/Table";
import MyVerticallyCenteredModal from "./UpdateTask";
import { getTasksFromJson, deleteTaskFromJson, setSelectedTask } from "../Slices/TaskSlice";

const TasksList = () => {
    const dispatch = useDispatch();
    const { taskList } = useSelector((state) => state.tasks);
    const [modalShow, setModalShow] = useState(false);

    useEffect(() => {
        dispatch(getTasksFromJson());
    }, [dispatch]);

    const updateTask = (task) => {
        dispatch(setSelectedTask(task));
        setModalShow(true);
    };

    const deleteTask = (taskId) => {
        dispatch(deleteTaskFromJson(taskId));
    };

    return (
        <>
            <Table striped bordered hover>
                <thead>
                    <tr className="text-center">
                        <th>#</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {taskList.map((task, index) => (
                        <tr className="text-center" key={task.id}>
                            <td>{index + 1}</td>
                            <td>{task.name}</td>
                            <td>{task.description}</td>
                            <td>
                                <Button
                                    variant="primary"
                                    className="mx-3"
                                    onClick={() => updateTask(task)}
                                >
                                    <i className="bi bi-pencil-square"></i>
                                </Button>
                                <Button variant="primary" onClick={() => deleteTask(task.id)}>
                                    <i className="bi bi-trash3"></i>
                                </Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>

            <MyVerticallyCenteredModal
                show={modalShow}
                onHide={() => setModalShow(false)}
            />
        </>
    );
};

export default TasksList;
