import logo from "./logo.svg";
import "./App.css";
import Navbar from "./components/Navbar";
import AddTask from "./components/AddTask";
import Container from "react-bootstrap/Container";
import { Row, Col } from "react-bootstrap";
import TasksList from "./components/TasksList";
import CustomerRegistration from "./components/CustomerRegistration";
import CustomerTable from "./components/CustomerTable";

function App() {
  return (
    <Container>
      <Navbar />
      <Row className="justify-content-md-center">
        <Col lg="10">
          {/* <AddTask />
          <TasksList /> */}
          <CustomerRegistration/>
          <CustomerTable/>
          <Kavya/>
          <logesh/>
        </Col>  
        
      </Row>
    </Container>
  );
}

export default App;
