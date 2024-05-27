import React, { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import { useDispatch } from "react-redux";
import { registerCustomer } from "../Slices/CustomerSlice"; // Adjust the path as necessary

const CustomerRegistration = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
    phone: '',
    address: '',
    dateOfBirth: '',
    gender: '',
    membershipType: '',
    termsAccepted: false,
  });
  const [error, setError] = useState('');

  const dispatch = useDispatch();

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFormData({ ...formData, [name]: type === 'checkbox' ? checked : value });
  };

  const handleRegister = (e) => {
    e.preventDefault();
    const { firstName, lastName, email, password, confirmPassword, phone, address, dateOfBirth, gender, membershipType, termsAccepted } = formData;

    if (!firstName || !lastName || !email || !password || !confirmPassword || !phone || !address || !dateOfBirth || !gender || !membershipType || !termsAccepted) {
      setError('All fields are required and terms must be accepted.');
      return;
    }

    if (password !== confirmPassword) {
      setError('Passwords do not match.');
      return;
    }

    // Dispatch the registration action
    dispatch(registerCustomer({ firstName, lastName, email, password, phone, address, dateOfBirth, gender, membershipType, termsAccepted }));
    setFormData({
      firstName: '',
      lastName: '',
      email: '',
      password: '',
      confirmPassword: '',
      phone: '',
      address: '',
      dateOfBirth: '',
      gender: '',
      membershipType: '',
      termsAccepted: false,
    });
    setError('');
  };

  return (
    <section className="my-5">
      <Form onSubmit={handleRegister}>
        <Form.Group className="mb-3" controlId="formFirstName">
          <Form.Label>First Name</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter First Name"
            name="firstName"
            value={formData.firstName}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formLastName">
          <Form.Label>Last Name</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter Last Name"
            name="lastName"
            value={formData.lastName}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formEmail">
          <Form.Label>Email</Form.Label>
          <Form.Control
            type="email"
            placeholder="Enter Email"
            name="email"
            value={formData.email}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formPassword">
          <Form.Label>Password</Form.Label>
          <Form.Control
            type="password"
            placeholder="Enter Password"
            name="password"
            value={formData.password}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formConfirmPassword">
          <Form.Label>Confirm Password</Form.Label>
          <Form.Control
            type="password"
            placeholder="Confirm Password"
            name="confirmPassword"
            value={formData.confirmPassword}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formPhone">
          <Form.Label>Phone Number</Form.Label>
          <Form.Control
            type="tel"
            placeholder="Enter Phone Number"
            name="phone"
            value={formData.phone}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formAddress">
          <Form.Label>Address</Form.Label>
          <Form.Control
            type="text"
            placeholder="Enter Address"
            name="address"
            value={formData.address}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formDateOfBirth">
          <Form.Label>Date of Birth</Form.Label>
          <Form.Control
            type="date"
            name="dateOfBirth"
            value={formData.dateOfBirth}
            onChange={handleChange}
          />
        </Form.Group>

        <Form.Group className="mb-3" controlId="formGender">
          <Form.Label>Gender</Form.Label>
          <div>
            <Form.Check
              inline
              label="Male"
              type="radio"
              name="gender"
              value="male"
              checked={formData.gender === 'male'}
              onChange={handleChange}
            />
            <Form.Check
              inline
              label="Female"
              type="radio"
              name="gender"
              value="female"
              checked={formData.gender === 'female'}
              onChange={handleChange}
            />
            <Form.Check
              inline
              label="Other"
              type="radio"
              name="gender"
              value="other"
              checked={formData.gender === 'other'}
              onChange={handleChange}
            />
          </div>
        </Form.Group>

        <Form.Group className="mb-3" controlId="formMembershipType">
          <Form.Label>Membership Type</Form.Label>
          <Form.Control
            as="select"
            name="membershipType"
            value={formData.membershipType}
            onChange={handleChange}
          >
            <option value="">Select Membership Type</option>
            <option value="basic">Basic</option>
            <option value="premium">Premium</option>
            <option value="vip">VIP</option>
          </Form.Control>
        </Form.Group>

        <Form.Group className="mb-3" controlId="formTermsAccepted">
          <Form.Check
            type="checkbox"
            label="I accept the terms and conditions"
            name="termsAccepted"
            checked={formData.termsAccepted}
            onChange={handleChange}
          />
        </Form.Group>

        {error && <div className="alert alert-danger">{error}</div>}

        <div className="text-end">
          <Button variant="primary" type="submit">
            Register
          </Button>
        </div>
      </Form>
    </section>
  );
};

export default CustomerRegistration;
