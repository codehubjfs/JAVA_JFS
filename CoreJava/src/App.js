import logo from "./logo.svg";
import "./App.css";
import Navbar from "./components/Navbar";
import AddTask from "./components/AddTask";
import Container from "react-bootstrap/Container";
import { Row, Col } from "react-bootstrap";
import TasksList from "./components/TasksList";
import CustomerRegistration from "./components/CustomerRegistration";
import CustomerTable from "./components/CustomerTable";
<<<<<<< HEAD
import sushcomponent from "./components/sushcomponent";
=======
import Sathish from "./sathish";
import Sathish1 from "./sathish";
>>>>>>> 5d6f5d7da9500973a50326f4ba614c10dda62306
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
          <Sathish1/>
          <Kavya/>
          <Component/>
        </Col>  
        <sushcomponent />
        <susmitha />
      </Row>
    </Container>
  );
}

export default App;
