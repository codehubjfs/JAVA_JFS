import React, { useState } from "react";
import { Container, Table, Button } from "react-bootstrap";

import "./cartstyle.css";

function Cart() {
  const [items, setItems] = useState([
    {
      id: 1,
      name: "Smart Phone",
      price: 10000,
      image:
        "https://img.freepik.com/free-photo/smartphone-balancing-with-pink-background_23-2150271746.jpg?w=740&t=st=1710665710~exp=1710666310~hmac=6b39b8f81ab905673c95e9962247e5a4e6a1f49819e755d0deff7d7f4f85a8d1",
      quantity: 1,
    },
    {
      id: 2,
      name: "V Brand T-shirt",
      price: 450,
      image:
        "https://img.freepik.com/free-photo/shirt-mockup-concept-with-plain-clothing_23-2149448792.jpg?t=st=1710665779~exp=1710669379~hmac=3bc416142dac8658813e152b28d965f948e72336e7d1afdb183d6ea3604d590f&w=360",
      quantity: 1,
    },
    {
      id: 3,
      name: "Teddy Bear",
      price: 300,
      image:
        "https://img.freepik.com/free-photo/front-view-cute-little-baby-boy-cupcake-teddybear_23-2148415544.jpg?t=st=1710665829~exp=1710669429~hmac=fc3b3b8ecf0e550b24f59fa5d6474a54f1c03b5edf990edcc1e9272a047dfe3f&w=740",
      quantity: 1,
    },
  ]);

  const handleIncreaseQuantity = (id) => {
    const updatedItems = items.map((item) => {
      if (item.id === id && item.quantity < 10) {
        // Limit quantity to 10
        return { ...item, quantity: item.quantity + 1 };
      }
      return item;
    });
    setItems(updatedItems);
  };

  const handleDecreaseQuantity = (id) => {
    const updatedItems = items.map((item) => {
      if (item.id === id && item.quantity > 1) {
        return { ...item, quantity: item.quantity - 1 };
      }
      return item;
    });
    setItems(updatedItems);
  };

  const handleRemoveItem = (id) => {
    const updatedItems = items.filter((item) => item.id !== id);
    setItems(updatedItems);
  };

  const calculateTotal = () => {
    return items.reduce((acc, item) => acc + item.price * item.quantity, 0);
  };

  return (
    <>
      <Container>
        <h1 className="mb-4">Shopping Cart</h1>
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Product</th>
              <th>Product Name</th>
              <th>Price</th>
              <th>Quantity</th>
              <th>Total</th>
              <th>Remove</th>
            </tr>
          </thead>
          <tbody>
            {items.map((item) => (
              <tr key={item.id}>
                <td>
                  <img src={item.image} alt="Item Image" />
                </td>
                <td>
                  <a href="#" className="text-decoration-none">
                    {item.name}
                  </a>
                </td>
                <td>₹{item.price}</td>
                <td>
                  <Button
                    variant="outline-primary"
                    className="quantity-btn"
                    style={{ marginRight: "10px" }}
                    onClick={() => handleDecreaseQuantity(item.id)}
                  >
                    <i className="fas fa-minus"></i>
                  </Button>
                  {item.quantity}
                  <Button
                    variant="outline-primary"
                    className="quantity-btn"
                    style={{ marginLeft: "10px" }}
                    onClick={() => handleIncreaseQuantity(item.id)}
                  >
                    <i className="fas fa-plus"></i>
                  </Button>
                </td>
                <td>₹{item.price * item.quantity}</td>
                <td>
                  <Button
                    variant="outline-danger"
                    onClick={() => handleRemoveItem(item.id)}
                  >
                    Remove
                  </Button>
                </td>
              </tr>
            ))}
          </tbody>
          <tfoot>
            <tr>
              <td colSpan="4"></td>
              <td>
                <strong style={{ fontSize: "25px" }}>
                  Total: ₹{calculateTotal()}
                </strong>
              </td>
              <td></td>
            </tr>
          </tfoot>
        </Table>
        <div style={{ marginLeft: "88%" }}>
          <Button variant="primary" className="mt-4 place-order-btn">
            Place Order
          </Button>
        </div>
      </Container>
      <div>
        <br />
      </div>
    </>
  );
}

export default Cart;
