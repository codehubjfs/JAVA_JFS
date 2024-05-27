import logo from "./logo.svg";
import "./App.css";
import Navbar from "./components/Navbar";
import AddTask from "./components/AddTask";
import Container from "react-bootstrap/Container";
import { Row, Col } from "react-bootstrap";
import TasksList from "./components/TasksList";
import CustomerRegistration from "./components/CustomerRegistration";
import CustomerTable from "./components/CustomerTable";
import sushcomponent from "./components/sushcomponent";
import Sathish from "./sathish";
import Sathish1 from "./sathish";
function App() {
  return (
    <Container>
      <Navbar />
      <Row className="justify-content-md-center">
        <Col lg="10">
          {/* <AddTask />
          <TasksList /> */}
          <CustomerRegistration/>
          <Sathish/>
          <CustomerTable/>
          <Sanjev/>
          <Santhosh />
          <Sathish1/>
          <Kavya/>
          <Component/>
          <Sush/>
        </Col>  
        <sushcomponent />
        <susmitha />
      </Row>
    </Container>
  );
}

export default App;
