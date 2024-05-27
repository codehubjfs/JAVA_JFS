import React, { useEffect, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import { Table, Button } from 'react-bootstrap';
import { fetchCustomers, deleteCustomer, setSelectedCustomer } from '../Slices/CustomerSlice';
import UpdateCustomerModal from './UpdateCustomerModal';

const CustomerTable = () => {
    const dispatch = useDispatch();
    const { customers, status, error, selectedCustomer } = useSelector((state) => state.customers);
    const [modalShow, setModalShow] = useState(false);

    useEffect(() => {
        dispatch(fetchCustomers());
    }, [dispatch]);

    const handleEdit = (customer) => {
        dispatch(setSelectedCustomer(customer));
        setModalShow(true);
    };

    const handleDelete = (id) => {
        dispatch(deleteCustomer(id));
    };

    return (
        <>
            <Table striped bordered hover>
                <thead>
                    <tr>
                        <th>#</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Address</th>
                        <th>Date of Birth</th>
                        <th>Gender</th>
                        <th>Membership Type</th>
                        <th>Actions</th>
                    </tr>
                </thead>
                <tbody>
                    {customers.map((customer, index) => (
                        <tr key={customer.id}>
                            <td>{index + 1}</td>
                            <td>{customer.firstName}</td>
                            <td>{customer.lastName}</td>
                            <td>{customer.email}</td>
                            <td>{customer.phone}</td>
                            <td>{customer.address}</td>
                            <td>{customer.dateOfBirth}</td>
                            <td>{customer.gender}</td>
                            <td>{customer.membershipType}</td>
                            <td>
                                <Button variant="warning" onClick={() => handleEdit(customer)}>Edit</Button>
                                {' '}
                                <Button variant="danger" onClick={() => handleDelete(customer.id)}>Delete</Button>
                            </td>
                        </tr>
                    ))}
                </tbody>
            </Table>

            {selectedCustomer && (
                <UpdateCustomerModal
                    show={modalShow}
                    onHide={() => setModalShow(false)}
                />
            )}
        </>
    );
};

export default CustomerTable;
